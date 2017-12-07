package com.programandoapasitos.facturador.utiles;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.programandoapasitos.facturador.participantes.Factura;
import com.programandoapasitos.facturador.participantes.Pago;

public class ManejadorPDF {

	// Participantes
	private String rutaFacturaGenerica;
	private String rutaFacturaImprimible;
	private String nombrePdfSalida;
	private String rutaCarpetaSalida;
	private String impresoraSalida;
	
	private PdfReader lectorPdf;
	private PdfStamper stamperPdf;
	private BaseFont fuenteBase;
	private PdfContentByte contenido;
	
	private Font fuente;
	
	private final String[] alineamientos = {"left", "right"};
	
	// Constructor
	public ManejadorPDF(String nombrePDF) {
		this.rutaCarpetaSalida = ManejadorProperties.verRuta("PDF_CARPETA_SALIDA");
		this.impresoraSalida = ManejadorProperties.verRuta("PDF_TMP") + nombrePDF;
		this.nombrePdfSalida = this.rutaCarpetaSalida + nombrePDF;
		this.rutaFacturaGenerica = ManejadorProperties.verRuta("FACTURA_GENERICA");
		this.rutaFacturaImprimible = ManejadorProperties.verRuta("FACTURA_VACIA");
		this.fuente = FontFactory.getFont(ManejadorProperties.verRuta("FUENTE"));
	}
	
	// Métodos
	
