package com.programandoapasitos.facturador.participantes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.programandoapasitos.facturador.excepciones.ExcepcionEmailInvalido;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;

public class Email {

	// Propiedades
	private String email;
	
	// Constructores
	public Email() {}
	
	public Email(String email) throws ExcepcionEmailInvalido {
		this.setEmail(email);
	}
	
	// Métodos
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws ExcepcionEmailInvalido {
		
		if (!this.validarEmail(email)) {
			throw new ExcepcionEmailInvalido(ManejadorProperties.verLiteral("EMAIL_NOT_VALID"));
		}
		else {
			this.email = email;
		}
	}
	
	/**
	 * Valida la dirección de correo electrónico
	 * @param email Dirección a comprobar
	 * @return True si es válido, false en caso contrario
	 */
	private boolean validarEmail(String email) {
		
		// Expresion regular para validar mails
		String expresion = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		// Compilar la expresión regular en un patron
		Pattern patron = Pattern.compile(expresion);
		
		// Comparo el correo electrónico contra el patrón
		Matcher comparador = patron.matcher(email);
		return comparador.matches();
	}
	
	
}
