package participants;

import org.apache.commons.lang3.math.NumberUtils;

import exceptions.InvalidTelephoneException;
import utils.UserMessages;

/**
 * Class Telephone using international standard UIT-T / E-164
 * @author Inazio
 *
 */
public class Telephone {
	
	// Properties
	private String prefix = "";
	private String number = "";

	// Constructors
	public Telephone() {}
	
	public Telephone(String prefix, String number) throws InvalidTelephoneException{
		if (prefix.length() + number.length() > 15) {
			throw new InvalidTelephoneException(UserMessages.PHONE_MORE_THAN_15_CHARACTERS);
		}
		else if (!NumberUtils.isDigits(number)) {
			throw new InvalidTelephoneException(UserMessages.PHONE_NUMBER_NOT_NUMERIC);
		}
		else if (!this.isValidPrefix(prefix)) {
			throw new InvalidTelephoneException(UserMessages.PREFIX_FORMAT_NOT_VALID);
		}
		else {
			this.prefix = prefix;
			this.number = number;
		}
		
	}

	// Methods
	/**
	 * Return phone string ready to be printed.
	 * @return String phone with format +00 000 00 00 00
	 */
	public String toString() {
		String result = prefix + " ";
		String aux = "";
		boolean firstIteration = true;
		
		for (char i : this.number.toCharArray()) {
			
			if (aux.length() == 0 && !firstIteration) {
				result += " ";
			}
			
			aux += i;
			
			if (aux.length() == 3 && firstIteration) {
				result += aux;
				aux = "";
				firstIteration = false;
			}
			
			if (aux.length() == 2 && !firstIteration) {
				result += aux;
				aux = "";
			}
		}
		result += aux; // In case there is a number without concatenating
		
		return result;
	}
	
	/**
	 * Check if prefix format start with + and the next characters are only digits
	 * @param prefix String. International telephone prefix
	 * @return boolean. true if is in format +00, false if not
	 */
	private boolean isValidPrefix(String prefix) {

		if (prefix.substring(0, 1).equals("+") && NumberUtils.isDigits(prefix.substring(1))) {
			return true;
		}
		
		return false;
	}
	
	// Getters and setters
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) throws InvalidTelephoneException{
		if (this.number.length() + prefix.length() > 15) {
			throw new InvalidTelephoneException(UserMessages.PHONE_MORE_THAN_15_CHARACTERS);
		}
		else if (!this.isValidPrefix(prefix)) {
			throw new InvalidTelephoneException(UserMessages.PREFIX_FORMAT_NOT_VALID);
		}
		else{
			this.prefix = prefix;
		}
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) throws InvalidTelephoneException{
		if (this.prefix.length() + this.number.length() > 15) {
			throw new InvalidTelephoneException(UserMessages.PHONE_MORE_THAN_15_CHARACTERS);
		}
		else if (!NumberUtils.isDigits(number)) {
			throw new InvalidTelephoneException(UserMessages.PHONE_NUMBER_NOT_NUMERIC);
		}
		else{
			this.number = number;
		}
		
	}	
}
