package exceptions;

/**
 * Exception thrown when a bill is not admitted
 * Causes:
 * 	- No works
 * 	- No client
 * 	- No bill number
 * @author Inazio
 *
 */
@SuppressWarnings("serial")
public class InvalidBillException extends Exception{

	public InvalidBillException() {}
	
	public InvalidBillException(String message) {
		super(message);
	}
	
}