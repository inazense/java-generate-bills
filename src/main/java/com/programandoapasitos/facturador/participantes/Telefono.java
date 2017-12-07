package com.programandoapasitos.facturador.participantes;

import org.apache.commons.lang3.math.NumberUtils;

import com.programandoapasitos.facturador.excepciones.ExcepcionTelefonoInvalido;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;

public class Telefono {

	// Propiedades
	private String prefijo;
	private String numero;
	
	// Constructores
	public Telefono() {
		this.prefijo = "";
		this.numero = "";
	}
	
	public Telefono(String prefijo, String numero) throws ExcepcionTelefonoInvalido {
		this.prefijo = "";
		this.numero = "";
		
		this.setPrefijo(prefijo);
		this.setNumero(numero);
	}
	
	// Métodos
	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) throws ExcepcionTelefonoInvalido {
		
		if (this.numero.length() + prefijo.length() > 15) {
			throw new ExcepcionTelefonoInvalido(ManejadorProperties.verLiteral("PHONE_MORE_THAN_15_CHARACTERS"));
		}
		else if (!this.esPrefijoValido(prefijo)) {
			throw new ExcepcionTelefonoInvalido(ManejadorProperties.verLiteral("PREFIX_FORMAT_NOT_VALID"));
		}
		else {
			this.prefijo = prefijo;
		}
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) throws ExcepcionTelefonoInvalido {
		if (this.numero.length() + prefijo.length() > 15) {
			throw new ExcepcionTelefonoInvalido(ManejadorProperties.verLiteral("PHONE_MORE_THAN_15_CHARACTERS"));
		}
		else if (!NumberUtils.isDigits(numero)) {
			throw new ExcepcionTelefonoInvalido(ManejadorProperties.verLiteral("PHONE_NUMBER_NOT_NUMERIC"));
		}
		else {
			this.numero = numero;
		}
	}
	
	/**
	 * Comprueba si es un prefijo válido o no
	 * @param prefijo Prefijo del número
	 * @return True si es valido, false si no
	 */
	private boolean esPrefijoValido(String prefijo) {
		if (prefijo.substring(0, 1).equals("+") && NumberUtils.isDigits(prefijo.substring(1))) {
			return true;
		}
		return false;
	}
	
	/**
	 * Devuelve la cadena de un teléfono lista para imprimirse
	 * @return Cadena de teléfono con formato +00 000 00 00 00
	 */
	public String toString() {
		String result = prefijo + " ";
		String aux = "";
		boolean firstIteration = true;
		
		for (char i : this.numero.toCharArray()) {
			
			if (aux.length() == 0 && !firstIteration) {
				result += " ";
			}
			
			aux += i;
			
			if (aux.length() == 3 && firstIteration) {
				result += aux;
				aux = "";
				firstIteration = false;
			}
			
			if (aux.length() == 2 && !firstIteration) {
				result += aux;
				aux = "";
			}
		}
		result += aux; // En caso de número sin concatenar
		
		return result;
	}
}
