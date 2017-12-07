package com.programandoapasitos.facturador.utiles;

public class ManejadorCalculos {

	/**
	 * Calcula el IVA de una cantidad
	 * @param cantidad Cantidad sobre la que calcular el IVA
	 * @param iva IVA a aplicar
	 * @return Devuelve la cantidad de dinero después de aplicar el IVA
	 */
	public static double calcularIVA(double cantidad, double iva) {
		return (cantidad / 100) * iva;
	}
}
