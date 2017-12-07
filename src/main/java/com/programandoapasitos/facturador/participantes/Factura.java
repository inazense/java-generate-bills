package com.programandoapasitos.facturador.participantes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.programandoapasitos.facturador.excepciones.ExcepcionFacturaInvalida;
import com.programandoapasitos.facturador.excepciones.ExcepcionPagoInvalido;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;

public class Factura {

	// Propiedades
	private String numeroFactura;
	private double iva;
	private String fecha;
	private Cliente cliente;
	private Vector<Pago> pagos;
	
	// Constructor
	public Factura() {
		this.pagos = new Vector<Pago>();
	}
	
	public Factura(String numeroFactura, Double iva, String fecha, Cliente cliente, Vector<Pago> pagos) throws ExcepcionFacturaInvalida, ExcepcionPagoInvalido {
		this.setNumeroFactura(numeroFactura);
		this.setPagos(pagos);
		this.setFecha(fecha);
		this.setIva(iva);
		this.setCliente(cliente);
	}
	
	// Métodos
	
	/**
	 * Calcula el total de pagos de los trabajos de la factura
	 * @return double
	 * @throws FileNotFoundException
	 * @throws ExcepcionPagoInvalido
	 * @throws IOException
	 */
	public double verTotalPagos() throws ExcepcionPagoInvalido {
		if (this.pagos.size() == 0 || this.pagos == null) {
			throw new ExcepcionPagoInvalido(ManejadorProperties.verLiteral("MORE_THAN_0_PAYMENTS"));
		}
		else {
			double cantidad = 0.0;
			
			for (Pago p : this.pagos) {
				cantidad += p.getCantidad();
			}
			cantidad += (cantidad * (this.iva / 100)); // Cálculo del IVA
			
			return cantidad;
		}
	}
	
	/**
	 * Agrega un pago al vector
	 * @param pago
	 * @return True si se ha agregado, false si no
	 */
	public boolean agregarPago(Pago pago) {
		return this.pagos.add(pago);
	}
	
	/**
	 * Borrar un pago del vector
	 * @param pago Pago a borrar
	 * @return True si se ha borrado, false si no
	 */
	public boolean borrarPago(Pago pago) {
		return this.pagos.remove(pago);
	}
	
	public String getNumeroFactura() {
		return numeroFactura;
	}
	
	public void setNumeroFactura(String numeroFactura) throws ExcepcionFacturaInvalida {
		this.numeroFactura = numeroFactura;
		if (numeroFactura.isEmpty() || numeroFactura == null) {
			throw new ExcepcionFacturaInvalida(ManejadorProperties.verLiteral("MANDATORY_BILL_NUMBER"));
		}
		else {
			this.numeroFactura = numeroFactura;
		}
	}
	
	public double getIva() {
		return iva;
	}
	
	public void setIva(double iva) {
		this.iva = iva;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) throws ExcepcionFacturaInvalida {
		if (!this.validarFechaFactura(fecha)) {
			throw new ExcepcionFacturaInvalida(ManejadorProperties.verLiteral("INVALID_DATE"));
		}
		else {
			this.fecha = fecha;
		}
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Vector<Pago> getPagos() {
		return pagos;
	}
	
	public void setPagos(Vector<Pago> pagos) throws ExcepcionPagoInvalido {
		if (pagos.size() == 0) {
			throw new ExcepcionPagoInvalido(ManejadorProperties.verLiteral("MORE_THAN_0_PAYMENTS"));
		}
		this.pagos = pagos;
	}
	
	private boolean validarFechaFactura(String fecha) {
		
		boolean resultado = true;
		
		try {
			int dia = Integer.parseInt(fecha.substring(0, 2));
			int mes = Integer.parseInt(fecha.substring(3, 5));
			int agno = Integer.parseInt(fecha.substring(6, 10));
			
			if (mes < 1 || mes > 12) {
				resultado = false;
			}
			else {
				if (mes == 2) {
					if ((agno % 4 == 0) && ((agno % 100 != 0) || (agno % 400 == 0))) {
						if (dia < 1 || dia > 29) {
							resultado = false;
						}
					}
					else {
						if (dia < 1 || dia > 28) {
							resultado = false;
						}
					}
				}
				else if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
					if (dia < 1 || dia > 31) {
						resultado = false;
					}
				}
				else {
					if (dia < 1 || dia > 30) {
						resultado = false;
					}
				}
			}
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, ManejadorProperties.verLiteral("NUM_DATE_EXCEPTION)"));
			resultado = false;
		}
		
		return resultado;
	}
}

































