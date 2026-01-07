package com.gf.webapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.gf.webapp.entity.DatosODS;

public class GestionXLS {

    public List<DatosODS> leerXLS(String ruta) {
        List<DatosODS> lista = new ArrayList<>();
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            System.out.println("No se encontro el archivo xls: " + ruta);
            return lista;
        }

        try (FileInputStream fis = new FileInputStream(archivo); Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> filas = sheet.iterator();
            boolean primera = true;
            while (filas.hasNext()) {
                Row fila = filas.next();
                if (primera) { // saltar cabecera
                    primera = false;
                    continue;
                }
                if (isRowEmpty(fila))
                    continue;

                DatosODS dato = new DatosODS();

                // Columna 0: anio
                dato.setAnio((int) getNumericCellValue(fila.getCell(0)));
                // Columna 1: grupo
                dato.setGrupo(getStringCellValue(fila.getCell(1)));
                // Columna 2: codigoSector
                dato.setCodigoSector(getStringCellValue(fila.getCell(2)));
                // Columna 3: sector
                dato.setSector(getStringCellValue(fila.getCell(3)));
                // Columna 4: contaminante
                dato.setContaminante(getStringCellValue(fila.getCell(4)));
                // Columna 5: unidad
                dato.setUnidad(getStringCellValue(fila.getCell(5)));
                // Columna 6: cantidad
                dato.setCantidad(getNumericCellValue(fila.getCell(6)));

                lista.add(dato);
            }

        } catch (Exception e) {
            System.out.println("Error en la lectura del xls: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public void escribirXLS(String ruta, DatosODS datos) {
        File archivo = new File(ruta);
        Workbook workbook = null;
        Sheet sheet = null;
        boolean isXlsx = ruta != null && ruta.toLowerCase().endsWith(".xlsx");
        try {
            if (archivo.exists()) {
                // intentar abrir con WorkbookFactory (soporta xls y xlsx)
                try (FileInputStream fis = new FileInputStream(archivo)) {
                    workbook = WorkbookFactory.create(fis);
                } catch (Exception e) {
                    // si falla, crear workbook nuevo según la extensión
                    System.out.println("No se pudo abrir como workbook existente (se creará nuevo): " + e.getMessage());
                    workbook = isXlsx ? new XSSFWorkbook() : new HSSFWorkbook();
                }

                if (workbook.getNumberOfSheets() > 0) {
                    sheet = workbook.getSheetAt(0);
                } else {
                    sheet = workbook.createSheet("Datos");
                }
            } else {
                // crear nuevo workbook y hoja con cabecera
                workbook = isXlsx ? new XSSFWorkbook() : new HSSFWorkbook();
                sheet = workbook.createSheet("Datos");
                Row cab = sheet.createRow(0);
                cab.createCell(0).setCellValue("anio");
                cab.createCell(1).setCellValue("grupo");
                cab.createCell(2).setCellValue("codigoSector");
                cab.createCell(3).setCellValue("sector");
                cab.createCell(4).setCellValue("contaminante");
                cab.createCell(5).setCellValue("unidad");
                cab.createCell(6).setCellValue("cantidad");
            }

            // Apuntar a la siguiente fila disponible (después de la cabecera)
            int nuevaFila;
            if (sheet.getPhysicalNumberOfRows() == 0) {
                nuevaFila = 0;
            } else {
                nuevaFila = sheet.getLastRowNum() + 1;
            }
            Row fila = sheet.createRow(nuevaFila);

            fila.createCell(0).setCellValue(datos.getAnio());
            fila.createCell(1).setCellValue(nullToEmpty(datos.getGrupo()));
            fila.createCell(2).setCellValue(nullToEmpty(datos.getCodigoSector()));
            fila.createCell(3).setCellValue(nullToEmpty(datos.getSector()));
            fila.createCell(4).setCellValue(nullToEmpty(datos.getContaminante()));
            fila.createCell(5).setCellValue(nullToEmpty(datos.getUnidad()));
            fila.createCell(6).setCellValue(datos.getCantidad());

            // asegurar que el directorio existe
            File parent = archivo.getParentFile();
            if (parent != null && !parent.exists())
                parent.mkdirs();

            // escribir en archivo temporal y mover para evitar corrupciones parciales
            File temp = new File(parent, archivo.getName() + ".tmp");
            try (FileOutputStream fos = new FileOutputStream(temp)) {
                workbook.write(fos);
            }

            // intentar reemplazar el archivo original
            try {
                Files.move(temp.toPath(), archivo.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException moveEx) {
                // si falla el move, intentar escribir directamente al original como último recurso
                System.out.println("No se pudo mover el archivo temporal al destino, intentando escritura directa: " + moveEx.getMessage());
                try (FileOutputStream fos2 = new FileOutputStream(archivo)) {
                    workbook.write(fos2);
                }
            }

            try {
                workbook.close();
            } catch (IOException ioe) {
                // ignore
            }

        } catch (Exception e) {
            System.out.println("Error al escribir xls: " + e.getMessage());
            e.printStackTrace();
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException ioe) {
                    // ignore
                }
            }
        }
    }

    // Helpers
    private String getStringCellValue(Cell cell) {
        if (cell == null)
            return "";
        if (cell.getCellType() == CellType.STRING)
            return cell.getStringCellValue().trim();
        return cell.toString().trim();
    }

    private double getNumericCellValue(Cell cell) {
        if (cell == null)
            return 0.0;
        if (cell.getCellType() == CellType.NUMERIC)
            return cell.getNumericCellValue();
        String s = cell.toString().replace(',', '.').trim();
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null)
            return true;
        Iterator<Cell> it = row.cellIterator();
        while (it.hasNext()) {
            Cell c = it.next();
            if (c != null && c.getCellType() != CellType.BLANK && !getStringCellValue(c).isEmpty())
                return false;
        }
        return true;
    }

    private String nullToEmpty(String s) {
        return s == null ? "" : s;
    }
}