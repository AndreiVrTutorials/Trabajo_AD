package com.gf.webapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gf.webapp.entity.DatosODS;
import com.gf.webapp.util.GestionCSV;
import com.gf.webapp.util.GestionJSON;
import com.gf.webapp.util.GestionXML;
import com.gf.webapp.util.GestionRDF;
import com.gf.webapp.util.GestionXLS;

@WebServlet("/ServletFich")
public class ServletFich extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServletFich() {
        super();
    }

  //REALIZADO POR SAMUEL CARNERO, debido a que no podia subir los cambios a github, me paso lo que hizo (Andrei) y los puse
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String formato = request.getParameter("formato");   
            String accion = request.getParameter("accion");    

            String anioStr = request.getParameter("anio");
            String sector = request.getParameter("sector");
            String contaminante = request.getParameter("contaminante");
            String cantidadStr = request.getParameter("cantidad");

            List<DatosODS> lista = new ArrayList<>();

            String rutaCSV = getServletContext().getRealPath("/ficheros/datos.csv");
            String rutaJSON = getServletContext().getRealPath("/ficheros/datos.json");
            String rutaXML = getServletContext().getRealPath("/ficheros/datos.xml");
            String rutaRDF = getServletContext().getRealPath("/ficheros/datos.rdf");
            String rutaXLS = getServletContext().getRealPath("/ficheros/datos.xls");

            if (formato.equalsIgnoreCase("CSV")) {

                GestionCSV gestor = new GestionCSV();

                if (accion.equalsIgnoreCase("escritura")) {

                    if (faltanDatos(anioStr, sector, contaminante, cantidadStr)) {
                        enviarError(request, response, "Faltan datos para escribir en CSV");
                        return;
                    }

                    DatosODS nuevo = crearObjeto(anioStr, sector, contaminante, cantidadStr);
                    gestor.escribirCSV(rutaCSV, nuevo);
                }

                lista = gestor.leerCSV(rutaCSV);
                enviarLista(request, response, lista);
                return;
            }

            if (formato.equalsIgnoreCase("JSON")) {

                if (accion.equalsIgnoreCase("escritura")) {

                    if (faltanDatos(anioStr, sector, contaminante, cantidadStr)) {
                        enviarError(request, response, "Faltan datos para escribir en JSON");
                        return;
                    }

                    DatosODS nuevo = crearObjeto(anioStr, sector, contaminante, cantidadStr);
                    GestionJSON.escribirJSON(rutaJSON, nuevo);
                }

                lista = GestionJSON.leerJSON(rutaJSON);
                enviarLista(request, response, lista);
                return;
            }

            if (formato.equalsIgnoreCase("XML")) {

                GestionXML gestor = new GestionXML();

                if (accion.equalsIgnoreCase("escritura")) {

                    if (faltanDatos(anioStr, sector, contaminante, cantidadStr)) {
                        enviarError(request, response, "Faltan datos para escribir en XML");
                        return;
                    }

                    DatosODS nuevo = crearObjeto(anioStr, sector, contaminante, cantidadStr);
                    gestor.escribirXML(rutaXML, nuevo);
                }

                lista = gestor.leerXML(rutaXML);
                enviarLista(request, response, lista);
                return;
            }

            if (formato.equalsIgnoreCase("RDF")) {

                GestionRDF gestor = new GestionRDF();

                if (accion.equalsIgnoreCase("escritura")) {

                    if (faltanDatos(anioStr, sector, contaminante, cantidadStr)) {
                        enviarError(request, response, "Faltan datos para escribir en RDF");
                        return;
                    }

                    DatosODS nuevo = crearObjeto(anioStr, sector, contaminante, cantidadStr);
                    gestor.escribirRDF(rutaRDF, nuevo);
                }

                lista = gestor.leerRDF(rutaRDF);
                enviarLista(request, response, lista);
                return;
            }

            if (formato.equalsIgnoreCase("XLS")) {

                GestionXLS gestor = new GestionXLS();

                if (accion.equalsIgnoreCase("escritura")) {

                    if (faltanDatos(anioStr, sector, contaminante, cantidadStr)) {
                        enviarError(request, response, "Faltan datos para escribir en XLS");
                        return;
                    }

                    DatosODS nuevo = crearObjeto(anioStr, sector, contaminante, cantidadStr);
                    gestor.escribirXLS(rutaXLS, nuevo);
                }

                lista = gestor.leerXLS(rutaXLS);
                enviarLista(request, response, lista);
                return;
            }

            enviarError(request, response, "Formato no soportado");

        } catch (Exception e) {
            enviarError(request, response, "Error técnico: " + e.getMessage());
        }
    }


    private boolean faltanDatos(String anio, String sector, String contaminante, String cantidad) {
        return anio == null || sector == null || contaminante == null || cantidad == null ||
               anio.isBlank() || sector.isBlank() || contaminante.isBlank() || cantidad.isBlank();
    }

    private DatosODS crearObjeto(String anioStr, String sector, String contaminante, String cantidadStr) {
        int anio = Integer.parseInt(anioStr);
        double cantidad = Double.parseDouble(cantidadStr);
        return new DatosODS(anio, sector, contaminante, cantidad);
    }

    private void enviarLista(HttpServletRequest request, HttpServletResponse response, List<DatosODS> lista)
            throws ServletException, IOException {
        request.setAttribute("lista", lista);
        request.getRequestDispatcher("AccesoDatosA.jsp").forward(request, response);
    }

    private void enviarError(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        request.setAttribute("error", mensaje);
        request.getRequestDispatcher("Error.jsp").forward(request, response);
    }
}