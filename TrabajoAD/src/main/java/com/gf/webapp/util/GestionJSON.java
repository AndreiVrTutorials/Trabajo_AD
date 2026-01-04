package com.gf.webapp.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gf.webapp.entity.DatosODS;

//REALIZADO POR SAMUEL CARNERO, debido a que no podia subir los cambios a github, me paso lo que hizo (Andrei) y los puse
public class GestionJSON {
	public static List<DatosODS> leerJSON(String rutaFichero) throws IOException {

        File fichero = new File(rutaFichero);

        // Si el fichero no existe o está vacío, devolvemos lista vacía
        if (!fichero.exists() || fichero.length() == 0) {
            return Collections.emptyList();
        }

        ObjectMapper mapper = new ObjectMapper();

        // Leemos el JSON como array de objetos DatosODS
        DatosODS[] arrayDatos = mapper.readValue(fichero, DatosODS[].class);

        // Convertimos el array en lista
        return Arrays.asList(arrayDatos);
    }
    
    public static void escribirJSON(String rutaFichero, DatosODS nuevoDato) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        File fichero = new File(rutaFichero);

        List<DatosODS> listaDatos;

        // Si el fichero existe y tiene contenido, lo leemos
        if (fichero.exists() && fichero.length() > 0) {
            DatosODS[] arrayDatos = mapper.readValue(fichero, DatosODS[].class);
            listaDatos = new java.util.ArrayList<>(Arrays.asList(arrayDatos));
        } else {
            // Si no existe o está vacío, empezamos una lista nueva
            listaDatos = new java.util.ArrayList<>();
        }

        // Añadimos el nuevo dato
        listaDatos.add(nuevoDato);

        // Guardamos la lista completa en el fichero
        mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, listaDatos);
    }
}
