package exceptions;

/**
 * Exception thrown when an email is not admitted
 * @author Inazio
 *
 */
@SuppressWarnings("serial")
public class InvalidTelephoneException extends Exception{

	public InvalidTelephoneException() {}
	
	public InvalidTelephoneException(String message) {
		super(message);
	}
}
