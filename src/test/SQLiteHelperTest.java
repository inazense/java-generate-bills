package test;

import java.sql.SQLException;
import java.util.Vector;

import org.junit.*;

import database.SQLiteHelper;
import exceptions.InvalidEmailException;
import exceptions.InvalidTelephoneException;
import participants.Email;
import participants.Telephone;

public class SQLiteHelperTest {

	SQLiteHelper s = new SQLiteHelper();
	
	@Test
	public void insertClientTest() {
		String error = null;
		try {
			s.insertClient("inazio", "claver", "calle sin numero", "08080", "huesca", "huesca");
		}
		catch(SQLException e) {
			error = e.getMessage();
		}
		
		Assert.assertEquals(null, error);
	}
	
	@Test
	public void insertEmailsTest() {
		String error = null;
		Vector<Email> emails = new Vector<Email>();
		try {
			// Just few emails to test JDBC
			emails.add(new Email("test@tester.com"));
			emails.add(new Email("prueba@tester.es"));
			emails.add(new Email("bimbo_pan@punset.cat"));
			emails.add(new Email("dios@labordeta.ara"));
			
			s.insertEmails(s.whatIsMaxClientNumber(), emails);
		} catch (SQLException | InvalidEmailException e) {
			error = e.getMessage();
		}
		
		Assert.assertEquals(null, error);
	}
	
	@Test
	public void insertPhonesTest() {
		String error = null;
		Vector<Telephone> phones = new Vector<Telephone>();
		try {
			// Just few emails to test JDBC
			phones.add(new Telephone("+34", "974222222"));
			phones.add(new Telephone("+34", "974333333"));
			phones.add(new Telephone("+34", "974444444"));
			phones.add(new Telephone("+34", "974555555"));
			
			s.insertTelephones(s.whatIsMaxClientNumber(), phones);
		} catch (SQLException | InvalidTelephoneException e) {
			error = e.getMessage();
		}
		
		Assert.assertEquals(null, error);
	}
	
	@Test
	public void whatIsMaxClientNumberTest() {
		
		try {
			int r = s.whatIsMaxClientNumber();
			
			Assert.assertNotNull(r);
			Assert.assertTrue(r > 0);
			
		}
		catch(SQLException e) {
			e.getMessage();
		}
	}
	
}
