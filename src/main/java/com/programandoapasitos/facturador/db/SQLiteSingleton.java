package com.programandoapasitos.facturador.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.programandoapasitos.facturador.utiles.ManejadorProperties;

/**
 * Patrón singleton para obtener conexiones SQLite
 * @author Inazio
 *
 */
public class SQLiteSingleton {

	// Propiedades
	private static Connection CONEXION = null;
	private final String driver = "org.sqlite.JDBC";
	private String ruta;
	
	// Constructor
	private SQLiteSingleton() throws ClassNotFoundException, SQLException {
		
		boolean existeDB = this.existeLaBaseDeDatos();
		
		ruta = "jdbc:sqlite:" + ManejadorProperties.verRuta("BASE_DATOS");
		Class.forName(driver);
		CONEXION = DriverManager.getConnection(ruta);
		
		if (!existeDB) { this.crearTablas(); }
	}
	
	// Métodos
	
	/**
	 * Devuelve una conexión a la base de datos SQLite
	 * @return Objeto Connection a la base de datos SQLite
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static Connection devolverConexion() throws ClassNotFoundException, SQLException {
		if (CONEXION == null) {
			new SQLiteSingleton();
		}
		
		return CONEXION;
	}
	
	/**
	 * Comprueba si el fichero de la base de datos existe antes de crear el conector
	 * @return True si existe, false en caso contrario
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private boolean existeLaBaseDeDatos() {
		
		File db = new File(ManejadorProperties.verRuta("BASE_DATOS"));
		
		return db.exists();
	}
	/**
	 * Genera la estructura de tablas de la base de datos
	 * @throws SQLException
	 */
	private void crearTablas() throws SQLException {
		String sql = "CREATE TABLE emails (" + 
				"	client INTEGER NOT NULL," + 
				"	email TEXT NOT NULL," + 
				"	PRIMARY KEY(client, email)" + 
				"	FOREIGN KEY(client) REFERENCES clients(id)" + 
				");" + 
				"" + 
				"CREATE TABLE phones (" + 
				"	client INTEGER NOT NULL," + 
				"	prefix TEXT NOT NULL," + 
				"	number TEXT NOT NULL," + 
				"	PRIMARY KEY(client, prefix, number)" + 
				"	FOREIGN KEY(client) REFERENCES clients(id)" + 
				");" + 
				"" + 
				"CREATE TABLE clients (" + 
				"	id INTEGER PRIMARY KEY," + 
				"	dni TEXT NOT NULL," + 
				"	name TEXT NOT NULL," + 
				"	surname TEXT NOT NULL," + 
				"	street TEXT," + 
				"	postalCode TEXT," + 
				"	locality TEXT," + 
				"	province TEXT" + 
				");" + 
				"" + 
				"CREATE TABLE payments (" + 
				"	id INTEGER PRIMARY KEY," + 
				"	bill INTEGER NOT NULL," + 
				"	concept TEXT NOT NULL," + 
				"	amount DOUBLE NOT NULL," + 
				"	FOREIGN KEY(bill) REFERENCES bills(id)" + 
				");" + 
				"" + 
				"CREATE TABLE bills (" + 
				"	id TEXT PRIMARY KEY," + 
				"	client INTEGER NOT NULL," + 
				"	vat DOUBLE NOT NULL," + 
				"	date TEXT NOT NULL," + 
				"	FOREIGN KEY(client) REFERENCES clients(id)" + 
				");";
		
		Statement stmt = CONEXION.createStatement();
		stmt.execute(sql);
		
		stmt.close();
		stmt = null;
	}
}
