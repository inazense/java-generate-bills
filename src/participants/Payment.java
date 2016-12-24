package participants;

import exceptions.InvalidPaymentException;
import utils.UserMessages;

public class Payment {

	// Properties
	private String paymentConcept;
	private double amount;
	
	// Constructors
	public Payment() {}
	
	public Payment(String paymentConcept, Double amount) throws InvalidPaymentException {
		if (amount == null) {
			throw new InvalidPaymentException(UserMessages.NO_AMOUNT);
		}
		else if (amount < 0.0) {
			throw new InvalidPaymentException(UserMessages.NEGATIVE_AMOUNT);
		}
		else if (paymentConcept.isEmpty() || paymentConcept == null) {
			throw new InvalidPaymentException(UserMessages.EMPTY_PAYMENT_CONCEPT);
		}
		else {
			this.paymentConcept = paymentConcept;
			this.amount = amount;
		}
		
	}

	// Getters and setters
	public String getPaymentConcept() {
		return paymentConcept;
	}

	public void setPaymentConcept(String paymentConcept) {
		this.paymentConcept = paymentConcept;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) throws InvalidPaymentException {
		if (amount == null) {
			throw new InvalidPaymentException(UserMessages.NO_AMOUNT);
		}
		else if (amount < 0.0) {
			throw new InvalidPaymentException(UserMessages.NEGATIVE_AMOUNT);
		}
		else if (paymentConcept.isEmpty() || paymentConcept == null) {
			throw new InvalidPaymentException(UserMessages.EMPTY_PAYMENT_CONCEPT);
		}
		else {
			this.amount = amount;
		}
	}
}
