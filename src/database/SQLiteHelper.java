package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import participants.Client;
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
	private boolean insert(String sql) throws SQLException {
		int result = SingletonSQLite.getConnection().createStatement().executeUpdate(sql);
		
		return result > 0;
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
	 * Insert Client into table clients
	 * @param client Client
	 * @return boolean. true if inserts are properly, false if not
	 * @throws SQLException 
	 */
	public boolean insertClient(Client client) throws SQLException {
		sql = "INSERT INTO clients VALUES("
				+ "null, " 
				+ client.getName() + ", " 
				+ client.getSurname() + ", " 
				+ client.getAddress().getStreet() + ", " 
				+ client.getAddress().getPostalCode() + ", " 
				+ client.getAddress().getLocality() + ", " 
				+ client.getAddress().getProvince() + ")";
		return this.insert(sql);
	}
	
	/**
	 * Insert emails into table emails
	 * @param emails Vector of Email
	 * @param clientCode Integer. Client clientCode property
	 * @return boolean. true if inserts are properly, false if not
	 * @throws SQLException
	 */
	public boolean insertEmails(Vector<Email> emails, int clientCode) throws SQLException {
		
		boolean result = true; 
		for (Email i : emails) {
			sql = "INSERT INTO emails VALUES(" + clientCode + ", " + i.getEmail() + ")";
			if (!insert(sql)) {
				result = false;
			}
		}
		
		return result;
	}
	
	/**
	 * Insert telephones into table phones
	 * @param phones Vector of Telephone
	 * @param clientCode Integer. Client clientCode property
	 * @return boolean. true if inserts are properly, false if not
	 * @throws SQLException
	 */
	public boolean insertPhones(Vector<Telephone> phones, int clientCode) throws SQLException {
		
		boolean result = true; 
		for (Telephone i : phones) {
			sql = "INSERT INTO phones VALUES(" + clientCode + ", " + i.toString() + ")";
			if (!insert(sql)) {
				result = false;
			}
		}
		
		return result;
	}
	
	/**
	 * Used to know bigger client id number
	 * @return Integer. Max. client id used
	 * @throws SQLException
	 */
	public int whatIsMaxClientNumber() throws SQLException {
		sql = "SELECT max(id) FROM clients";
		rs = this.read(sql);
		if (!rs.next()) {
			return 0;
		}
		else {
			return rs.getInt(0);
		}
	}
}
