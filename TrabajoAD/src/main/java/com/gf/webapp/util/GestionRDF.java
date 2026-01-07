package com.gf.webapp.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.*;

import com.gf.webapp.entity.DatosODS;

public class GestionRDF {

    private static final String NS =
            "http://inventarioemisiones.madrid.org/";

    private final File archivoRDF;

    public GestionRDF(String rutaRealRDF) {
        this.archivoRDF = new File(rutaRealRDF);
    }

    // ESCRITURA RDF
    public void escribirRDF(DatosODS datos) {

        try {
            Model model = ModelFactory.createDefaultModel();

            // Leer RDF existente SOLO si existe
            if (archivoRDF.exists()) {
                try (FileInputStream fis =
                             new FileInputStream(archivoRDF)) {
                    model.read(fis, null);
                }
            }

            Resource registro = model.createResource(
                    NS + "registro_" + System.currentTimeMillis());

            Property pAnio = model.createProperty(NS, "anio");
            Property pGrupo = model.createProperty(NS, "grupo");
            Property pCodigoSector =
                    model.createProperty(NS, "codigoSector");
            Property pSector = model.createProperty(NS, "sector");
            Property pContaminante =
                    model.createProperty(NS, "contaminante");
            Property pUnidad = model.createProperty(NS, "unidad");
            Property pCantidad = model.createProperty(NS, "cantidad");

            registro.addProperty(pAnio,
                    String.valueOf(datos.getAnio()));
            registro.addProperty(pGrupo, datos.getGrupo());
            registro.addProperty(pCodigoSector,
                    datos.getCodigoSector());
            registro.addProperty(pSector, datos.getSector());
            registro.addProperty(pContaminante,
                    datos.getContaminante());
            registro.addProperty(pUnidad, datos.getUnidad());
            registro.addProperty(pCantidad,
                    String.valueOf(datos.getCantidad()));

            try (FileOutputStream fos =
                         new FileOutputStream(archivoRDF)) {
                model.write(fos, "RDF/XML-ABBREV");
            }

        } catch (Exception e) {
            System.err.println(
                "Error al escribir el RDF");
            e.printStackTrace();
        }
    }

    // LECTURA RDF
    public List<DatosODS> leerRDF() {

        List<DatosODS> lista = new ArrayList<>();

        if (!archivoRDF.exists()) {
            return lista;
        }

        try (FileInputStream fis =
                     new FileInputStream(archivoRDF)) {

            Model model = ModelFactory.createDefaultModel();
            model.read(fis, null);

            Property pAnio = model.createProperty(NS, "anio");
            Property pGrupo = model.createProperty(NS, "grupo");
            Property pCodigoSector =
                    model.createProperty(NS, "codigoSector");
            Property pSector = model.createProperty(NS, "sector");
            Property pContaminante =
                    model.createProperty(NS, "contaminante");
            Property pUnidad = model.createProperty(NS, "unidad");
            Property pCantidad = model.createProperty(NS, "cantidad");

            ResIterator it =
                    model.listResourcesWithProperty(pAnio);

            while (it.hasNext()) {
                Resource r = it.nextResource();

                DatosODS d = new DatosODS(
                        r.getProperty(pAnio).getInt(),
                        r.getProperty(pGrupo).getString(),
                        r.getProperty(pCodigoSector).getString(),
                        r.getProperty(pSector).getString(),
                        r.getProperty(pContaminante).getString(),
                        r.getProperty(pUnidad).getString(),
                        r.getProperty(pCantidad).getDouble()
                );

                lista.add(d);
            }

        } catch (Exception e) {
            System.err.println(
                "Error al leer el RDF");
            e.printStackTrace();
        }

        return lista;
    }
}
