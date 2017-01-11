package database;

import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import exceptions.InvalidBillException;
import exceptions.InvalidEmailException;
import exceptions.InvalidPaymentException;
import exceptions.InvalidTelephoneException;
import participants.Address;
import participants.Bill;
import participants.Client;
import participants.Email;
import participants.Payment;
import participants.Telephone;
import utils.UserMessages;

/**
 * Used to work with SQLite database
 * @author Inazio
 *
 */
public class SQLiteHelper {

	// Properties
	ResultSet rs;
	String sql;
	
	// Constructor
	public SQLiteHelper() {}
	
	// Methods
	
	/**
	 * Insert, delete or update an SQL sentence.
	 * @param sql SQL sentence
	 * @return boolean. true if insert is correct, false if not
	 * @throws SQLException
	 */
	private void dbAction(String sql) throws SQLException {
		SingletonSQLite.getConnection().createStatement().executeUpdate(sql);
	}
	
	/**
	 * Get results from database
	 * @param sql String SQL sentence
	 * @return ResultSet with result of the SQL query
	 * @throws SQLException
	 */
	private ResultSet dbQuery(String sql) throws SQLException {
		return SingletonSQLite.getConnection().createStatement().executeQuery(sql);
	}
	
	/**
	 * Used to know bigger client id number
	 * @return Integer. Max. client id used
	 * @throws SQLException
	 */
	public int whatIsMaxClientNumber() throws SQLException {
		sql = "SELECT MAX(id) FROM clients";
		rs = this.dbQuery(sql);
		if (!rs.next()) {
			return 0;
		}
		else {
			return rs.getInt(1);
		}
	}
	
	/**
	 * Insert a client into database
	 * @param name Not null
	 * @param surname Not null
	 * @param street
	 * @param postalCode
	 * @param locality
	 * @param province
	 * @throws SQLException
	 */
	public void insertClient(String name, String surname, String street, String postalCode, String locality, String province) throws SQLException {
		sql = "INSERT INTO clients(name, surname, street, postalCode, locality, province) VALUES ('" + name + "', '" + surname + "', '" + street + "', '" + postalCode + "', '" + locality + "', '" + province + "')";
		dbAction(sql);
	}
	
	/**
	 * Insert mails into database
	 * @param clientCode Client clientCode. Not null
	 * @param emails Vector<Email> not null, and almost have one Email to add
	 * @throws SQLException
	 */
	public void insertEmails(int clientCode, Vector<Email> emails) throws SQLException {
		for (Email i : emails) {
			sql = "INSERT INTO emails(client, email) VALUES (" + clientCode + ", '" + i.getEmail() + "')";
			dbAction(sql);
		}
	}
	
	/**
	 * Insert phones into database
	 * @param clientCode Client clientCode. Not null
	 * @param phones Vector<Telephone> not null, and almost have one Telephone to add
	 * @throws SQLException
	 */
	public void insertTelephones(int clientCode, Vector<Telephone> phones) throws SQLException {
		for (Telephone i : phones) {
			sql = "INSERT INTO phones(client, prefix, number) VALUES (" + clientCode + ", '" + i.getPrefix() + "', '" + i.getNumber() + "')";
			dbAction(sql);
		}
	}
	
	/**
	 * Insert bills into database
	 * @param bill Object to insert (no Payments)
	 * @throws Exception 
	 */
	public void insertBill(Bill bill) throws InvalidBillException {
		sql = "INSERT INTO bills(id, client, vat, date) VALUES ('" + bill.getBillNumber() + "', " + bill.getClient().getClientCode() + ", " + bill.getVat() + ", '" + bill.getDate() + "')";
		try {
			dbAction(sql);
		} catch (SQLException e) {
			throw new InvalidBillException(UserMessages.FAIL_SAVE_BILLS);
		}
	}
	
	/**
	 * Insert Payments into database.
	 * @param idBill Bill identifier. It is mandatory that there be in database - table bills
	 * @param payments
	 * @throws Exception 
	 * @throws SQLException
	 */
	public void insertPayments(String idBill, Vector<Payment> payments) throws InvalidPaymentException {
		for (Payment i : payments) {
			sql = "INSERT INTO payments(bill, concept, amount) VALUES('" + idBill + "', '" + i.getPaymentConcept() + "', " + i.getAmount() + ")";
			try {
				dbAction(sql);
			} catch (SQLException e) {
				throw new InvalidParameterException(UserMessages.FAIL_SAVE_PAYMENTS);
			}
		}
	}
	
	/**
	 * Returns name, surname, locality and client code to show in search clients.
	 * @return HashMap. Key -> cliendCode. String -> name + surname + locality
	 * @throws SQLException
	 */
	public HashMap<String, Integer> showAllClients() throws SQLException {
		HashMap<String, Integer> clients = new HashMap<String, Integer>();
		
		rs = dbQuery("SELECT * FROM clients");
		while (rs.next()) {
			String key = rs.getString("name") + " " + rs.getString("surname") + " - " + rs.getString("locality");
			int name = rs.getInt("id");
			clients.put(key, name);
		}
		
		return clients;
	}
	
