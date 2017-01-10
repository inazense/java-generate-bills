package participants;

import java.util.Vector;

import javax.swing.JOptionPane;

import exceptions.InvalidBillException;
import utils.UserMessages;

public class Bill {

	// Properties
	private String billNumber;
	private double vat; // IVA in Spain
	private String date;
	private Client client;
	private Vector<Payment> payments;
	
	// Constructors
	public Bill() {
		payments = new Vector<Payment>();
	}
	
	public Bill(String billNumber, double vat, Client client, Vector<Payment> payments, String date) throws InvalidBillException {
		if (billNumber.isEmpty() || billNumber == null) {
			throw new InvalidBillException(UserMessages.MANDATORY_BILL_NUMBER);
		}
		else if (payments.size() == 0) {
			throw new InvalidBillException(UserMessages.MORE_THAN_0_PAYMENTS);
		}
		else if (!validDate(date)) {
			throw new InvalidBillException(UserMessages.INVALID_DATE);
		}
		else {
			this.billNumber = billNumber;
			this.vat = vat;
			this.client = client;
			this.payments = payments;
			this.date = date;
		}
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
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) throws InvalidBillException {
		if (!validDate(date)) {
			throw new InvalidBillException(UserMessages.INVALID_DATE);
		}
		else {
			this.date = date;
		}
	}

	public void setPayments(Vector<Payment> payments) throws InvalidBillException {
		if (payments.size() == 0) {
			throw new InvalidBillException(UserMessages.MORE_THAN_0_PAYMENTS);
		}
		else {
			this.payments = payments;
		}
	}
	
	/**
	 * Check if a string date is valid
	 * @param date String date with format dd/mm/yyyy
	 * @return boolean. true if is valid, false if not
	 */
	private boolean validDate(String date) {
		
		boolean r = true;
		try {
			int day = Integer.parseInt(date.substring(0, 2));
			int month = Integer.parseInt(date.substring(3, 5));
			int year = Integer.parseInt(date.substring(6, 10));

			if (month < 1 || month > 12) {
				r = false;
			}
			else {
				if (month == 2) {
					if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) {
						if (day < 1 || day > 29) {
							r = false;
						}
					}
					else {
						if (day < 1 || day > 28) {
							r = false;
						}
					}
				}
				else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
					if (day < 1 || day > 31) {
						r = false;
					}
				}
				else {
					if (day < 1 || day > 30) {
						r = false;
					}
				}
			}
		}
		catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, UserMessages.NUM_DATE_EXCEPTION);
			r = false;
		}
		
		return r;
	}
}
