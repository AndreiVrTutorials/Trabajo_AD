package com.gf.webapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.*;

import com.gf.webapp.entity.DatosODS;

/**
 * Gestión de lectura y escritura RDF para el Inventario de Emisiones
 * a la Atmósfera de la Comunidad de Madrid (SNAP).
 */
public class GestionRDF {

    private static final String NS = "http://inventarioemisiones.madrid.org/";
    private static final String RUTA_RDF =
            "src/main/webapp/ficheros/archivo_inventario_emisiones.rdf";

    /**
     * Guarda un objeto DatosODS como un recurso RDF
     */
    public void escribirRDF(DatosODS datos) {

        try {
            Model model = ModelFactory.createDefaultModel();

            File carpeta = new File("src/main/webapp/ficheros");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            try (FileInputStream fis = new FileInputStream(RUTA_RDF)) {
                model.read(fis, null);
            } catch (Exception e) {
                System.out.println("Archivo RDF no existe, se creará uno nuevo.");
            }

            Resource registro = model.createResource(
                    NS + "registro_" + System.currentTimeMillis());

            Property pAnio = model.createProperty(NS, "anio");
            Property pGrupo = model.createProperty(NS, "grupo");
            Property pCodigoSector = model.createProperty(NS, "codigoSector");
            Property pSector = model.createProperty(NS, "sector");
            Property pContaminante = model.createProperty(NS, "contaminante");
            Property pUnidad = model.createProperty(NS, "unidad");
            Property pCantidad = model.createProperty(NS, "cantidad");

            registro.addProperty(pAnio, String.valueOf(datos.getAnio()));
            registro.addProperty(pGrupo, datos.getGrupo());
            registro.addProperty(pCodigoSector, datos.getCodigoSector());
            registro.addProperty(pSector, datos.getSector());
            registro.addProperty(pContaminante, datos.getContaminante());
            registro.addProperty(pUnidad, datos.getUnidad());
            registro.addProperty(pCantidad, String.valueOf(datos.getCantidad()));

            try (FileOutputStream fos = new FileOutputStream(RUTA_RDF)) {
                model.write(fos, "RDF/XML-ABBREV");
            }

        } catch (Exception e) {
            System.out.println("Error al escribir el RDF");
            e.printStackTrace();
        }
    }

    /**
     * Lee el archivo RDF y devuelve una lista de DatosODS
     */
    public List<DatosODS> leerRDF() {

        List<DatosODS> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(RUTA_RDF)) {

            Model model = ModelFactory.createDefaultModel();
            model.read(fis, null);

            Property pAnio = model.createProperty(NS, "anio");
            Property pGrupo = model.createProperty(NS, "grupo");
            Property pCodigoSector = model.createProperty(NS, "codigoSector");
            Property pSector = model.createProperty(NS, "sector");
            Property pContaminante = model.createProperty(NS, "contaminante");
            Property pUnidad = model.createProperty(NS, "unidad");
            Property pCantidad = model.createProperty(NS, "cantidad");

            ResIterator it = model.listResourcesWithProperty(pAnio);

            while (it.hasNext()) {
                Resource r = it.nextResource();

                int anio = r.getProperty(pAnio).getInt();
                String grupo = r.getProperty(pGrupo).getString();
                String codigoSector = r.getProperty(pCodigoSector).getString();
                String sector = r.getProperty(pSector).getString();
                String contaminante = r.getProperty(pContaminante).getString();
                String unidad = r.getProperty(pUnidad).getString();
                double cantidad = r.getProperty(pCantidad).getDouble();

                DatosODS d = new DatosODS(
                        anio,
                        grupo,
                        codigoSector,
                        sector,
                        contaminante,
                        unidad,
                        cantidad
                );

                lista.add(d);
            }

        } catch (Exception e) {
            System.out.println("Error al leer el RDF");
            e.printStackTrace();
        }

        return lista;
    }
}
