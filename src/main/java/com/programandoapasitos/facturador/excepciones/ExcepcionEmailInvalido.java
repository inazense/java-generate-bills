package com.programandoapasitos.facturador.excepciones;

@SuppressWarnings("serial")
public class ExcepcionEmailInvalido extends Exception {

	public ExcepcionEmailInvalido() {}
	
	public ExcepcionEmailInvalido(String mensaje) {
		super(mensaje);
	}
}