	/**
	 * Genera el PDF de una factura
	 * @param f Factura a generar
	 * @param modo 0 = documento PDF, 1 = versión imprimible (cambian los logos)
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void crearFactura(Factura f, int modo) throws IOException, DocumentException {
		
		if (modo == 0) { // PDF
			this.lectorPdf = new PdfReader(this.rutaFacturaGenerica);
			this.stamperPdf = new PdfStamper(this.lectorPdf, new FileOutputStream(this.nombrePdfSalida));
		}
		else { // Printer
			this.lectorPdf = new PdfReader(this.rutaFacturaImprimible);
			this.stamperPdf = new PdfStamper(this.lectorPdf, new FileOutputStream(this.impresoraSalida));
		}
		
		this.fuenteBase = this.fuente.getBaseFont();
		
		for (int i = 1; i <= this.lectorPdf.getNumberOfPages(); i++) {
			
			this.contenido = this.stamperPdf.getOverContent(i);
			this.contenido.beginText();
			this.contenido.setFontAndSize(this.fuenteBase, 10);
			
			// Información básica del cliente
			this.imprimirTexto(ManejadorProperties.verPDF("CLIENTE_NOMBRE_X"), ManejadorProperties.verPDF("CLIENTE_NOMBRE_Y"), f.getCliente().getNombre() + " " + f.getCliente().getApellidos(), this.alineamientos[0]);
			this.imprimirTexto(ManejadorProperties.verPDF("CLIENTE_DNI_X"), ManejadorProperties.verPDF("CLIENTE_DNI_Y"), f.getCliente().getDni(), this.alineamientos[0]);
			this.imprimirTexto(ManejadorProperties.verPDF("CLIENTE_DIRECCION_X"), ManejadorProperties.verPDF("CLIENTE_DIRECCION_Y"), f.getCliente().getDireccion().getCalle(), this.alineamientos[0]);
			this.imprimirTexto(ManejadorProperties.verPDF("CLIENTE_CODPOSTAL_X"), ManejadorProperties.verPDF("CLIENTE_CODPOSTAL_Y"), f.getCliente().getDireccion().getCodigoPostal() + ", " + f.getCliente().getDireccion().getLocalidad() + " (" + f.getCliente().getDireccion().getProvincia() + ")", this.alineamientos[0]);
			
			if (f.getCliente().getTelefonos().size() > 0) {
				this.imprimirTexto(ManejadorProperties.verPDF("CLIENTE_TELEFONO_X"), ManejadorProperties.verPDF("CLIENTE_TELEFONO_Y"), f.getCliente().getTelefonos().elementAt(0).toString(), this.alineamientos[0]);
			}
			
			if (f.getCliente().getEmails().size() > 0) {
				this.imprimirTexto(ManejadorProperties.verPDF("CLIENTE_MAIL_X"), ManejadorProperties.verPDF("CLIENTE_MAIL_Y"), f.getCliente().getEmails().elementAt(0).getEmail(), this.alineamientos[0]);
			}
			
			// Información básica de la factura
			this.imprimirTexto(ManejadorProperties.verPDF("FACTURA_NUMERO_X"), ManejadorProperties.verPDF("FACTURA_NUMERO_Y"), f.getNumeroFactura(), this.alineamientos[0]);
			this.imprimirTexto(ManejadorProperties.verPDF("FACTURA_FECHA_X"), ManejadorProperties.verPDF("FACTURA_FECHA_Y"), f.getFecha(), this.alineamientos[0]);
			
			// Pagos
			float posHorizontal = ManejadorProperties.verPDF("PAGOS_HORIZONTAL");
			float posVertical = ManejadorProperties.verPDF("PAGOS_VERTICAL");
			double total = 0;
			
			for (Pago pago : f.getPagos()) {
				total += pago.getCantidad();
				
				if (pago.getConcepto().length() > ManejadorProperties.verPDF("PAGOS_LARGO")) {
					Vector<String> lineas = new Vector<String>();
					lineas = this.dividirTexto(pago.getConcepto(), ManejadorProperties.verPDF("PAGOS_LARGO"));
					for (int x = 0; x < lineas.size(); x++) {
						if (x + 1 == lineas.size()) {
							this.imprimirTexto(posHorizontal, posVertical, lineas.elementAt(x), this.alineamientos[0]);
							this.imprimirTexto(ManejadorProperties.verPDF("PAGOS_CANTIDAD_X"), posVertical, String.valueOf(pago.getCantidad()) + ManejadorProperties.verLiteral("CURRENCY_SYMBOL"), this.alineamientos[1]);
							posVertical -= 10;
						}
						else {
							this.imprimirTexto(posHorizontal, posVertical, lineas.elementAt(x), this.alineamientos[0]);
							posVertical -= 10;
						}
					}
				}
				else {
					this.imprimirTexto(posHorizontal, posVertical, pago.getConcepto(), this.alineamientos[0]);
					this.imprimirTexto(ManejadorProperties.verPDF("PAGOS_CANTIDAD_X"), posVertical, String.valueOf(pago.getCantidad()) + ManejadorProperties.verLiteral("CURRENCY_SYMBOL"), this.alineamientos[1]);
					posVertical -= 10;
				}
			}
			
			this.imprimirTexto(ManejadorProperties.verPDF("TOTAL_SIN_IVA_TEXTO_X"), ManejadorProperties.verPDF("TOTAL_SIN_IVA_TEXTO_Y"), ManejadorProperties.verLiteral("TOTAL_WITHOUT_VAT"), this.alineamientos[1]);
			this.imprimirTexto(ManejadorProperties.verPDF("TOTAL_SIN_IVA_X"), ManejadorProperties.verPDF("TOTAL_SIN_IVA_TEXTO_Y"), String.format("%.2f", total) + ManejadorProperties.verLiteral("CURRENCY_SYMBOL"), this.alineamientos[1]);
			
			// Separador
			this.imprimirTexto(ManejadorProperties.verPDF("SEPARADOR_X"), ManejadorProperties.verPDF("SEPARADOR_Y"), "______________________________________________________________________________         __________________________", this.alineamientos[0]);
			
			// IVA
			this.imprimirTexto(ManejadorProperties.verPDF("IVA_TEXTO_X"), ManejadorProperties.verPDF("IVA_TEXTO_Y"), ManejadorProperties.verLiteral("NEW_BILL_VAT") + ": " + String.valueOf(f.getIva()) + ManejadorProperties.verLiteral("PERCENT"), this.alineamientos[1]);
			this.imprimirTexto(ManejadorProperties.verPDF("IVA_X"), ManejadorProperties.verPDF("IVA_TEXTO_Y"), String.format("%.2f", ManejadorCalculos.calcularIVA(total, f.getIva())) + ManejadorProperties.verLiteral("CURRENCY_SYMBOL"), this.alineamientos[1]);
			
			// Total
			this.imprimirTexto(ManejadorProperties.verPDF("TOTAL_TEXTO_X"), ManejadorProperties.verPDF("TOTAL_TEXTO_Y"), ManejadorProperties.verLiteral("NEW_BILL_TOTAL"), this.alineamientos[1]);
			this.imprimirTexto(ManejadorProperties.verPDF("TOTAL_X"), ManejadorProperties.verPDF("TOTAL_TEXTO_Y"), String.format("%2.f", total + ManejadorCalculos.calcularIVA(total, f.getIva())) + ManejadorProperties.verLiteral("CURRENCY_SYMBOL"), this.alineamientos[1]);
			
			this.contenido.endText();
		}
		
		this.stamperPdf.close();
	}
	
	/**
	 * Agrega texto al PdfContentByte para mostrarlo en la nueva factura PDF
	 * @param horizontal
	 * @param vertical
	 * @param texto
	 * @param alineacion
	 */
	private void imprimirTexto(float horizontal, float vertical, String texto, String alineacion) {
		
		if (alineacion.equals(this.alineamientos[0])) {
			this.contenido.showTextAligned(Element.ALIGN_LEFT, texto, horizontal, vertical, 0);
		}
		else if (alineacion.equals(this.alineamientos[1])) {
			this.contenido.showTextAligned(Element.ALIGN_RIGHT, texto, horizontal, vertical, 0);
		}
	}
	
	/**
	 * Divide una cadena de texto en distintas líneas cuando se alcanza un máximo de caracteres 
	 * @param texto Cadena de texto
	 * @param caracteres Máximo de caracteres por línea
	 * @return
	 */
	public Vector<String> dividirTexto(String texto, int caracteres) {
		
		Vector<String> vector = new Vector<String>();
		int i = 0;
		
		while (i < texto.length()) {
			vector.add(texto.substring(i, Math.min(i + caracteres, texto.length())));
			i += caracteres;
		}
		
		return vector;
	}
}

















