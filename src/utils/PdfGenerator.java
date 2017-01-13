package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import com.itextpdf.text.DocumentException;
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
			this.printText(260, 780, b.getClient().getName() + " " + b.getClient().getSurname());
			this.printText(260, 770, b.getClient().getAddress().getStreet());
			this.printText(260, 760, b.getClient().getAddress().getPostalCode() + ", " + b.getClient().getAddress().getLocality() + " (" + b.getClient().getAddress().getProvince() + ")");
			this.printText(260, 740, b.getClient().getPhones().elementAt(0).toString());
			this.printText(260, 730, b.getClient().getEmails().elementAt(0).getEmail());
			
			// Bill basic info
			this.printText(138, 633.4f, b.getBillNumber());
			this.printText(250, 633.4f, b.getDate());
			this.printText(420, 633.4f, String.valueOf(b.getClient().getClientCode()));
			
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
							this.printText(horizontalPos, verticalPos, v.elementAt(x));
							this.printText(447, verticalPos, String.valueOf(p.getAmount()) + UserMessages.CURRENCY_SYMBOL);
							verticalPos -= 10;
						}
						else {
							this.printText(horizontalPos, verticalPos, v.elementAt(x));
							verticalPos -= 10;
						}
					}
				}
				else {
					this.printText(horizontalPos, verticalPos, p.getPaymentConcept());
					this.printText(447, verticalPos, String.valueOf(p.getAmount()) + UserMessages.CURRENCY_SYMBOL);
					verticalPos -= 10;
				}
				
				verticalPos -= 10;
			}
			
			// Separator
			this.printText(60, 215, "______________________________________________________________________________         __________________________");
			
			// VAT
			this.printText(376, 195, UserMessages.NEW_BILL_VAT + ":");
			this.printText(447, 195, String.valueOf(b.getVat()) + UserMessages.PERCENT);
			
			// Total
			this.printText(370, 175, UserMessages.NEW_BILL_TOTAL);
			this.printText(447, 175, String.format("%.2f", this.calculatesVAT(total, b.getVat())) + UserMessages.CURRENCY_SYMBOL);
			
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
	private void printText(float horizontal, float vertical, String text) {
		content.setTextMatrix(horizontal, vertical);
		content.showText(text);
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
		double vatComplete = (amount / 100) * vat;
		return amount + vatComplete;
	}
}
