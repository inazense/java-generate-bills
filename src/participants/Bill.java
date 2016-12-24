package participants;

import java.util.Vector;

import exceptions.InvalidBillException;
import utils.UserMessages;

public class Bill {

	// Properties
	private String billNumber;
	private double vat; // IVA in Spain
	private Client client;
	private Vector<Payment> payments;
	
	// Constructors
	public Bill() {
		payments = new Vector<Payment>();
	}
	
	public Bill(String billNumber, double vat, Client client, Vector<Payment> payments) throws InvalidBillException {
		if (billNumber.isEmpty() || billNumber == null) {
			throw new InvalidBillException(UserMessages.MANDATORY_BILL_NUMBER);
		}
		if (payments.size() == 0) {
			throw new InvalidBillException(UserMessages.MORE_THAN_0_PAYMENTS);
		}
		this.billNumber = billNumber;
		this.vat = vat;
		this.client = client;
		this.payments = payments;
	}

	// Methods
	/**
	 * Calculates total amount from works in the bill
	 * @return double
	 */
	public double getTotalAmount() throws InvalidBillException{
		
		if (this.payments.size() == 0 || this.payments == null) {
			throw new InvalidBillException(UserMessages.MORE_THAN_0_PAYMENTS);
		}
		else {
			double amount = 0.0;
			
			for (Payment i : payments) {
				amount += i.getAmount();
			}
			amount += (amount * (vat / 100)); // VAT
			return amount;
		}
	}
	
	/**
	 * Add a payment to vector
	 * @param payment Payment
	 * @return boolean
	 */
	public boolean addPayment(Payment payment) {
		return this.payments.add(payment);
	}
	
	/**
	 * Remove a payment from vector
	 * @param payment Payment
	 * @return 
	 */
	public boolean removePayment(Payment payment) {
		return this.payments.remove(payment);
	}
	
	// Getters and setters
	public String getBillNumber(){
		return billNumber;
	}

	public void setBillNumber(String billNumber) throws InvalidBillException {
		if (billNumber.isEmpty() || billNumber == null) {
			throw new InvalidBillException(UserMessages.MANDATORY_BILL_NUMBER);
		}
		else {
			this.billNumber = billNumber;
		}
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Vector<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Vector<Payment> payments) throws InvalidBillException {
		if (payments.size() == 0) {
			throw new InvalidBillException(UserMessages.MORE_THAN_0_PAYMENTS);
		}
		else {
			this.payments = payments;
		}
	}
}
