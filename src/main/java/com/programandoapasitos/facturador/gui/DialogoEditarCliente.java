package com.programandoapasitos.facturador.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.programandoapasitos.facturador.db.ManejadorSQLite;
import com.programandoapasitos.facturador.excepciones.ExcepcionEmailInvalido;
import com.programandoapasitos.facturador.excepciones.ExcepcionTelefonoInvalido;
import com.programandoapasitos.facturador.participantes.Cliente;
import com.programandoapasitos.facturador.participantes.Email;
import com.programandoapasitos.facturador.participantes.Telefono;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;
import com.programandoapasitos.facturador.utiles.Transferencia;

@SuppressWarnings("serial")
public class DialogoEditarCliente extends JDialog {

	// Propiedades
	private ManejadorSQLite manejadorSql;
	private Cliente cliente;
	
	private DefaultListModel<String> dlmEmails;
	private DefaultListModel<String> dlmTelefonos;
	
	private JButton btnAgregarEmail;
	private JButton btnAgregarTelefono;
	private JButton btnBorrarEmail;
	private JButton btnBorrarTelefono;
	private JButton btnOk;
	private JButton btnCancelar;
	
	private JLabel lblDni;
	private JLabel lblLocalidad;
	private JLabel lblNombre;
	private JLabel lblCodigoPostal;
	private JLabel lblProvincia;
	private JLabel lblCalle;
	private JLabel lblApellidos;
	
	private JList<String> listEmails;
	private JList<String> listTelefonos;
	
	private JPanel paneContenedor;
	private JPanel paneBoton;
	
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

	private int codigoCliente;
	
	// Constructor
	public DialogoEditarCliente(int codigoCliente) {
		this.manejadorSql = new ManejadorSQLite();
		this.cliente = new Cliente();
		this.codigoCliente = codigoCliente;
		this.inicializar();
	}
	
	// Métodos
	
	/**
	 * Inicializa todo el JDialog
	 */
	private void inicializar() {
		setBounds(100, 100, ManejadorProperties.verGui("SUBFRAMES_WIDTH"), ManejadorProperties.verGui("SUBFRAMES_HEIGHT"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(ManejadorProperties.verRuta("ICONO")).getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(ManejadorProperties.verLiteral("CLIENT_EDIT"));
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		this.inicializarPaneles();
		getContentPane().add(this.paneContenedor, BorderLayout.CENTER);
		setVisible(true);
	}
	
	/**
	 * Inicializa los paneles
	 */
	private void inicializarPaneles() {
		this.paneContenedor = new JPanel();
		this.paneContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.paneContenedor.setLayout(null);
		{
			// Panel botonera
			this.paneBoton = new JPanel();
			this.paneBoton.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(this.paneBoton, BorderLayout.SOUTH);
			{
				this.btnOk = new JButton(ManejadorProperties.verLiteral("SAVE_DATA"));
				this.btnOk.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						actualizarCliente();
						dispose();
					}
				});
				this.btnOk.setActionCommand("OK");
				this.paneBoton.add(this.btnOk);
			}
			{
				this.btnCancelar = new JButton(ManejadorProperties.verLiteral("DIALOG_CANCEL_BUTTON"));
				this.btnCancelar.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				this.btnCancelar.setActionCommand("Cancel");
				this.paneBoton.add(this.btnCancelar);
			}
		}
		
