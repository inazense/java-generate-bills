package exceptions;

/**
 * Work exception
 * @author Inazio
 *
 */
@SuppressWarnings("serial")
public class InvalidPaymentException extends Exception {

	public InvalidPaymentException() {}
	
	public InvalidPaymentException(String message) {
		super(message);
	}
}
