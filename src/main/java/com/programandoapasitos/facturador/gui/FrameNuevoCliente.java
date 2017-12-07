package com.programandoapasitos.facturador.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.programandoapasitos.facturador.db.ManejadorSQLite;
import com.programandoapasitos.facturador.excepciones.ExcepcionCamposNuevoCliente;
import com.programandoapasitos.facturador.excepciones.ExcepcionEmailInvalido;
import com.programandoapasitos.facturador.excepciones.ExcepcionTelefonoInvalido;
import com.programandoapasitos.facturador.gui.superclases.FramePadre;
import com.programandoapasitos.facturador.participantes.Cliente;
import com.programandoapasitos.facturador.participantes.Direccion;
import com.programandoapasitos.facturador.participantes.Email;
import com.programandoapasitos.facturador.participantes.Telefono;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;
import com.programandoapasitos.facturador.utiles.Transferencia;

@SuppressWarnings("serial")
public class FrameNuevoCliente extends FramePadre {

	// Properties
	private ManejadorSQLite manejadorSQLite;
	
	private DefaultListModel<String> dlmEmails;
	private DefaultListModel<String> dlmTelefonos;
	
	private JButton btnAgregarEmail;
	private JButton btnAgregarTelefono;
	private JButton btnBorrarEmail;
	private JButton btnBorrarTelefono;
	private JButton btnGuardarDatos;
	
	private JLabel lblDni;
	private JLabel lblLocalidad;
	private JLabel lblNombre;
	private JLabel lblCodigoPostal;
	private JLabel lblProvincia;
	private JLabel lblCalle;
	private JLabel lblApellidos;
	
	private JList<String> listEmails;
	private JList<String> listTelefonos;
	
	private JSeparator separator1;
	private JSeparator separator2;
	
	private JScrollPane scrollEmail;
	private JScrollPane scrollTelefono;
	
	private JTextField txtDni;
	private JTextField txtLocalidad;
	private JTextField txtNombre;
	private JTextField txtCodigoPostal;
	private JTextField txtProvincia;
	private JTextField txtCalle;
	private JTextField txtApellidos;
	
	// Constructor
	public FrameNuevoCliente(int ancho, int alto, String titulo) {
		super(ancho, alto, titulo);
		this.inicializar();
	}
	
	// Métodos
	
	/**
	 * Inicializa el JPanel
	 */
	private void inicializar() {
		setVisible(true);
		this.inicializarLabels();
		this.inicializarSeparators();
		this.inicializarTextFields();
		this.inicializarLists();
		this.inicializarButtons();
	}
	
	private void inicializarLabels() {
		this.lblLocalidad = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_LOCALITY"));
		this.lblLocalidad.setBounds(283, 128, 92, 14);
		this.panel.add(this.lblLocalidad);
		
		this.lblNombre = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_NAME"));
		this.lblNombre.setBounds(10, 11, 92, 14);
		this.panel.add(this.lblNombre);
		
		this.lblCodigoPostal = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_POSTAL_CODE"));
		this.lblCodigoPostal.setBounds(10, 128, 92, 14);
		this.panel.add(this.lblCodigoPostal);
		
		this.lblProvincia = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_PROVINCE"));
		this.lblProvincia.setBounds(10, 153, 92, 14);
		this.panel.add(this.lblProvincia);
		
		this.lblCalle = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_STREET"));
		this.lblCalle.setBounds(10, 100, 92, 14);
		this.panel.add(this.lblCalle);
		
		this.lblApellidos = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_SURNAMES"));
		this.lblApellidos.setBounds(283, 11, 92, 14);
		this.panel.add(this.lblApellidos);
		
		this.lblDni = new JLabel(ManejadorProperties.verLiteral("DNI"));
		this.lblDni.setBounds(10, 47, 92, 14);
		this.panel.add(this.lblDni);
	}
	
	private void inicializarSeparators() {
		this.separator1 = new JSeparator();
		this.separator1.setBounds(104, 82, 475, 2);
		this.panel.add(this.separator1);
		
		this.separator2 = new JSeparator();
		this.separator2.setBounds(104, 178, 475, 2);
		this.panel.add(this.separator2);
	}
	
