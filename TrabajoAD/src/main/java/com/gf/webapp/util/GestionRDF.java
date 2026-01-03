package com.gf.webapp.util;

import java.io.File;             
import java.io.FileInputStream;  
import java.io.FileOutputStream; 
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.*; 

import com.gf.webapp.entity.DatosODS;

/*
Enlaces
https://jena.apache.org/tutorials/rdf_api.html
https://jena.apache.org/documentation/javadoc/jena/org.apache.jena.core/org/apache/jena/rdf/model/Model.html
https://jena.apache.org/documentation/javadoc/jena/org.apache.jena.core/org/apache/jena/rdf/model/Resource.html
https://jena.apache.org/documentation/javadoc/jena/org.apache.jena.core/org/apache/jena/rdf/model/ResIterator.html
https://jena.apache.org/documentation/io/
https://www.baeldung.com/apache-jena-resource-description-framework
https://jena.apache.org/
*/
public class GestionRDF {

    private static final String NS = "http://inventarioemisiones.madrid.org/";
    private static final String RUTA_RDF = "src/main/webapp/ficheros/archivo_inventario_emisiones.rdf";

    /**
     * ESCRITURA DE REGISTRO
     * 
     * @param datos Objeto DatosODS que queremos guardar
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
                System.out.println("Archivo RDF existente cargado.");
            } catch (Exception e) {
                System.out.println("Archivo RDF no encontrado. Se creará uno nuevo.");
            }

            Resource registro = model.createResource(NS + "registro_" + System.currentTimeMillis());            
            Property pAnio = model.createProperty(NS, "anio");
            Property pSectorSNAP = model.createProperty(NS, "sectorSNAP");
            Property pContaminante = model.createProperty(NS, "contaminante");
            Property pCantidad = model.createProperty(NS, "cantidad");
            Property pGrupo = model.createProperty(NS, "grupoUsuario"); 
            Property pCero = model.createProperty(NS, "cero");          
            Property pT = model.createProperty(NS, "t");                

            registro.addProperty(pAnio, String.valueOf(datos.getAnio()));
            registro.addProperty(pSectorSNAP, datos.getSector());
            registro.addProperty(pContaminante, datos.getContaminante());
            registro.addProperty(pCantidad, String.valueOf(datos.getCantidad()));
            registro.addProperty(pGrupo, "Grupo-Usuario");
            registro.addProperty(pCero, "0");
            registro.addProperty(pT, "t");

            try (FileOutputStream fos = new FileOutputStream(RUTA_RDF)) {
                model.write(fos, "RDF/XML-ABBREV"); 
                System.out.println("Registro RDF agregado correctamente.");
            }

        } catch (Exception e) {
            System.out.println("Ha ocurrido una excepción al escribir RDF:");
            e.printStackTrace();
        }
    }

    /**
     * LECTURA DE REGISTROS
     * 
     * @return Lista de objetos DatosODS leídos del archivo RDF
     */
    public List<DatosODS> leerRDF() {
        List<DatosODS> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(RUTA_RDF)) {

            Model model = ModelFactory.createDefaultModel();

            model.read(fis, null);

            Property pAnio = model.createProperty(NS, "anio");
            Property pSectorSNAP = model.createProperty(NS, "sectorSNAP");
            Property pContaminante = model.createProperty(NS, "contaminante");
            Property pCantidad = model.createProperty(NS, "cantidad");

            ResIterator it = model.listResourcesWithProperty(pAnio);

            while (it.hasNext()) {
                Resource r = it.nextResource();

                int anio = r.getProperty(pAnio).getInt();
                String sector = r.getProperty(pSectorSNAP).getString();
                String contaminante = r.getProperty(pContaminante).getString();
                double cantidad = r.getProperty(pCantidad).getDouble();

                DatosODS d = new DatosODS(anio, sector, contaminante, cantidad);
                lista.add(d);
            }

        } catch (Exception e) {
            System.out.println("Ha ocurrido una excepción al leer RDF:");
            e.printStackTrace();
        }

        return lista;
    }
}
