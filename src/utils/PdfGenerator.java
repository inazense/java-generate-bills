package utils;

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

import participants.Bill;
import participants.Payment;

public class PdfGenerator {

	// Properties
	private final String pathBillGeneric;
	private String pdfOutputName;
	private String pdfOutputFolder;
	
	private PdfReader pdfReader;
	private PdfStamper pdfStamper;
	private BaseFont baseFont;
	private PdfContentByte content;
	
	private Font font;
	
	String[] alignments = {"left", "right"};
	
	// Constructor
	public PdfGenerator(String pdfName) {
		this.pdfOutputFolder = "facturas/";
		this.pdfOutputName = this.pdfOutputFolder + pdfName;
		this.pathBillGeneric = "resources/factura.pdf";
		this.font = FontFactory.getFont("resources/fonts/Roboto-Light.ttf");
	}
	
	// Methods
	public void createBill(Bill b) throws IOException, DocumentException {
		pdfReader = new PdfReader(this.pathBillGeneric);
		pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(this.pdfOutputName));
		baseFont = font.getBaseFont();
		
		for(int i=1; i<= pdfReader.getNumberOfPages(); i++){
			
			content = pdfStamper.getOverContent(i);
			content.beginText();
			content.setFontAndSize(baseFont, 10);

			// Client basic info
			this.printText(260, 780, b.getClient().getName() + " " + b.getClient().getSurname() + "                          " + b.getClient().getDni(), alignments[0]);
			this.printText(260, 770, b.getClient().getAddress().getStreet(), alignments[0]);
			this.printText(260, 760, b.getClient().getAddress().getPostalCode() + ", " + b.getClient().getAddress().getLocality() + " (" + b.getClient().getAddress().getProvince() + ")", alignments[0]);
			if (b.getClient().getPhones().size() > 0) {
				this.printText(260, 740, b.getClient().getPhones().elementAt(0).toString(), alignments[0]);
			}
			if (b.getClient().getEmails().size() > 0) {
				this.printText(260, 730, b.getClient().getEmails().elementAt(0).getEmail(), alignments[0]);
			}
			
			// Bill basic info
			this.printText(176, 633.4f, b.getBillNumber(), alignments[1]);
			this.printText(250, 633.4f, b.getDate(), alignments[0]);
			this.printText(490, 633.4f, String.valueOf(b.getClient().getClientCode()), alignments[0]);
			
			// Payments
			float horizontalPos = 70;
			float verticalPos = 570;
			double total = 0;
			
			for (Payment p : b.getPayments()) {
				total += p.getAmount();
				if (p.getPaymentConcept().length() > 66) {
					Vector<String> v = new Vector<String>();
					v = this.splitArray(p.getPaymentConcept(), 66);
					for (int x = 0; x < v.size(); x++) {
						if (x + 1 == v.size()) {
							this.printText(horizontalPos, verticalPos, v.elementAt(x), alignments[0]);
							this.printText(520, verticalPos, String.valueOf(p.getAmount()) + UserMessages.CURRENCY_SYMBOL, alignments[1]);
							verticalPos -= 10;
						}
						else {
							this.printText(horizontalPos, verticalPos, v.elementAt(x), alignments[0]);
							verticalPos -= 10;
						}
					}
				}
				else {
					this.printText(horizontalPos, verticalPos, p.getPaymentConcept(), alignments[0]);
					this.printText(520, verticalPos, String.valueOf(p.getAmount()) + UserMessages.CURRENCY_SYMBOL, alignments[1]);
					verticalPos -= 10;
				}
				
				verticalPos -= 10;
			}
			
			// Separator
			this.printText(60, 215, "______________________________________________________________________________         __________________________", alignments[0]);
			
			// VAT
			this.printText(376, 195, UserMessages.NEW_BILL_VAT + ": " + String.valueOf(b.getVat()) + UserMessages.PERCENT, alignments[1]);
			this.printText(520, 195, String.format("%.2f", this.calculatesVAT(total, b.getVat())) + UserMessages.CURRENCY_SYMBOL, alignments[1]);
			
			// Total
			this.printText(376, 175, UserMessages.NEW_BILL_TOTAL, alignments[1]);
			this.printText(520, 175, String.format("%.2f", total + this.calculatesVAT(total, b.getVat())) + UserMessages.CURRENCY_SYMBOL, alignments[1]);
			
			content.endText();
		}
		
		pdfStamper.close();
	}
	
	/**
	 * Add text to PdfContentByte to show in new PDF bill
	 * @param horizontal Horizontal position in PDF
	 * @param vertical Vertical position in PDF
	 * @param text Text to print
	 */
	private void printText(float horizontal, float vertical, String text, String alignment) {
		if (alignment.equals(alignments[0])) {
			content.showTextAligned(Element.ALIGN_LEFT, text, horizontal, vertical, 0);
		}
		else if (alignment.equals(alignments[1])) {
			content.showTextAligned(Element.ALIGN_RIGHT, text, horizontal, vertical, 0);
		}
		
	}
	
	/**
	 * 
	 * Split a String into a vector when max characters is over
	 * @param text String to split
	 * @param characters Limit of characters for String
	 * @return Vector of Strings
	 */
	public Vector<String> splitArray(String text, int characters) {
		Vector<String> vector = new Vector<String>();
		int i = 0;
		
		while (i < text.length()) {
			vector.add(text.substring(i, Math.min(i + characters, text.length())));
			i += characters;
		}
		return vector;
	}
	
	/**
	 * Calculates VAT of Payments
	 * @param amount Total without VAT
	 * @param vat In percent %
	 * @return Total price of payments including VAT
	 */
	private double calculatesVAT(double amount, double vat) {
		return (amount / 100) * vat;
	}
}
