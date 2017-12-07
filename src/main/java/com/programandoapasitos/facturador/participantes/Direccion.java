package com.programandoapasitos.facturador.participantes;

public class Direccion {

	// Propiedades
	private String calle;
	private String codigoPostal;
	private String localidad;
	private String provincia;
	
	// Constructor
	public Direccion() {}
	
	public Direccion(String calle, String codigoPostal, String localidad, String provincia) {
		this.calle = calle;
		this.codigoPostal = codigoPostal;
		this.localidad = localidad;
		this.provincia = provincia;
	}
	
	// Métodos
	
	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	
}
