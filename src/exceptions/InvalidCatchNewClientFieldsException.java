package exceptions;

/**
 * Exception thrown when we try to catch fields from NewClientFrame GUI
 * @author Inazio
 *
 */
@SuppressWarnings("serial")
public class InvalidCatchNewClientFieldsException extends Exception{

	public InvalidCatchNewClientFieldsException() {}
	
	public InvalidCatchNewClientFieldsException(String message) {
		super(message);
	}
}
