package participants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.InvalidEmailException;
import utils.UserMessages;

/**
 * Class Main
 * @author Inazio
 *
 */
public class Email {
	
	// Properties
	private String email;
	
	// Constructors
	public Email() {};
	
	public Email(String email) throws InvalidEmailException {
		
		if (!this.validateEmail(email)) {
			throw new InvalidEmailException(UserMessages.EMAIL_NOT_VALID);
		}
		else {
			this.email = email;
		}
	}
	
	// Methods
	/**
	 * Validates an email using regular expressions
	 * @param email Email as String
	 * @return true if email is valid, false if not
	 */
	private boolean validateEmail(String email) {
		
		// Regular expression to validate mail
		String patternEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		// Compiles regular expression into a pattern
		Pattern pattern = Pattern.compile(patternEmail);
		
		// Match email parameter against the pattern
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	// Getters and setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws InvalidEmailException {
		
		if (!this.validateEmail(email)) {
			throw new InvalidEmailException(UserMessages.EMAIL_NOT_VALID);
		}
		else {
			this.email = email;
		}
	}
}
