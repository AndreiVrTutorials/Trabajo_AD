package com.gf.webapp.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosODS {
	@JsonProperty("atm_inventario_año")
    private int anio;

    @JsonProperty("atm_inventario_grupo_cont")
    private String grupo;

    @JsonProperty("atm_inventario_sector")
    private String codigoSector;

    @JsonProperty("atm_inventario_sector_descripcion")
    private String sector;

    @JsonProperty("atm_inventario_cont_desc")
    private String contaminante;

    @JsonProperty("atm_inventario_cont_unidades")
    private String unidad;

    @JsonProperty("atm_inventario_cont_cantidad")
    private double cantidad;
	
	public DatosODS() {}
	
	public DatosODS(int anio, String sector, String contaminante, double cantidad) {
		super();
		this.anio = anio;
		this.sector = sector;
		this.contaminante = contaminante;
		this.cantidad = cantidad;
	}

	public DatosODS(int anio, String grupo, String codigoSector, String sector, String contaminante, String unidad,
			double cantidad) {
		super();
		this.anio = anio;
		this.grupo = grupo;
		this.codigoSector = codigoSector;
		this.sector = sector;
		this.contaminante = contaminante;
		this.unidad = unidad;
		this.cantidad = cantidad;
	}
	
	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getCodigoSector() {
		return codigoSector;
	}

	public void setCodigoSector(String codigoSector) {
		this.codigoSector = codigoSector;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getContaminante() {
		return contaminante;
	}

	public void setContaminante(String contaminante) {
		this.contaminante = contaminante;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "DatosODS [anio=" + anio + ", grupo=" + grupo + ", codigoSector=" + codigoSector + ", sector=" + sector
				+ ", contaminante=" + contaminante + ", unidad=" + unidad + ", cantidad=" + cantidad + "]";
	}

	
	
}