		this.inicializarButtons();
		this.inicializarLabels();
		this.inicializarLists();
		this.inicializarPaneles();
		this.inicializarSeparadores();
		this.inicializarTextFields();
		this.cargarCliente();
	}
	
	/**
	 * Inicializa JButtons
	 */
	private void inicializarButtons() {
		
		// Agregar mail
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
		this.paneContenedor.add(this.btnAgregarEmail);
		
		// Agregar telefono
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
		this.paneContenedor.add(this.btnAgregarTelefono);
		
		// Borrar email
		this.btnBorrarEmail = new JButton(ManejadorProperties.verLiteral("REMOVE_ITEM"));
		this.btnBorrarEmail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				borrarItemLista(listEmails.getSelectedIndex(), listEmails, ManejadorProperties.verLiteral("NEW_CLIENT_REMOVED_EMAIL"));
			}
		});
		this.btnBorrarEmail.setBounds(401, 380, 174, 23);
		this.paneContenedor.add(this.btnBorrarEmail);
		
		// Borrar telefono
		this.btnBorrarTelefono = new JButton(ManejadorProperties.verLiteral("REMOVE_ITEM"));
		this.btnBorrarTelefono.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				borrarItemLista(listEmails.getSelectedIndex(), listEmails, ManejadorProperties.verLiteral("NEW_CLIENT_REMOVED_PHONE"));
			}
		});
		this.btnBorrarTelefono.setBounds(107, 380, 174, 23);
		this.paneContenedor.add(this.btnBorrarTelefono);
	}
	
	/**
	 * Inicializa JLabels
	 */
	private void inicializarLabels() {
		this.lblLocalidad = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_LOCALITY"));
		this.lblLocalidad.setBounds(283, 128, 92, 14);
		this.paneContenedor.add(this.lblLocalidad);
		
		this.lblNombre = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_NAME"));
		this.lblNombre.setBounds(10, 11, 92, 14);
		this.paneContenedor.add(this.lblNombre);
		
		lblDni = new JLabel(ManejadorProperties.verLiteral("DNI"));
		lblDni.setBounds(10, 47, 92, 14);
		this.paneContenedor.add(lblDni);
		
		this.lblCodigoPostal = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_POSTAL_CODE"));
		this.lblCodigoPostal.setBounds(10, 128, 92, 14);
		this.paneContenedor.add(this.lblCodigoPostal);
		
		this.lblProvincia= new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_PROVINCE"));
		this.lblProvincia.setBounds(10, 153, 92, 14);
		this.paneContenedor.add(this.lblProvincia);
		
		this.lblCalle = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_STREET"));
		this.lblCalle.setBounds(10, 100, 92, 14);
		this.paneContenedor.add(this.lblCalle);
		
		this.lblApellidos = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_SURNAMES"));
		this.lblApellidos.setBounds(283, 11, 92, 14);
		this.paneContenedor.add(this.lblApellidos);
	}
	
	/**
	 * Inicializa JLists
	 */
	private void inicializarLists() {
		
		this.scrollTelefono = new JScrollPane();
		this.scrollTelefono.setBounds(95, 235, 199, 134);
		this.paneContenedor.add(this.scrollTelefono);
		
		this.listTelefonos = new JList<String>();
		this.dlmTelefonos = new DefaultListModel<String>();
		this.listTelefonos.setModel(this.dlmTelefonos);
		this.listTelefonos.setSelectedIndex(-1);
		this.scrollTelefono.setViewportView(this.listTelefonos);
		
		this.scrollEmail = new JScrollPane();
		this.scrollEmail.setBounds(389, 235, 199, 134);
		this.paneContenedor.add(scrollEmail);
		
		this.listEmails = new JList<String>();
		this.dlmEmails = new DefaultListModel<String>();
		this.listEmails.setModel(this.dlmEmails);
		this.listEmails.setSelectedIndex(-1);
		this.scrollEmail.setViewportView(this.listEmails);
	}
	
	/**
	 * Inicializa JSeparators
	 */
	private void inicializarSeparadores() {
		this.separator1 = new JSeparator();
		this.separator1.setBounds(104, 82, 475, 2);
		this.paneContenedor.add(separator1);
		
		this.separator2 = new JSeparator();
		this.separator2.setBounds(104, 178, 475, 2);
		this.paneContenedor.add(this.separator2);
	}
	
	/**
	 * Inicializa JTextFields
	 */
	private void inicializarTextFields() {
		
		this.txtLocalidad = new JTextField();
		this.txtLocalidad.setBounds(346, 125, 262, 20);
		this.txtLocalidad.setColumns(10);
		this.paneContenedor.add(this.txtLocalidad);
		
		this.txtNombre = new JTextField();
		this.txtNombre.setBounds(104, 8, 169, 20);
		this.txtNombre.setColumns(10);
		this.paneContenedor.add(this.txtNombre);
		
		this.txtDni = new JTextField();
		this.txtDni.setBounds(104, 44, 169, 20);
		this.paneContenedor.add(this.txtDni);
		
		this.txtCodigoPostal = new JTextField();
		this.txtCodigoPostal.setBounds(104, 125, 169, 20);
		this.txtCodigoPostal.setColumns(10);
		this.paneContenedor.add(this.txtCodigoPostal);
		
		this.txtProvincia = new JTextField();
		this.txtProvincia.setBounds(104, 150, 169, 20);
		this.txtProvincia.setColumns(10);
		this.paneContenedor.add(this.txtProvincia);
		
		this.txtCalle = new JTextField();
		this.txtCalle.setBounds(104, 97, 504, 20);
		this.txtCalle.setColumns(10);
		this.paneContenedor.add(this.txtCalle);
		
		this.txtApellidos = new JTextField();
		this.txtApellidos.setBounds(346, 8, 262, 20);
		this.txtApellidos.setColumns(10);
		this.paneContenedor.add(this.txtApellidos);
	}
	
	/**
	 * Borra un item de la lista
	 * @param index Indice del item
	 * @param lista Lista de la que borrar el item
	 * @param mensaje Mensaje si el borrado es correcto
	 */
	private void borrarItemLista(int index, JList<String> lista, String mensaje) {
		if (index != -1) {
			DefaultListModel<String> modelo = (DefaultListModel<String>)lista.getModel();
			modelo.remove(index);
			Mensajes.verMensajeInformacion(mensaje);
		}
		else {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("NEW_CLIENT_ERROR_REMOVE"), null);
			Transferencia.CLIENTE = null;
		}
	}
	
	/**
	 * Extrae el prefijo y el número de teléfono de un string
	 * @param numeroCompleto Srting con prefijo y número separado por un espacio
	 * @return Array. Posicion 0 = prefijo. Posicion 1 = numero
	 */
	private String[] extraerTelefono(String numeroCompleto) {
		String prefijo = "";
		String numero = "";
		String[] telefono = new String[2];
		boolean turno = true; // True = turno del prefijo. False = turno del teléfono
		
		for (char i : numeroCompleto.toCharArray()) {
			if (String.valueOf(i).equals(" ")) {
				turno = false;
			}
			else {
				if (turno) { prefijo += String.valueOf(i); }
				else { numero += String.valueOf(i); }
			}
		}
		
		telefono[0] = prefijo;
		telefono[1] = numero;
		
		return telefono;
	}
	
	/**
	 * Carga los datos de un cliente desde DB usando el código de cliente
	 */
	private void cargarCliente()  {
		try {
			this.cliente = this.manejadorSql.devolverClienteDesdeId(this.codigoCliente);
			this.txtNombre.setText(this.cliente.getNombre());
			this.txtApellidos.setText(this.cliente.getApellidos());
			this.txtDni.setText(this.cliente.getDni());
			this.txtCalle.setText(this.cliente.getDireccion().getCalle());
			this.txtCodigoPostal.setText(this.cliente.getDireccion().getCodigoPostal());
			this.txtLocalidad.setText(this.cliente.getDireccion().getLocalidad());
			this.txtProvincia.setText(this.cliente.getDireccion().getProvincia());
			
			for (Telefono telefono : this.cliente.getTelefonos()) {
				this.dlmTelefonos.addElement(telefono.getPrefijo() + " " + telefono.getNumero());
			}
			for (Email email : this.cliente.getEmails()) {
				this.dlmEmails.addElement(email.getEmail());
			}
		}
		catch (SQLException | ClassNotFoundException | IOException e) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_CLIENT_FIELDS"), null);
		} 
		catch (ExcepcionTelefonoInvalido | ExcepcionEmailInvalido ex) {
			Mensajes.verMensajeError(ex.getMessage(), null);
		}
	}
	
	/**
	 * Actualiza las filas en las tablas de clients, phones y emails
	 */
	private void actualizarCliente() {
		try {
			
			// Primero borro los telefonos y emails
			this.manejadorSql.borrarFilasTelefonosEmailsPorCliente(this.cliente.getCodigoCliente(), "phones");
			this.manejadorSql.borrarFilasTelefonosEmailsPorCliente(this.cliente.getCodigoCliente(), "emails");
			
			// Inserto emails
			Vector<Email> emails = new Vector<Email>();
			for (int i = 0; i < this.dlmEmails.size(); i++) {
				Email email = new Email(this.dlmEmails.getElementAt(i));
				emails.add(email);
			}
			if (!emails.isEmpty() && emails.size() > 0 && emails != null) {
				this.manejadorSql.insertarMail(this.cliente.getCodigoCliente(), emails);
			}
			
			// Inserto teléfonos
			Vector<Telefono> telefonos = new Vector<Telefono>();
			for (int i = 0; i < this.dlmTelefonos.size(); i++) {
				String[] numeroTelefono = this.extraerTelefono(this.dlmTelefonos.getElementAt(i));
				Telefono telefono = new Telefono(numeroTelefono[0], numeroTelefono[1]);
				telefonos.add(telefono);
			}
			if (telefonos.isEmpty() && telefonos.size() > 0 && telefonos != null) {
				this.manejadorSql.insertarTelefonos(this.cliente.getCodigoCliente(), telefonos);
			}
			
			// Actualizo la información básica del cliente y su dirección
			this.manejadorSql.actualizarCliente(this.cliente.getCodigoCliente(), this.txtDni.getText(), this.txtNombre.getText(), this.txtApellidos.getText(), this.txtCalle.getText(), this.txtCodigoPostal.getText(), this.txtLocalidad.getText(), this.txtProvincia.getText());
			
		} 
		catch (ClassNotFoundException | SQLException | IOException e) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_INSERT_CLIENT"), null);
		} 
		catch (ExcepcionEmailInvalido | ExcepcionTelefonoInvalido e) {
			Mensajes.verMensajeError(e.getMessage(), null);
		}
		
	}
}
