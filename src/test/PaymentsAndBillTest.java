package test;

import org.junit.*;

import exceptions.InvalidBillException;
import exceptions.InvalidPaymentException;
import participants.Bill;
import participants.Payment;
import utils.UserMessages;

public class PaymentsAndBillTest {
	
	@Test
	public void createPaymentTest() {
		try {
			@SuppressWarnings("unused")
			Payment p = new Payment("Payment 1", 25.60);
			Payment p2 = new Payment();
			p2.setPaymentConcept("Payment");
			p2.setAmount(35.99);
		} catch (InvalidPaymentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void createPaymentWithNoAmountTest() {
		
		String errorTest = "";
		try {
			@SuppressWarnings("unused")
			Payment p = new Payment("Payment 1", null);
		} catch (InvalidPaymentException e) {
			errorTest = e.getMessage();
		}
		
		Assert.assertEquals(errorTest, UserMessages.NO_AMOUNT);
	}
	
	@Test
	public void createPaymentWithNegativeAmmountTest() {
		
		String errorTest = "";
		try {
			@SuppressWarnings("unused")
			Payment p = new Payment("Payment 1", -5.20);
		} catch (InvalidPaymentException e) {
			errorTest = e.getMessage();
		}
		
		Assert.assertEquals(errorTest, UserMessages.NEGATIVE_AMOUNT);
	}
	
	@Test
	public void createPaymentWithEmptyPaymentTest() {
		
		String errorTest = "";
		try {
			@SuppressWarnings("unused")
			Payment p;
			p = new Payment("", 5.20);
		} catch (InvalidPaymentException e) {
			errorTest = e.getMessage();
		}
		
		Assert.assertEquals(errorTest, UserMessages.EMPTY_PAYMENT_CONCEPT);
	}
	
	@Test
	public void getTotalAmountTest() {
		
		double amount = 0;
		try {
			Bill b = new Bill();
			b.setBillNumber("113346789");
			Payment p1 = new Payment("P1", 25.50);
			Payment p2 = new Payment("P2", 32.75);
			Payment p3 = new Payment("P3", 15.20);
			b.addPayment(p1);
			b.addPayment(p2);
			b.addPayment(p3);
			amount = b.getTotalAmount();
		} 
		catch (InvalidBillException e) {
			System.out.println(e.getMessage());
		}
		catch (InvalidPaymentException e2) {
			System.out.println(e2.getMessage());
		}
		
		Assert.assertEquals(73.45, amount, 0);
	}
}
