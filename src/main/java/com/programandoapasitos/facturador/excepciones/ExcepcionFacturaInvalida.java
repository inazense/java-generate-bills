package com.programandoapasitos.facturador.excepciones;

@SuppressWarnings("serial")
public class ExcepcionFacturaInvalida extends Exception {

	public ExcepcionFacturaInvalida() {}
	
	public ExcepcionFacturaInvalida(String mensaje) {
		super(mensaje);
	}
}
