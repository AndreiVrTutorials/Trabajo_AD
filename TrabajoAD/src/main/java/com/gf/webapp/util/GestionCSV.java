package com.gf.webapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.gf.webapp.entity.DatosODS;

public class GestionCSV {
	//TO DO: LA RUTA, HAY QUE "CALCULARLA" EN EL SERVLET, EL RESTO ESTA
	public List<DatosODS> leerCSV(String ruta) {
		/*
		 * Explicacion: transformamos el texto del csv en objetos para mostrarlos en la web
		 */
		List<DatosODS> lista = new ArrayList<>();
		File archivo = new File(ruta);
		
		if(!archivo.exists()) {
			System.out.println("No se encontro el archivo csv");
			return lista;
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
			String linea;
			boolean primeraLinea = true; //para saltar la cabecera
			while((linea = br.readLine())!= null) {
				if(primeraLinea) {
					primeraLinea = false;
				}else {
					String[] partes = linea.split(";");
					
					DatosODS dato = new DatosODS();
					
					dato.setAnio(Integer.parseInt(partes[0].trim()));
					dato.setSector(partes[3].trim());
					dato.setContaminante(partes[4].trim());
					
					String cantidad = partes[6].replace(",", ".").trim(); //cambiamos de coma a punto para evitar problemas
					dato.setCantidad(Double.parseDouble(cantidad));
					
					lista.add(dato);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error el la lectura del csv");
			e.printStackTrace();
		}
		
		return lista;
	}
	public void escribirCSV(String ruta, DatosODS datos) {
		/*
		 * Explicacion: cojemos los objetos y los transformamos en texto
		 */
		try (PrintWriter escribir = new PrintWriter(new FileWriter(ruta, true))){
			String linea = datos.getAnio() + ";" + "Grupo-Usuario" + ";" + "0" +";" + datos.getSector() + ";" 
					+ datos.getContaminante() + ";" + "t" + ";" + datos.getCantidad();
			//el 0 corresponde al id, y t a la unidad de medida
			escribir.println(linea);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ha ocurrido una excepcion: ");
			e.printStackTrace();
		}
	}
}