	private void inicializarTextFields() {
		this.txtLocalidad = new JTextField();
		this.txtLocalidad.setBounds(346, 125, 262, 20);
		this.txtLocalidad.setColumns(10);
		this.panel.add(this.txtLocalidad);
		
		this.txtNombre = new JTextField();
		this.txtNombre.setBounds(104, 8, 169, 20);
		this.txtNombre.setColumns(10);
		this.panel.add(this.txtNombre);
		
		this.txtCodigoPostal = new JTextField();
		this.txtCodigoPostal.setBounds(104, 125, 169, 20);
		this.txtCodigoPostal.setColumns(10);
		this.panel.add(this.txtCodigoPostal);
		
		this.txtProvincia = new JTextField();
		this.txtProvincia.setBounds(104, 150, 169, 20);
		this.txtProvincia.setColumns(10);
		this.panel.add(this.txtProvincia);
		
		this.txtCalle = new JTextField();
		this.txtCalle.setBounds(104, 97, 504, 20);
		this.txtCalle.setColumns(10);
		this.panel.add(this.txtCalle);
		
		this.txtApellidos = new JTextField();
		this.txtApellidos.setBounds(346, 8, 262, 20);
		this.txtApellidos.setColumns(10);
		this.panel.add(this.txtApellidos);
		
		this.txtDni = new JTextField();
		txtDni.setBounds(104, 42, 169, 20);
		txtDni.setColumns(10);
		this.panel.add(txtDni);
	}
	
	private void inicializarLists() {
		this.scrollTelefono = new JScrollPane();
		this.scrollTelefono.setBounds(95, 235, 199, 134);
		this.panel.add(this.scrollTelefono);
		
		this.listTelefonos = new JList<String>();
		this.dlmTelefonos = new DefaultListModel<String>();
		this.listTelefonos.setModel(this.dlmTelefonos);
		this.listTelefonos.setSelectedIndex(-1);
		this.scrollTelefono.setViewportView(this.listTelefonos);
		
		this.scrollEmail = new JScrollPane();
		this.scrollEmail.setBounds(389, 235, 199, 134);
		this.panel.add(this.scrollEmail);
		
		this.listEmails = new JList<String>();
		this.dlmEmails = new DefaultListModel<String>();
		this.listEmails.setModel(this.dlmEmails);
		this.listEmails.setSelectedIndex(-1);
		this.scrollEmail.setViewportView(listEmails);
	}
	
