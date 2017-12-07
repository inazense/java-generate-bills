package com.programandoapasitos.facturador.excepciones;

@SuppressWarnings("serial")
public class ExcepcionCamposNuevoCliente extends Exception {

	public ExcepcionCamposNuevoCliente() {}
	
	public ExcepcionCamposNuevoCliente(String mensaje) {
		super(mensaje);
	}
}
