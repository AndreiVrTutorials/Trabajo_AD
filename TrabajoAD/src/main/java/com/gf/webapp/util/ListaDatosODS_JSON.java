package com.gf.webapp.util;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gf.webapp.entity.DatosODS;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListaDatosODS_JSON {

    @JsonProperty("data")
    private List<DatosODS> data;

    public ListaDatosODS_JSON() {}

    public List<DatosODS> getData() {
        return data;
    }

    public void setData(List<DatosODS> data) {
        this.data = data;
    }
}
