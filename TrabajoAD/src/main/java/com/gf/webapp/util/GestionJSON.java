package com.gf.webapp.util;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gf.webapp.entity.DatosODS;

public class GestionJSON {

    public static List<DatosODS> leerJSON(String rutaFichero) throws IOException {

        File fichero = new File(rutaFichero);

        if (!fichero.exists() || fichero.length() == 0) {
            return Collections.emptyList();
        }

        ObjectMapper mapper = new ObjectMapper();

        ListaDatosODS_JSON wrapper =
                mapper.readValue(fichero, ListaDatosODS_JSON.class);

        return wrapper.getData() != null
                ? wrapper.getData()
                : Collections.emptyList();
    }

    public static void escribirJSON(String rutaFichero, DatosODS nuevoDato) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        File fichero = new File(rutaFichero);

        ListaDatosODS_JSON wrapper;
        List<DatosODS> lista;

        if (fichero.exists() && fichero.length() > 0) {
            wrapper = mapper.readValue(fichero, ListaDatosODS_JSON.class);
            lista = wrapper.getData() != null
                    ? new ArrayList<>(wrapper.getData())
                    : new ArrayList<>();
        } else {
            wrapper = new ListaDatosODS_JSON();
            lista = new ArrayList<>();
        }

        lista.add(nuevoDato);
        wrapper.setData(lista);

        mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, wrapper);
    }
}
