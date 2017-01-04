package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton pattern to get SQLite Connection
 * @author Inazio
 *
 */
public class SingletonSQLite {

	// Properties
	private static Connection connection 	= null;
	private String driver 					= "org.sqlite.JDBC";;
	private String path 					= "jdbc:sqlite:database/register.db";
	
	// Constructor
	private SingletonSQLite() {
		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(path);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO: handle exception
		}
	}
	
	// Methods
	
	/**
	 * Returns SQLite connection (and create one if doesn't exists)
	 * @return SQL Connection to SQLite database
	 */
	public static Connection getConnection() {
		if (connection == null) {
			new SingletonSQLite();
		}
		
		return connection;
	}
}
