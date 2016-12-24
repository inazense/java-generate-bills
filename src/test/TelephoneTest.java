package test;

import org.junit.*;

import exceptions.InvalidTelephoneException;
import participants.Telephone;
import utils.UserMessages;

public class TelephoneTest {

	@Test
	public void createNewTelephoneTest() {
		Telephone t = new Telephone();
		try {
			t.setNumber("000000000");
			t.setPrefix("+34");
		} catch (InvalidTelephoneException e) {
			e.getMessage();
		}
		
		Assert.assertTrue(t.getNumber() == "000000000" && t.getPrefix() == "+34");
		
	}
	
	@Test
	public void createNewTelephoneWrongNumberTest() {
		
		try {
			@SuppressWarnings("unused")
			Telephone t = new Telephone("aaaaaaaa", "+34");
		} catch (InvalidTelephoneException e) {
			Assert.assertEquals(UserMessages.PHONE_NUMBER_NOT_NUMERIC, e.getMessage());
		}
	}
	
	@Test
	public void createNewTelephoneWrongPrefixTest() {
		
		Telephone t = new Telephone();
		try {
			t.setPrefix("-34aa");
		} catch (InvalidTelephoneException e) {
			Assert.assertEquals(UserMessages.PREFIX_FORMAT_NOT_VALID, e.getMessage());
		}
	}
	
	@Test
	public void createNewTelephoneBiggerThan15Test() {
		try {
			@SuppressWarnings("unused")
			Telephone t = new Telephone("+34", "000000000");
		} catch (InvalidTelephoneException e) {
			Assert.assertEquals(UserMessages.PHONE_MORE_THAN_15_CHARACTERS, e.getMessage());
		}
	}
	
	@Test
	public void seeTelephoneStringTest() {
		try {
			Telephone t = new Telephone("+34", "666444555");
			Assert.assertEquals("+34 666 44 45 55", t.toString());
		} catch (InvalidTelephoneException e) {
			e.getMessage();
		}
	}
}
