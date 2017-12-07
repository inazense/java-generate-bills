package com.programandoapasitos.facturador.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import com.programandoapasitos.facturador.excepciones.ExcepcionEmailInvalido;
import com.programandoapasitos.facturador.excepciones.ExcepcionFacturaInvalida;
import com.programandoapasitos.facturador.excepciones.ExcepcionPagoInvalido;
import com.programandoapasitos.facturador.excepciones.ExcepcionTelefonoInvalido;
import com.programandoapasitos.facturador.participantes.Cliente;
import com.programandoapasitos.facturador.participantes.Direccion;
import com.programandoapasitos.facturador.participantes.Email;
import com.programandoapasitos.facturador.participantes.Factura;
import com.programandoapasitos.facturador.participantes.Pago;
import com.programandoapasitos.facturador.participantes.Telefono;

public class ManejadorSQLite {

	// Propiedades
	//private ResultSet resultset;
	//private String sql;
	
	// Constructor
	public ManejadorSQLite() {}
	
	// Métodos
	
	/**
	 * Inserta, borra o actualiza una sentencia SQL
	 * @param sql Sentencia SQL
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	private void accionDB(String sql) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		SQLiteSingleton.devolverConexion().createStatement().executeUpdate(sql);
	}
	
	/**
	 * Devuelve los resultados de la base de datos
	 * @param sql Sentencia SQL
	 * @return Resulset con los resultados de la consulta SQL
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	private ResultSet consultaDB(String sql) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		return SQLiteSingleton.devolverConexion().createStatement().executeQuery(sql);
	}
	
	/**
	 * Devuelve el máximo id de tabla clientes
	 * @return
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public int maximoNumeroDeCliente() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "SELECT MAX(id) FROM clients";
		ResultSet rs = this.consultaDB(sql);
		
		if (!rs.next()) {
			return 0;
		}
		else {
			return rs.getInt(1);
		}
	}
	
	/**
	 * Inserta un cliente en la base de datos
	 * @param dni no puede ser null
	 * @param nombre no puede ser null
	 * @param apellido
	 * @param calle
	 * @param codigoPostal
	 * @param localidad
	 * @param provincia
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void insertarCliente(String dni, String nombre, String apellido, String calle, String codigoPostal, String localidad, String provincia) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "INSERT INTO clients(dni, name, surname, street, postalCode, locality, province) VALUES ('" + dni + "', '" + nombre + "', '" + apellido + "', '" + calle + "', '" + codigoPostal + "', '" + localidad + "', '" + provincia + "')";
		
		this.accionDB(sql);
	}
	
	/**
	 * Inserta correos electrónicos de un cliente en la base de datos
	 * @param codigoCliente no puede ser null
	 * @param emails no puede ser null
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void insertarMail(int codigoCliente, Vector<Email> emails) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		for (Email email : emails) {
			String sql = "INSERT INTO emails(client, email) VALUES (" + codigoCliente + ", '" + email.getEmail() + "')";
			this.accionDB(sql);
		}
	}
	
	/**
	 * Inserta telefonos de un cliente en la base de datos
	 * @param codigoCliente no puede ser null
	 * @param telefonos no puede se null
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void insertarTelefonos(int codigoCliente, Vector<Telefono> telefonos) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		for (Telefono telefono : telefonos) {
			String sql = "INSERT INTO phones(client, prefix, number) VALUES (" + codigoCliente + ", '" + telefono.getPrefijo() + "', '" + telefono.getNumero()+ "')";
			this.accionDB(sql);
		}
	}
	
	/**
	 * Insert bills into database
	 * @param Factura Objeto a insertar (sin pagos)
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws Exception 
	 */
	public void insertarFactura(Factura factura) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "INSERT INTO bills(id, client, vat, date) VALUES ('" + factura.getNumeroFactura() + "', " + factura.getCliente().getCodigoCliente() + ", " + factura.getIva() + ", '" + factura.getFecha() + "')";
		this.accionDB(sql);
	}
	
	/**
	 * Inserta los pagos de una factura en la base de datos
	 * @param idFactura
	 * @param pagos
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void insertarPagos(String idFactura, Vector<Pago> pagos) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		for (Pago pago : pagos) {
			String sql = "INSERT INTO payments(bill, concept, amount) VALUES('" + idFactura + "', '" + pago.getConcepto() + "', " + pago.getCantidad() + ")";
			this.accionDB(sql);
		}
	}
	
	/**
	 * Devuelve el nombre, apellidos, localidad y código de cliente para visualizarlos en la búsqueda de clientes
	 * @return Hashmap
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public HashMap<String, Integer> verTodosLosClientes() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		HashMap<String, Integer> clientes = new HashMap<String, Integer>();
		String sql = "SELECT * FROM clientes";
		ResultSet rs = this.consultaDB(sql);
		
		while(rs.next()) {
			String clave = rs.getString("name") + " " + rs.getString("surname") + " - " + rs.getString("locality");
			int nombre = rs.getInt("id");
			clientes.put(clave, nombre);
		}
		
		rs.close();
		rs = null;
		
		return clientes;
	}
	
	/**
	 * Devuele el nombre, apellidos, localidad y código del cliente
	 * @param filtro
	 * @param valor
	 * @return HashMap con nombre, apellidos, localidad y código de cliente
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public HashMap<String, Integer> verClientesFiltrados(String filtro, String valor) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		HashMap<String, Integer> clientes = new HashMap<String, Integer>();
		String sql = "";
		switch (filtro) {
			case "number":
				sql = "SELECT DISTINCT id, name, surname, locality FROM clients, phones WHERE number like '%" + valor + "%' and id = client";
				break;
			case "email":
				sql = "SELECT DISTINCT id, name, surname, locality FROM clients, emails WHERE email like '%" + valor + "%' and id = client";
				break;
			default:
				sql = "SELECT * FROM clients WHERE " + filtro + " like '%" + valor + "%'";
		}
		
		ResultSet rs = this.consultaDB(sql);
		while (rs.next()) {
			String clave = rs.getString("name") + " " + rs.getString("surname") + " - " + rs.getString("locality");
			int nombre = rs.getInt("id");
			clientes.put(clave, nombre);
		}
		
		rs.close();
		rs = null;
		
		return clientes;
	}
	
	/**
	 * Devuelve todas las facturas
	 * @return
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Vector<String[]> verTodasLasFacturas() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		Vector<String[]> facturas = new Vector<String[]>();
		String sql = "SELECT bills.id, name, surname, locality, date FROM bills, clients WHERE clients.id = bills.client";
		ResultSet rs = this.consultaDB(sql);
		
		while (rs.next()) {
			String[] factura = {rs.getString("id"), rs.getString("name") + " " + rs.getString("surname") + " - " + rs.getString("locality"), rs.getString("date")};
			facturas.add(factura);
		}
		
		rs.close();
		rs = null;
		
		return facturas;
	}
	
	/**
	 * Devuelve id y fecha de la tabla facturas y nombre, apellidos y localidad de la tabla clientes
	 * @param filtro
	 * @param valor
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Vector<String[]> verFacturasFiltradas(String filtro, String valor) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		
		Vector<String[]> facturas = new Vector<String[]>();
		String sql = "";
		if (filtro.equals("id")) {
			sql = "SELECT bills.id, name, surname, locality, date FROM bills, clients WHERE bills.id like '%" + valor + "%' AND clients.id = bills.client"; 
		}
		else if (filtro.equals("date")) {
			sql = "SELECT bills.id, name, surname, locality, date FROM bills, clients WHERE date = '" + valor + "' AND clients.id = bills.client";
		}
		else if (filtro.equals("client")) {
			sql = "SELECT bills.id, name, surname, locality, date FROM bills, clients WHERE client = " + valor + " AND clients.id = bills.client";
		}
		
		ResultSet rs = this.consultaDB(sql);
		while (rs.next()) {
			String[] factura = {rs.getString("id"), rs.getString("name") + " " + rs.getString("surname") + " - " + rs.getString("locality"), rs.getString("date")};
			facturas.add(factura);
		}
		
		rs.close();
		rs = null;
		return facturas;
	}
	
	/**
	 * Devuelve un cliente filtrando por ID
	 * @param id
	 * @return Cliente con con los datos básicos, dirección, teléfonos y emails
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 * @throws ExcepcionTelefonoInvalido 
	 * @throws ExcepcionEmailInvalido 
	 */
	public Cliente devolverClienteDesdeId(int id) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException, ExcepcionTelefonoInvalido, ExcepcionEmailInvalido {
		
		Cliente c = new Cliente();
		
		// Cliente y dirección
		String sql = "SELECT * FROM clients WHERE id = " + id;
		ResultSet rs = this.consultaDB(sql);
		while (rs.next()) {
			c.setCodigoCliente(id);
			c.setNombre(rs.getString("name"));
			c.setApellidos(rs.getString("surname"));
			c.setDni(rs.getString("dni"));
			c.setDireccion(new Direccion(rs.getString("street"), rs.getString("postalCode"), rs.getString("locality"), rs.getString("province")));
		}
		
		rs.close();
		rs = null;
		
		// Teléfonos
		sql = "SELECT * FROM phones WHERE client = " + id;
		rs = this.consultaDB(sql);
		Vector<Telefono> telefonos = new Vector<Telefono>();
		while (rs.next()) {
			telefonos.add(new Telefono(rs.getString("prefix"), rs.getString("number")));
		}
		c.setTelefonos(telefonos);
		
		rs.close();
		rs = null;
		
		// Emails
		sql = "SELECT * FROM emails WHERE client = " + id;
		Vector<Email> emails = new Vector<Email>();
		rs = this.consultaDB(sql);
		while (rs.next()) {
			emails.add(new Email(rs.getString("email")));
		}
		c.setEmails(emails);
		
		rs.close();
		rs = null;
		
		return c;
	}
	
	/**
	 * Devuelve una factura filtrando por el id
	 * @param id
	 * @return Factura completa, con su cliente y sus pagos correspondientes
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ExcepcionFacturaInvalida
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws ExcepcionTelefonoInvalido
	 * @throws ExcepcionEmailInvalido
	 * @throws ExcepcionPagoInvalido
	 */
	public Factura devolverFacturaDesdeId(String id) throws SQLException, FileNotFoundException, ExcepcionFacturaInvalida, IOException, ClassNotFoundException, ExcepcionTelefonoInvalido, ExcepcionEmailInvalido, ExcepcionPagoInvalido {
		
		Factura f = new Factura();
		int codigoCliente = -1;
		
		// Información básica
		String sql = "SELECT * FROM bills WHERE id = '" + id + "'";
		ResultSet rs = this.consultaDB(sql);
		while (rs.next()) {
			f.setNumeroFactura(rs.getString("id"));
			f.setFecha(rs.getString("date"));
			f.setIva(rs.getDouble("vat"));
			codigoCliente = rs.getInt("client");
		}
		
		rs.close();
		rs = null;
		
		// Cliente
		f.setCliente(this.devolverClienteDesdeId(codigoCliente));
		
		// Pagos
		Vector<Pago> pagos = new Vector<Pago>();
		
		sql = "SELECT * FROM payments WHERE bill = '" + id + "'";
		rs = this.consultaDB(sql);
		while (rs.next()) {
			Pago pago = new Pago(rs.getString("concept"), rs.getDouble("amount"));
			pagos.add(pago);
		}
		f.setPagos(pagos);
		
		rs.close();
		rs = null;
		
		return f;
	}
	
	/**
	 * Borra todas las filas de las tablas phones o emails que contengan un código de cliente determinado
	 * @param codigoCliente Código de cliente
	 * @param tabla Nombre de la tabla
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void borrarFilasTelefonosEmailsPorCliente(int codigoCliente, String tabla) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		if (tabla.equals("phones") || tabla.equals("emails")) {
			String sql = "DELETE FROM " + tabla + " WHERE client = " + codigoCliente;
			this.accionDB(sql);
		}
	}
	
	/**
	 * Borra todas las filas de las tablas bill y payments filtrando por id de factura
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void borrarFacturaPorId(String id) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		
		String sql = "DELETE FROM payments WHERE bill = '" + id + "'";
		this.consultaDB(sql);
		
		sql = "DELETE FROM bills WHERE id = '" + id + "'";
		this.consultaDB(sql);
	}
	
	/**
	 * Actualiza una fila en la tabla clients filtrando por código de cliente
	 * @param codigoCliente
	 * @param dni
	 * @param nombre
	 * @param apellidos
	 * @param calle
	 * @param codigoPostal
	 * @param localidad
	 * @param provicia
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void actualizarCliente(int codigoCliente, String dni, String nombre, String apellidos, String calle, String codigoPostal, String localidad, String provicia) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String sql = "UPDATE clients SET dni = '" + dni 
				+ "', name = '" + nombre 
				+ "', surname = '" + apellidos 
				+ "', street = '" + calle 
				+ "', postalCode = '" + codigoPostal 
				+ "', locality = '" + localidad 
				+ "', province = '" + provicia 
				+ "' WHERE id = " + codigoCliente;
		
		this.consultaDB(sql);
	}
}