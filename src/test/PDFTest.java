package test;

import java.io.IOException;
import java.util.Vector;

import javax.print.PrintException;

import org.junit.*;

import com.itextpdf.text.DocumentException;

import exceptions.InvalidBillException;
import exceptions.InvalidEmailException;
import exceptions.InvalidPaymentException;
import exceptions.InvalidTelephoneException;
import participants.Address;
import participants.Bill;
import participants.Client;
import participants.Email;
import participants.Payment;
import participants.Telephone;
import utils.PdfGenerator;

public class PDFTest {

	@Test
	public void printPDFTest() {
		Bill b = null;
		Vector<Telephone> phones = new Vector<Telephone>();
		try {
			phones.add(new Telephone("+34", "999999999"));
			phones.add(new Telephone("+34", "888888888"));
		} catch (InvalidTelephoneException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Vector<Email> mails = new Vector<Email>();
		try {
			mails.add(new Email("tester@test.com"));
		} catch (InvalidEmailException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Client c = new Client("Inazio", "Claver Paules", 1225, new Address("Calle Falsa 123, 3º Izquierda", "22005", "Huesca", "Huesca"), phones, mails);
		
		Vector<Payment> payments = new Vector<Payment>();
		try {
			payments.add(new Payment("Alicatado de baño y duchaAlicatado de baño y duchaAlicatado de baño y duchaAlicatado de baño y duchaAlicatado de baño y ducha", 1687.45));
			payments.add(new Payment("Alicatado de baño y ducha", 998.99));
			payments.add(new Payment("Alicatado de baño y ducha", 15.99));
		} catch (InvalidPaymentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			b = new Bill("122235", 12, c, payments, "03/01/2017");
		} catch (InvalidBillException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PdfGenerator p = new PdfGenerator("1231566001.pdf");
		try {
			p.createBill(b);
		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