	private void inicializarButtons() {
		// Agregar email
		this.btnAgregarEmail = new JButton(ManejadorProperties.verLiteral("NEW_CLIENT_ADD_EMAIL"));
		this.btnAgregarEmail.setBounds(389, 193, 199, 23);
		this.btnAgregarEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				DialogoNuevoEmail nuevoEmail = new DialogoNuevoEmail();
				if (!Transferencia.EMAIL.equals("")) {
					dlmEmails.addElement(Transferencia.EMAIL);
					Transferencia.EMAIL = "";
				}
			}
		});
		this.panel.add(this.btnAgregarEmail);
		
		// Agregar teléfono
		this.btnAgregarTelefono = new JButton(ManejadorProperties.verLiteral("NEW_CLIENT_ADD_PHONE"));
		this.btnAgregarTelefono.setBounds(95, 193, 199, 23);
		this.btnAgregarTelefono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				DialogoNuevoTelefono nuevoTelefono = new DialogoNuevoTelefono();
				if (!Transferencia.PREFIJO.equals("") && !Transferencia.TELEFONO.equals("")) {
					dlmTelefonos.addElement(Transferencia.PREFIJO + " " + Transferencia.TELEFONO);
					Transferencia.PREFIJO = "";
					Transferencia.TELEFONO = "";
				}
				
			}
		});
		this.panel.add(this.btnAgregarTelefono);
		
		// Borrar email
		this.btnBorrarEmail = new JButton(ManejadorProperties.verLiteral("REMOVE_ITEM"));
		this.btnBorrarEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarItemLista(listEmails.getSelectedIndex(), listEmails, ManejadorProperties.verLiteral("NEW_CLIENT_REMOVED_EMAIL"));
			}
		});
		this.btnBorrarEmail.setBounds(401, 380, 174, 23);
		this.panel.add(this.btnBorrarEmail);
		
		// Borrar teléfono
		this.btnBorrarTelefono = new JButton(ManejadorProperties.verLiteral("REMOVE_ITEM"));
		this.btnBorrarTelefono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				borrarItemLista(listTelefonos.getSelectedIndex(), listTelefonos, ManejadorProperties.verLiteral("NEW_CLIENT_REMOVED_PHONE"));
			}
		});
		this.btnBorrarTelefono.setBounds(107, 380, 174, 23);
		this.panel.add(this.btnBorrarTelefono);
		
		// Guardar
		this.btnGuardarDatos = new JButton(ManejadorProperties.verLiteral("SAVE_DATA"));
		this.btnGuardarDatos.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					guardarCliente();
				}
				catch(ExcepcionCamposNuevoCliente | ExcepcionTelefonoInvalido | ExcepcionEmailInvalido error) {
					Mensajes.verMensajeError(error.getMessage(), null);
				}
			}
		});
		this.btnGuardarDatos.setBounds(585, 427, 89, 23);
		this.panel.add(this.btnGuardarDatos);
	}
	
	/**
	 * Borra el item seleccionado de una lista
	 * @param indice
	 * @param lista
	 * @param mensaje
	 */
	private void borrarItemLista(int indice, JList<String> lista, String mensaje) {
		if (indice != -1) {
			DefaultListModel<String> model = (DefaultListModel<String>) lista.getModel();
            model.remove(indice);
            Mensajes.verMensajeInformacion(mensaje);
		}
		else {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("NEW_CLIENT_ERROR_REMOVE"), null);
			Transferencia.CLIENTE = null;
		}
	}
	
	
	private void guardarCliente() throws ExcepcionCamposNuevoCliente, ExcepcionTelefonoInvalido, ExcepcionEmailInvalido {
		if (this.txtDni.getText().equals("")) {
			throw new ExcepcionCamposNuevoCliente(ManejadorProperties.verLiteral("MANDATORY_DNI"));
		}
		if (this.txtNombre.getText().equals("")) {
			throw new ExcepcionCamposNuevoCliente(ManejadorProperties.verLiteral("MANDATORY_CLIENT_NAME"));
		}
		else if (this.txtApellidos.getText().equals("")) {
			throw new ExcepcionCamposNuevoCliente(ManejadorProperties.verLiteral("MANDATORY_CLIENT_SURNAME"));
		}
		else {
			// Direccion
			Direccion direccion = new Direccion();
			direccion.setLocalidad(this.txtLocalidad.getText());
			direccion.setProvincia(this.txtProvincia.getText());
			direccion.setCodigoPostal(this.txtCodigoPostal.getText());
			direccion.setCalle(this.txtCalle.getText());
			
			// Cliente
			Cliente cliente = new Cliente();
			cliente.setDni(this.txtDni.getText());
			cliente.setNombre(this.txtNombre.getText());
			cliente.setApellidos(this.txtApellidos.getText());
			cliente.setDireccion(direccion);
			
			// Telefono
			for (int i = 0; i < this.dlmTelefonos.getSize(); i++) {
				String[] numero = extraerTelefono(this.dlmTelefonos.getElementAt(i));
				Telefono telefono = new Telefono(numero[0], numero[1]);
				cliente.agregarTelefono(telefono);
			}
			
			// Email
			for (int i = 0; i < this.dlmEmails.getSize(); i++) {
				Email em = new Email(this.dlmEmails.getElementAt(i));
				cliente.agregarEmail(em);
			}

			Transferencia.CLIENTE = cliente;
			this.insertarCliente(cliente);
			
			dispose();
		}
	}
	
	/**
	 * Extrae el prefijo y el número de un telefono
	 * @param numeroCompleto
	 * @return
	 */
	private String[] extraerTelefono(String numeroCompleto) { 
		
		String prefijo = "";
		String numero = "";
		boolean turno = true; // True = turno del prefijo, False = turno del número
		String[] telefono = new String[2];
		
		for (char i : numeroCompleto.toCharArray()) {
			if (String.valueOf(i).equals(" ")) {
				turno = false;
			}
			else {
				if (turno) {
					prefijo += String.valueOf(i);
				}
				else {
					numero += String.valueOf(i);
				}
			}
		}
		telefono[0] = prefijo;
		telefono[1] = numero;
		
		return telefono;
	}
	
	private void insertarCliente(Cliente cliente) {
		// INSERTAR CLIENTE
		String nombre = cliente.getNombre();
		String apellidos = cliente.getApellidos();
		String dni = cliente.getDni();
		String calle;
		String codigoPostal;
		String localidad;
		String provincia;
		if (cliente.getDireccion() == null) {
			calle = null;
			codigoPostal = null;
			localidad = null;
			provincia = null;
		}
		else {
			calle = cliente.getDireccion().getCalle();
			codigoPostal = cliente.getDireccion().getCodigoPostal();
			localidad = cliente.getDireccion().getLocalidad();
			provincia = cliente.getDireccion().getProvincia();
		}
		try {
			this.manejadorSQLite.insertarCliente(dni, nombre, apellidos, calle, codigoPostal, localidad, provincia);
		} catch (SQLException | ClassNotFoundException | IOException e) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_INSERT_CLIENT"), null);
		}
		
		// INSERTAR EMAILS
		if (!cliente.getEmails().isEmpty() && cliente.getEmails().size() > 0 && cliente.getEmails() != null) {
			try {
				this.manejadorSQLite.insertarMail(this.manejadorSQLite.maximoNumeroDeCliente(), cliente.getEmails());
			}
			catch(SQLException | ClassNotFoundException | IOException e) {
				Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_INSERT_EMAILS"), null);
			}
		}
		
		// INSERT PHONES
		if (!cliente.getTelefonos().isEmpty() && cliente.getTelefonos().size() > 0 && cliente.getTelefonos() != null) {
			try {
				this.manejadorSQLite.insertarTelefonos(this.manejadorSQLite.maximoNumeroDeCliente(), cliente.getTelefonos());
			}
			catch(SQLException | ClassNotFoundException | IOException e) {
				Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_INSERT_PHONES"), null);
			}
		}
	}
}
