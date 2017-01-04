package database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Used to work with SQLite database
 * @author Inazio
 *
 */
public class SQLiteHelper {

	// Properties
	
	// Constructor
	public SQLiteHelper() {}
	
	// Methods
	
	/**
	 * Insert a SQL sentence into database
	 * @param sql SQL sentence
	 * @return boolean. true if insert is correct, false if not
	 * @throws SQLException
	 */
	public boolean insert(String sql) throws SQLException {
		int result = SingletonSQLite.getConnection().createStatement().executeUpdate(sql);
		
		return result > 0;
	}
	
	/**
	 * Get results from database
	 * @param sql String SQL sentence
	 * @return ResultSet with result of the SQL query
	 * @throws SQLException
	 */
	public ResultSet read(String sql) throws SQLException {
		return SingletonSQLite.getConnection().createStatement().executeQuery(sql);
	}
	
}
