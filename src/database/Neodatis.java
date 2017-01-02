package database;

import java.util.Vector;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import exceptions.InvalidNeodatisException;
import participants.Bill;
import participants.Client;
import utils.UserMessages;

/**
 * Class used to manage the Neodatis Database
 * @author Inazio
 *
 */
public class Neodatis {

	// Properties
	private String path = "database/db.neodatis";
	private ODB odb;
	
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
	
	/**
	 * Return a vector with all clients stored in neodatis database
	 * @return
	 * @throws InvalidNeodatisException
	 */
	public Vector<Client> listClients() throws InvalidNeodatisException {
		Vector<Client> vector = new Vector<Client>();
		
		this.openDatabase();
		
		Objects<Client> objs = odb.getObjects(Client.class);
		while (objs.hasNext()) {
			vector.add(objs.next());
		}
		
		this.closeDatabase();
		
		return vector;
	}
	
	/**
	 * Return a vector with all bills stored in neodatis database
	 * @return
	 * @throws InvalidNeodatisException
	 */
	public Vector<Bill> listBills() throws InvalidNeodatisException {
		Vector<Bill> vector = new Vector<Bill>();
		
		this.openDatabase();
		
		Objects<Bill> objs = odb.getObjects(Bill.class);
		while (objs.hasNext()) {
			vector.add(objs.next());
		}
		
		this.closeDatabase();
		
		return vector;
	}
 }
