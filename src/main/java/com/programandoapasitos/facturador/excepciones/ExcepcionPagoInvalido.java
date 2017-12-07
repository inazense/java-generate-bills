package com.programandoapasitos.facturador.excepciones;

@SuppressWarnings("serial")
public class ExcepcionPagoInvalido extends Exception {

	public ExcepcionPagoInvalido() {}
	
	public ExcepcionPagoInvalido(String mensaje) {
		super(mensaje);
	}
}
