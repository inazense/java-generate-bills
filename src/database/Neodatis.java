package database;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

import exceptions.InvalidNeodatisException;
import utils.UserMessages;

/**
 * Class used to manage the Neodatis Database
 * @author Inazio
 *
 */
public class Neodatis {

	// Properties
	public String path = "database/db";
	public ODB odb;
	
	// Constructor
	public Neodatis() {}
	
	// Methods
	
	/**
	 * Open Neodatis database connection
	 * @throws InvalidNeodatisException
	 */
	public void openDatabase() throws InvalidNeodatisException{
		if (odb != null) {
			throw new InvalidNeodatisException(UserMessages.NEODATIS_NOT_CLOSED);
		}
		else {
			odb = ODBFactory.open(path);
		}
	}
	
	/**
	 * Cloase Neodatis database connection
	 * @throws InvalidNeodatisException
	 */
	public void closeDatabase() throws InvalidNeodatisException {
		if (odb.isClosed()) {
			throw new InvalidNeodatisException(UserMessages.NEODATIS_IS_CLOSED);
		}
		else {
			odb.close();
		}
	}
	
	/**
	 * Add an object to database
	 * @param obj Object to add. Generic because I want to store few different types of objects
	 * @throws InvalidNeodatisException
	 */
	public void addObject(Object obj) throws InvalidNeodatisException {
		this.openDatabase();
		odb.store(obj);
		this.closeDatabase();
	}
 }
