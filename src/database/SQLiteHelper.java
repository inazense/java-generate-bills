package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import participants.Email;
import participants.Telephone;

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
	 * Insert a SQL sentence into database
	 * @param sql SQL sentence
	 * @return boolean. true if insert is correct, false if not
	 * @throws SQLException
	 */
	private void insert(String sql) throws SQLException {
		SingletonSQLite.getConnection().createStatement().executeUpdate(sql);
	}
	
	/**
	 * Get results from database
	 * @param sql String SQL sentence
	 * @return ResultSet with result of the SQL query
	 * @throws SQLException
	 */
	private ResultSet read(String sql) throws SQLException {
		return SingletonSQLite.getConnection().createStatement().executeQuery(sql);
	}
	
	/**
	 * Used to know bigger client id number
	 * @return Integer. Max. client id used
	 * @throws SQLException
	 */
	public int whatIsMaxClientNumber() throws SQLException {
		sql = "SELECT MAX(id) FROM clients";
		rs = this.read(sql);
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
		insert(sql);
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
			insert(sql);
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
			insert(sql);
		}
	}
	
	/**
	 * Returns name, surname, locality and client code to show in search clients.
	 * @return HashMap. Key -> cliendCode. String -> name + surname + locality
	 * @throws SQLException
	 */
	public Map<Integer, String> showAllClients() throws SQLException {
		Map<Integer, String> clients = new HashMap<Integer, String>();
		
		rs = read("SELECT * FROM clients");
		while (rs.next()) {
			String name = rs.getString("name") + " " + rs.getString("surname") + " - " + rs.getString("locality");
			int key = rs.getInt("id");
			clients.put(key, name);
		}
		
		return clients;
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
	public Map<Integer, String> showFilteredClients(String filter, String value) throws SQLException {
		Map<Integer, String> clients = new HashMap<Integer, String>();
		
		switch (filter) {
			case "number":
				sql = "SELECT DISTINCT id, name, surname, locality FROM clients, phones WHERE number = '" + value + "' and id = client";
				break;
			case "email":
				sql = "SELECT DISTINCT id, name, surname, locality FROM clients, emails WHERE email = '" + value + "' and id = client";
				break;
			default:
				sql = "SELECT * FROM clients WHERE " + filter + " = '" + value + "'";
		}
		
		rs = read(sql);
		while (rs.next()) {
			String name = rs.getString("name") + " " + rs.getString("surname") + " - " + rs.getString("locality");
			int key = rs.getInt("id");
			clients.put(key, name);
		}
		
		return clients;
	}
}
