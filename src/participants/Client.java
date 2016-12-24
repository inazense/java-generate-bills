package participants;

import java.util.Vector;

/**
 * Customer to bill
 * @author Inazio
 *
 */
public class Client {

	// Properties
	private String name;
	private String surname;
	private int codClient;
	private Address address;
	private Vector<Telephone> phones;
	private Vector<Email> emails;
	
	// Constructors
	public Client(){
		phones = new Vector<Telephone>();
		emails = new Vector<Email>();
	}
	
	public Client(String name, String surname, int codClient, Address address, Vector<Telephone> phones, Vector<Email> emails) {
		this.name = name;
		this.surname = surname;
		this.codClient = codClient;
		this.address = address;
		this.phones = phones;
		this.emails = emails;
	}
	
	// Methods
	
	/**
	 * Add a phone to vector
	 * @param phone Telephone
	 * @return boolean. true if added properly, false if not
	 */
	public boolean addPhone(Telephone phone) {
		return this.phones.add(phone);
	}
	
	/**
	 * Remove a phone from vector
	 * @param phone Telephone
	 * @return boolean. true if removed properly, false if not
	 */
	public boolean removePhone(Telephone phone) {
		return this.phones.remove(phone);
	}
	
	/**
	 * Check if phone exists in vector
	 * @param phone Telephone
	 * @return boolean. true if exits, false if not
	 */
	public boolean searchPhone(Telephone phone) {
		return this.phones.contains(phone);
	}
	
	/**
	 * Add an email to vector
	 * @param email Email
	 * @return boolean. true if added properly, false if not
	 */
	public boolean addEmail(Email email) {
		return this.addEmail(email);
	}
	
	/**
	 * Remove a email from vector
	 * @param email Email
	 * @return boolean. true if removed properly, false if not
	 */
	public boolean removeEmail(Email email) {
		return this.emails.add(email);
	}
	
	/**
	 * Check if email exists in vector
	 * @param email Email
	 * @return boolean. true if exits, false if not
	 */
	public boolean searchEmail(Email email) {
		return this.emails.contains(email);
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getCodClient() {
		return codClient;
	}

	public void setCodClient(int codClient) {
		this.codClient = codClient;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Vector<Telephone> getPhones() {
		return phones;
	}

	public void setPhones(Vector<Telephone> phones) {
		this.phones = phones;
	}

	public Vector<Email> getEmails() {
		return emails;
	}

	public void setEmails(Vector<Email> emails) {
		this.emails = emails;
	}
}
