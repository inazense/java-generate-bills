package com.programandoapasitos.facturador.gui;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.programandoapasitos.facturador.utiles.ManejadorProperties;

public class Mensajes {

	/**
	 * Imprime un mensaje de información genérico por pantalla
	 * @param mensaje
	 */
	public static void verMensajeInformacion(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje);
	}
	
	/**
	 * Imprime un mensaje de error con padre JDialog
	 * @param mensaje Error
	 * @param padre Padre para anidar el mensaje
	 */
	public static void verMensajeError(String mensaje, JDialog padre)  {
		JOptionPane.showMessageDialog(padre, mensaje, ManejadorProperties.verLiteral("ERROR"), JOptionPane.ERROR_MESSAGE);
	}
}
