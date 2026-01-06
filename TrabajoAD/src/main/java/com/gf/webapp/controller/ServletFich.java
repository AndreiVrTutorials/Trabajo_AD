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

//REALIZADO POR SAMUEL CARNERO, debido a que no podia subir los cambios a github, me paso lo que hizo (Andrei) y los puse
@WebServlet("/ServletFich")
public class ServletFich extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServletFich() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String formato = request.getParameter("formato");
            String accion = request.getParameter("accion");

            String anioStr = request.getParameter("anio");
            String grupo = request.getParameter("grupo");
            String codigo = request.getParameter("codigoSector");
            String sector = request.getParameter("sector");
            String contaminante = request.getParameter("contaminante");
            String unidad = request.getParameter("unidad");
            String cantidadStr = request.getParameter("cantidad");

            List<DatosODS> lista = new ArrayList<>();

            String rutaCSV = getServletContext().getRealPath("/ficheros/atmosfera_inventario_emisiones.csv");
            String rutaJSON = getServletContext().getRealPath("/ficheros/datos.json");
            String rutaXML = getServletContext().getRealPath("/ficheros/datos.xml");
            String rutaRDF = getServletContext().getRealPath("/ficheros/datos.rdf");
            String rutaXLS = getServletContext().getRealPath("/ficheros/datos.xls");

            if (formato.equalsIgnoreCase("CSV")) {
                GestionCSV gestor = new GestionCSV();

                if (accion.equalsIgnoreCase("escritura")) {
                    if (faltanDatos(anioStr, grupo, codigo, sector, contaminante, unidad, cantidadStr)) {
                        enviarError(request, response, "Faltan datos para escribir en CSV");
                        return;
                    }
                    DatosODS nuevo = crearObjeto(anioStr, grupo, codigo, sector, contaminante, unidad, cantidadStr);
                    gestor.escribirCSV(rutaCSV, nuevo);
                }
                lista = gestor.leerCSV(rutaCSV);
                enviarLista(request, response, lista);
                return;
            }

            if (formato.equalsIgnoreCase("JSON")) {
                if (accion.equalsIgnoreCase("escritura")) {
                    if (faltanDatos(anioStr, grupo, codigo, sector, contaminante, unidad, cantidadStr)) {
                        enviarError(request, response, "Faltan datos para escribir en JSON");
                        return;
                    }
                    DatosODS nuevo = crearObjeto(anioStr, grupo, codigo, sector, contaminante, unidad, cantidadStr);
                    GestionJSON.escribirJSON(rutaJSON, nuevo);
                }
                lista = GestionJSON.leerJSON(rutaJSON);
                enviarLista(request, response, lista);
                return;
            }

            if (formato.equalsIgnoreCase("XML")) {
                GestionXML gestor = new GestionXML();
                if (accion.equalsIgnoreCase("escritura")) {
                    if (faltanDatos(anioStr, grupo, codigo, sector, contaminante, unidad, cantidadStr)) {
                        enviarError(request, response, "Faltan datos para escribir en XML");
                        return;
                    }
                    DatosODS nuevo = crearObjeto(anioStr, grupo, codigo, sector, contaminante, unidad, cantidadStr);
                    gestor.escribirXML(rutaXML, nuevo);
                }
                lista = gestor.leerXML(rutaXML);
                enviarLista(request, response, lista);
                return;
            }

            if (formato.equalsIgnoreCase("RDF")) {
                GestionRDF gestor = new GestionRDF();
                if (accion.equalsIgnoreCase("escritura")) {
                    if (faltanDatos(anioStr, grupo, codigo, sector, contaminante, unidad, cantidadStr)) {
                        enviarError(request, response, "Faltan datos para escribir en RDF");
                        return;
                    }
                    DatosODS nuevo = crearObjeto(anioStr, grupo, codigo, sector, contaminante, unidad, cantidadStr);
                    gestor.escribirRDF(nuevo);
                }
                lista = gestor.leerRDF();
                enviarLista(request, response, lista);
                return;
            }

            if (formato.equalsIgnoreCase("XLS")) {
                GestionXLS gestor = new GestionXLS();
                if (accion.equalsIgnoreCase("escritura")) {
                    if (faltanDatos(anioStr, grupo, codigo, sector, contaminante, unidad, cantidadStr)) {
                        enviarError(request, response, "Faltan datos para escribir en XLS");
                        return;
                    }
                    DatosODS nuevo = crearObjeto(anioStr, grupo, codigo, sector, contaminante, unidad, cantidadStr);
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

    private boolean faltanDatos(String anio, String grupo, String codigo, String sector, String contaminante, String unidad, String cantidad) {
        return anio == null || grupo == null || codigo == null || sector == null ||
               contaminante == null || unidad == null || cantidad == null ||
               anio.isBlank() || grupo.isBlank() || codigo.isBlank() || sector.isBlank() ||
               contaminante.isBlank() || unidad.isBlank() || cantidad.isBlank();
    }

    private DatosODS crearObjeto(String anioStr, String grupo, String codigo, String sector, String contaminante, String unidad, String cantidadStr) {
        anioStr = anioStr.trim();
        grupo = grupo.trim();
        codigo = codigo.trim();
        sector = sector.trim();
        contaminante = contaminante.trim();
        unidad = unidad.trim();
        cantidadStr = cantidadStr.trim();

        int anio = Integer.parseInt(anioStr);
        double cantidad = Double.parseDouble(cantidadStr.replace(",", "."));

        return new DatosODS(anio, grupo, codigo, sector, contaminante, unidad, cantidad);
    }

    private void enviarLista(HttpServletRequest request, HttpServletResponse response, List<DatosODS> lista)
            throws ServletException, IOException {
        request.setAttribute("lista", lista);
        request.getRequestDispatcher("AccesoDatosA.jsp").forward(request, response);
    }

    private void enviarError(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.getRequestDispatcher("TratamientoFich.jsp").forward(request, response);
    }
}