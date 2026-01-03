package com.gf.webapp.entity;

public class DatosODS {
	private int anio;
	private String sector;
	private String contaminante;
	private double cantidad;
	
	public DatosODS() {}

	public DatosODS(int anio, String sector, String contaminante, double cantidad) {
		super();
		this.anio = anio;
		this.sector = sector;
		this.contaminante = contaminante;
		this.cantidad = cantidad;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
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

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "DatosODS [anio=" + anio + ", sector=" + sector + ", contaminante=" + contaminante + ", cantidad="
				+ cantidad + "]";
	}
	
	
}
