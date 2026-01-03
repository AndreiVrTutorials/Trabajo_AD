package com.gf.webapp.util;

import java.io.FileWriter;
import java.io.PrintWriter;

import com.gf.webapp.entity.DatosODS;

public class GestionCSV {
	public void escribirCSV(String ruta, DatosODS datos) {
		try (PrintWriter escribir = new PrintWriter(new FileWriter(ruta, true))){
			String linea = datos.getAnio() + ";" + "Grupo-Usuario" + ";" + "0" +";" + datos.getSector() + ";" 
					+ datos.getContaminante() + ";" + "t" + ";" + datos.getCantidad();
			escribir.print(linea);
		}catch (Exception e) {
				// TODO: handle exception
			System.out.println("Ha ocurrido una excepcion: ");
			e.printStackTrace();
		}
	}
}
