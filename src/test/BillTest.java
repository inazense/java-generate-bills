package test;

import org.junit.*;

import exceptions.InvalidBillException;
import participants.Bill;

public class BillTest {

	String errorTest;
	
	@Test
	public void invalidDateTest() {
		Bill b = new Bill();
		String date = "29/02/2017";
		
		try {
			b.setDate(date);
			errorTest = "all ok";
		} catch (InvalidBillException e) {
			errorTest = e.getMessage();
		}
		
		Assert.assertEquals("Fecha invalida", errorTest);
	}
}
