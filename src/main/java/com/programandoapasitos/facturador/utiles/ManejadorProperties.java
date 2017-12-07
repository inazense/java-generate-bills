package com.programandoapasitos.facturador.utiles;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import com.programandoapasitos.facturador.gui.Mensajes;

public class ManejadorProperties {

	// Propiedades
	private String rutaLiterales = "configuracion/literales.properties";
	private String rutaGui = "configuracion/gui.properties";
	private String rutaRutas = "configuracion/rutas.properties";
	private String rutaPdf = "configuracion/pdf.properties";
	
	private static Properties propiedadesLiterales = null;
	private static Properties propiedadesGui = null;
	private static Properties propiedadesRutas = null;
	private static Properties propiedadesPdf = null;
	
	// Constructor
	private ManejadorProperties() throws FileNotFoundException, IOException {
		propiedadesLiterales = new Properties();
		propiedadesLiterales.load(new FileReader(rutaLiterales));
		
		propiedadesGui = new Properties();
		propiedadesGui.load(new FileReader(rutaGui));
		
		propiedadesRutas = new Properties();
		propiedadesRutas.load(new FileReader(rutaRutas));
		
		propiedadesPdf = new Properties();
		propiedadesPdf.load(new FileReader(rutaPdf));
	}
	
	// Métodos
	/**
	 * Lee una propiedad del fichero .properties indicado por parámetro
	 * @param modo 0 -> Literales, 1 -> Gui
	 * @param miClave Clave de la propiedad
	 * @return Valor de la propiedad dependiente de miClave
	 * @throws FileNotFoundException Si no encuentra el fichero properties
	 * @throws IOException Si tiene problemas para trabajar con dichero fichero
	 */
	private static String leerPropiedad(int modo, String miClave) throws FileNotFoundException, IOException {
		
		if (propiedadesLiterales == null || propiedadesGui == null || propiedadesRutas == null || propiedadesPdf == null) {
			new ManejadorProperties();
		}
		String resultado = "";
		Enumeration<Object> claves = null;
		
		switch (modo) {
			case 0:
				claves = propiedadesLiterales.keys();
				break;
			case 1:
				claves = propiedadesGui.keys();
				break;
			case 2:
				claves = propiedadesRutas.keys();
				break;
			case 3:
				claves = propiedadesPdf.keys();
			default:
				break;
		}
		
		while (claves.hasMoreElements()) {
			Object clave = claves.nextElement();
			
			if (clave.toString().equals(miClave)) {
				switch (modo) {
					case 0:
						resultado = propiedadesLiterales.get(clave).toString();
						break;
					case 1:
						resultado = propiedadesGui.get(clave).toString();
						break;
					case 2:
						resultado = propiedadesRutas.get(clave).toString();
						break;
					case 3:
						resultado = propiedadesPdf.get(clave).toString();
					default:
						break;
				}
				break;
			}
		}
		return resultado;
	}
	
	/**
	 * Lee un literal del fichero de propiedadesLiterales
	 * @param miClave Clave de la propiedad
	 * @return Valor de la propiedad
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String verLiteral(String miClave) {
		
		String resultado = "";
		
		try {
			resultado = leerPropiedad(0, miClave);
		} catch (IOException e) {
			Mensajes.verMensajeError("Properties Literales --> " + e.getMessage(), null);
		}
		
		return resultado;
	}
	
	/**
	 * Lee una propiedad del fichero propiedadesGui
	 * @param miClave Clave de la propiedad
	 * @return Valor de la propiedad
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static int verGui(String miClave) {
		
		int resultado = -1;
		
		try {
			resultado = Integer.parseInt(leerPropiedad(1, miClave));
		} catch (NumberFormatException | IOException e) {
			Mensajes.verMensajeError("Properties GUI --> " + e.getMessage(), null);
		}
		
		return resultado;
	}
	
	/**
	 * Lee un literal del fichero de propiedadesRut
	 * @param miClave
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String verRuta(String miClave) {
		
		String resultado = "";
		
		try {
			resultado = leerPropiedad(2, miClave);
		} 
		catch (IOException e) {
			Mensajes.verMensajeError("Properties Rutas --> " + e.getMessage(), null);
		}
		
		return resultado;
	}
	
	/**
	 * Lee una propiedad del fichero propiedadesPdf
	 * @param miClave Clave de la propiedad
	 * @return Valor de la propiedad
	 * @throws NumberFormatException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static int verPDF(String miClave) {
		
		int resultado = -1;
		try {
			resultado = Integer.parseInt(leerPropiedad(3, miClave));
		} 
		catch (NumberFormatException | IOException e) {
			Mensajes.verMensajeError("Properties PDF --> " + e.getMessage(), null);
		}
		
		return resultado;
	}
}
