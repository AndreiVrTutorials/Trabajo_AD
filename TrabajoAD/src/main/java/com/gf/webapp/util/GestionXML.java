package com.gf.webapp.util;

import com.gf.webapp.entity.DatosODS;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GestionXML {

    // 🔹 LECTURA XML
    public List<DatosODS> leerXML(String rutaFichero) {

        List<DatosODS> lista = new ArrayList<>();

        try {
            File fichero = new File(rutaFichero);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fichero);

            doc.getDocumentElement().normalize();

            NodeList nodos = doc.getElementsByTagName("data");

            for (int i = 0; i < nodos.getLength(); i++) {

                Element e = (Element) nodos.item(i);

                int anio = Integer.parseInt(
                        e.getElementsByTagName("atm_inventario_año")
                                .item(0).getTextContent()
                );

                String grupo =
                        e.getElementsByTagName("atm_inventario_grupo_cont")
                                .item(0).getTextContent();

                String codigoSector =
                        e.getElementsByTagName("atm_inventario_sector")
                                .item(0).getTextContent()
                                .replace(",0", "");

                String sector =
                        e.getElementsByTagName("atm_inventario_sector_descripcion")
                                .item(0).getTextContent();

                String contaminante =
                        e.getElementsByTagName("atm_inventario_cont_desc")
                                .item(0).getTextContent();

                String unidad =
                        e.getElementsByTagName("atm_inventario_cont_unidades")
                                .item(0).getTextContent();

                double cantidad = Double.parseDouble(
                        e.getElementsByTagName("atm_inventario_cont_cantidad")
                                .item(0).getTextContent()
                );

                DatosODS dato = new DatosODS(
                        anio,
                        grupo,
                        codigoSector,
                        sector,
                        contaminante,
                        unidad,
                        cantidad
                );

                lista.add(dato);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void escribirXML(String rutaFichero, DatosODS d) {

        try {
            File fichero = new File(rutaFichero);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fichero);

            Element root = doc.getDocumentElement();

            Element data = doc.createElement("data");

            Element anio = doc.createElement("atm_inventario_año");
            anio.setTextContent(String.valueOf(d.getAnio()));
            data.appendChild(anio);

            Element grupo = doc.createElement("atm_inventario_grupo_cont");
            grupo.setTextContent(d.getGrupo());
            data.appendChild(grupo);

            Element sectorCod = doc.createElement("atm_inventario_sector");
            sectorCod.setTextContent(d.getCodigoSector());
            data.appendChild(sectorCod);

            Element sectorDesc = doc.createElement("atm_inventario_sector_descripcion");
            sectorDesc.setTextContent(d.getSector());
            data.appendChild(sectorDesc);

            Element contaminante = doc.createElement("atm_inventario_cont_desc");
            contaminante.setTextContent(d.getContaminante());
            data.appendChild(contaminante);

            Element unidad = doc.createElement("atm_inventario_cont_unidades");
            unidad.setTextContent(d.getUnidad());
            data.appendChild(unidad);

            Element cantidad = doc.createElement("atm_inventario_cont_cantidad");
            cantidad.setTextContent(String.valueOf(d.getCantidad()));
            data.appendChild(cantidad);

            root.appendChild(data);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(
                    new DOMSource(doc),
                    new StreamResult(fichero)
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
