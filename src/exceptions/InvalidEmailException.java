package exceptions;

/**
 * Exception thrown when an email is not admitted
 * @author Inazio
 *
 */
@SuppressWarnings("serial")
public class InvalidEmailException extends Exception {

	public InvalidEmailException() {}
	
	public InvalidEmailException(String message) {
		super(message);
	}
}
