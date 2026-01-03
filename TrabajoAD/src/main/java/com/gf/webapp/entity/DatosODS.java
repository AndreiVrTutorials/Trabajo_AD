package com.gf.webapp.entity;

public class DatosODS {
	private int anio;
	private String grupo;
	private String codigoSector;
	private String sector;
	private String contaminante;
	private String unidad;
	private double cantidad;
	
	public DatosODS() {}

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
