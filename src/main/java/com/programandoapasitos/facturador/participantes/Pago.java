package com.programandoapasitos.facturador.participantes;

import com.programandoapasitos.facturador.excepciones.ExcepcionPagoInvalido;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;

public class Pago {

	// Propiedades
	private String concepto;
	private double cantidad;
	
	// Constructores
	public Pago() {}
	
	public Pago(String concepto, Double cantidad) throws ExcepcionPagoInvalido {
		
		this.setConcepto(concepto);
		this.setCantidad(cantidad);
	}
	
	// Métodos
	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) throws ExcepcionPagoInvalido {
		if (concepto.isEmpty() || concepto == null) {
			throw new ExcepcionPagoInvalido(ManejadorProperties.verLiteral("EMPTY_PAYMENT_CONCEPT"));
		}
		else {
			this.concepto = concepto;
		}
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) throws ExcepcionPagoInvalido {
		if (cantidad == null) {
			throw new ExcepcionPagoInvalido(ManejadorProperties.verLiteral("NO_AMOUNT"));
		}
		else if (cantidad < 0.0) {
			throw new ExcepcionPagoInvalido(ManejadorProperties.verLiteral("NEGATIVE_AMOUNT"));
		}
		else {
			this.cantidad = cantidad;
		}
	}
}
