package com.gf.webapp.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.gf.webapp.entity.DatosODS;

public class GestionRDF {

    private final File archivoRDF;

    public GestionRDF(String rutaRealRDF) {
        this.archivoRDF = new File(rutaRealRDF);
    }

    public List<DatosODS> leerRDF() {
        List<DatosODS> lista = new ArrayList<>();
        if (!archivoRDF.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoRDF))) {
            String line;
            while ((line = br.readLine()) != null) {
                int start = line.indexOf("\"");
                int end = line.lastIndexOf("\"");
                if (start >= 0 && end > start) {
                    String literal = line.substring(start + 1, end);
                    String[] partes = literal.split(";", -1);

                    if (partes.length >= 7) {
                        try {
                            DatosODS d = new DatosODS(
                                    Integer.parseInt(partes[0]),  
                                    partes[1],                    
                                    partes[2],                   
                                    partes[3],                   
                                    partes[4],                   
                                    partes[5],                    
                                    Double.parseDouble(partes[6]) 
                            );
                            lista.add(d);
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing numbers in: " + literal);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer el RDF");
            e.printStackTrace();
        }

        return lista;
    }
    
    public void escribirRDF(DatosODS datos) {
        try {
            List<String> lineas = new ArrayList<>();
            if (archivoRDF.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(archivoRDF))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        lineas.add(line);
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("<http://example.com/resource/row-")
              .append(System.currentTimeMillis())
              .append(">\n");
            sb.append("    rdf:type <http://schema.org/Thing>\n");
            sb.append("    prop:atm_inventario_a_o_atm_inventario_grupo_cont_")
              .append("atm_inventario_sector_atm_inventario_sector_descripcion_")
              .append("atm_inventario_cont_desc_atm_inventario_cont_unidades_")
              .append("atm_inventario_cont_cantidad \"")
              .append(datos.getAnio()).append(";")
              .append(datos.getGrupo()).append(";")
              .append(datos.getCodigoSector()).append(";")
              .append(datos.getSector()).append(";")
              .append(datos.getContaminante()).append(";")
              .append(datos.getUnidad()).append(";")
              .append(datos.getCantidad())
              .append("\" .\n");

            lineas.add(sb.toString());

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoRDF))) {
                for (String l : lineas) {
                    bw.write(l);
                    if (!l.endsWith("\n")) bw.newLine();
                }
            }

        } catch (Exception e) {
            System.err.println("Error al escribir el RDF");
            e.printStackTrace();
        }
    }
}
