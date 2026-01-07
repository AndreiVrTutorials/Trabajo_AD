package com.gf.webapp.util;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.gf.webapp.entity.DatosODS;

@JacksonXmlRootElement(localName = "inventario")
public class ListaDatosODS {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "registro")
    public List<DatosODS> datosODS;

    public ListaDatosODS() {
    }
}