	/**
	 * Returns id and date from table bills and name, surname and locality from table clients
	 * @param filter name of the field to filter the query
	 * @param value value of the field to filter the query
	 * @return Vector of Bill
	 * @throws SQLException 
	 */
	public Vector<String[]> showBills(String filter, String value) throws SQLException {
		Vector<String[]> bills = new Vector<String[]>();
		
		switch(filter) {
			case "id":
			case "date":
				sql = "SELECT bills.id, name, surname, locality, date FROM bills, clients WHERE " + filter + " like '%" + value + "%' AND clients.id = bills.client";
				break;
			case "client":
				sql = "SELECT bills.id, name, surname, locality, date FROM bills, clients WHERE " + filter + " = " + value + " AND clients.id = bills.client";
			default:
				sql = "SELECT bills.id, name, surname, locality, date FROM bills, clients WHERE clients.id = bills.client";
		}
		
		rs = dbQuery(sql);
		while (rs.next()) {
			String id = rs.getString("id");
			String client = rs.getString("name") + " " + rs.getString("surname") + " " + rs.getString("locality");
			String date = rs.getString("date");
			String[] s = {id, client, date};
			bills.add(s);
		}
		
		return bills;
	}
	
	/**
	 * Returns name, surname, locality and client code to show in search clients.
	 * @param name
	 * @param surname
	 * @param locality
	 * @param province
	 * @param phone
	 * @param email
	 * @return HashMap. Key -> cliendCode. String -> name + surname + locality
	 * @throws SQLException 
	 */
	public HashMap<String, Integer> showFilteredClients(String filter, String value) throws SQLException {
		HashMap<String, Integer> clients = new HashMap<String, Integer>();
		
		switch (filter) {
			case "number":
				sql = "SELECT DISTINCT id, name, surname, locality FROM clients, phones WHERE number like '%" + value + "%' and id = client";
				break;
			case "email":
				sql = "SELECT DISTINCT id, name, surname, locality FROM clients, emails WHERE email like '%" + value + "%' and id = client";
				break;
			default:
				sql = "SELECT * FROM clients WHERE " + filter + " like '%" + value + "%'";
		}
		
		rs = dbQuery(sql);
		while (rs.next()) {
			String key= rs.getString("name") + " " + rs.getString("surname") + " - " + rs.getString("locality");
			int name = rs.getInt("id");
			clients.put(key, name);
		}
		
		return clients;
	}
	
	/**
	 * Get complete client from client id
	 * @param id Integer. clientCode of Client
	 * @return Client with basic data, address, telephones and emails
	 * @throws SQLException 
	 * @throws InvalidTelephoneException 
	 * @throws InvalidEmailException 
	 */
	public Client getClientFromId(int id) throws SQLException, InvalidTelephoneException, InvalidEmailException {
		Client c = new Client();
		
		// Client and address
		sql = "SELECT * FROM clients WHERE id = " + id;
		rs = this.dbQuery(sql);
		while (rs.next()) {
			c.setClientCode(id);
			c.setName(rs.getString("name"));
			c.setSurname(rs.getString("surname"));
			c.setAddress(new Address(rs.getString("street"), rs.getString("postalCode"), rs.getString("locality"), rs.getString("province")));
		}
		
		rs = null;
		
		// Phones
		sql = "SELECT * FROM phones WHERE client = " + id;
		rs = this.dbQuery(sql);
		Vector<Telephone> phones = new Vector<Telephone>();
		while (rs.next()) {
			phones.add(new Telephone(rs.getString("prefix"), rs.getString("number")));
		}
		c.setPhones(phones);
		
		rs = null;
		
		// TODO Add mails
		sql = "SELECT * FROM emails WHERE client = " + id;
		Vector<Email> emails = new Vector<Email>();
		rs = this.dbQuery(sql);
		while (rs.next()) {
			emails.add(new Email(rs.getString("email")));
		}
		c.setEmails(emails);
		
		rs = null;
		
		return c;
	}
	
	/**
	 * Delete all rows with a clientCode into database phones or emails
	 * @param clientCode Client clientCode field
	 * @param table Only "phones" or "emails"
	 * @throws SQLException
	 */
	public void deleteRowsByClient(int clientCode, String table) throws SQLException {
		if (table.equals("phones") || table.equals("emails")) {
			sql = "DELETE FROM " + table + " WHERE client = " + clientCode;
			this.dbAction(sql);
		}
	}
	
	/**
	 * Update row in table clients with field id = clientCode 
	 * @param clientCode
	 * @param name
	 * @param surname
	 * @param street
	 * @param postalCode
	 * @param locality
	 * @param province
	 * @throws SQLException
	 */
	public void updateClient(int clientCode, String name, String surname, String street, String postalCode, String locality, String province) throws SQLException {
		sql = "UPDATE clients SET name = '" + name 
				+ "', surname = '" + surname 
				+ "', street = '" + street 
				+ "', postalCode = '" + postalCode 
				+ "', locality = '" + locality 
				+ "', province = '" + province 
				+ "' WHERE id = " + clientCode;
		this.dbAction(sql);
	}
}
