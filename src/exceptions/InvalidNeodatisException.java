package exceptions;

@SuppressWarnings("serial")
public class InvalidNeodatisException extends Exception {

	public InvalidNeodatisException(){}
	
	public InvalidNeodatisException(String message) {
		super(message);
	}
}
