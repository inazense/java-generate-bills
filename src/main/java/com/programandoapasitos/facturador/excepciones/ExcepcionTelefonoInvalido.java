package com.programandoapasitos.facturador.excepciones;

@SuppressWarnings("serial")
public class ExcepcionTelefonoInvalido extends Exception {

	public ExcepcionTelefonoInvalido() {}
	
	public ExcepcionTelefonoInvalido(String mensaje) {
		super(mensaje);
	}
}
