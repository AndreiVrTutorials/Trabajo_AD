package com.gf.webapp.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gf.webapp.entity.DatosODS;

public class GestionXML {

    public static List<DatosODS> leerXML(String rutaFichero) throws IOException {

        File fichero = new File(rutaFichero);

        // Si el fichero no existe o está vacío
        if (!fichero.exists() || fichero.length() == 0) {
            return Collections.emptyList();
        }

        XmlMapper mapper = new XmlMapper();

        ListaDatosODS wrapper = mapper.readValue(fichero, ListaDatosODS.class);

        return wrapper.datosODS != null ? wrapper.datosODS : Collections.emptyList();
    }

    public static void escribirXML(String rutaFichero, DatosODS nuevoDato) throws IOException {

        File fichero = new File(rutaFichero);
        XmlMapper mapper = new XmlMapper();

        ListaDatosODS wrapper;
        List<DatosODS> listaDatos;

        // Si existe y tiene contenido
        if (fichero.exists() && fichero.length() > 0) {
            wrapper = mapper.readValue(fichero, ListaDatosODS.class);
            listaDatos = wrapper.datosODS != null
                    ? new ArrayList<>(wrapper.datosODS)
                    : new ArrayList<>();
        } else {
            wrapper = new ListaDatosODS();
            listaDatos = new ArrayList<>();
        }

        // Añadimos el nuevo dato
        listaDatos.add(nuevoDato);
        wrapper.datosODS = listaDatos;

        // Guardamos
        mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, wrapper);
    }
}
