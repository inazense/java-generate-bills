package test;

import org.junit.*;

import exceptions.InvalidEmailException;
import participants.Email;
import utils.UserMessages;

public class EmailTest {

	private static String[] validEmails = new String[] {
			"test@example.com", "test-101@example.com", "test.101@yahoo.com", 
			"test101@example.com", "test_101@example.com", "test-101@example-test.com", 
			"test.100@example.com.au", "test@e.com", "test@1.com", 
			"test@example.com.com", "test+101@example.com", "101@example.com"
	};
	
	private static String[] invalidEmails = new String[] { 
			"example", "example@.com.com", "exampel101@test.a", 
			"exampel101@.com", ".example@test.com", "example**()@test.com", 
			"example@%*.com", "example..101@test.com", "example.@test.com", 
			"test@example_101.com", "example@test@test.com", "example@test.com.a5"
	};
	
	@Test
	public void validEmailsTest() {
	
		Email email = new Email();
		for (String i : validEmails) {
			try {
				email.setEmail(i);
			} catch (InvalidEmailException e) {
				e.getMessage();
			}
			Assert.assertEquals(i, email.getEmail());
		}
	}
	
	@Test
	public void invalidEmailsTest() {
		
		Email email = new Email();
		String result;
		for (String i : invalidEmails) {
			result = "";
			try {
				email.setEmail(i);
			} catch (InvalidEmailException e) {
				result = e.getMessage();
			}
			Assert.assertEquals(UserMessages.EMAIL_NOT_VALID, result);
		}
	}
}
