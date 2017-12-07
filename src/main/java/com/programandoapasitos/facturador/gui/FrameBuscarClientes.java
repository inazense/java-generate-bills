package com.programandoapasitos.facturador.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.programandoapasitos.facturador.db.ManejadorSQLite;
import com.programandoapasitos.facturador.gui.superclases.FramePadre;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;

@SuppressWarnings("serial")
public class FrameBuscarClientes extends FramePadre {

	// Propiedades
	private HashMap<String, Integer> clientes;
	
	private DefaultListModel<String> dlmClientes;
	
	private JButton btnBuscar;
	
	private JList<String> listaClientes;
	
	private JLabel lblDni;
	private JLabel lblEmail;
	private JLabel lblInstrucciones;
	private JLabel lblLista;
	private JLabel lblLocalidad;
	private JLabel lblNombre;
	private JLabel lblTelefono;
	private JLabel lblProvincia;
	private JLabel lblBusqueda;
	private JLabel lblApellidos;
	
	private JSeparator separator;
	
	private JScrollPane scrollPane;
	
	private JTextField txtDni;
	private JTextField txtEmail;
	private JTextField txtLocalidad;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtProvincia;
	private JTextField txtApellidos;
	
	// Constructor
	public FrameBuscarClientes(int ancho, int alto, String titulo) {
		super(ancho, alto, titulo);
		this.inicializar();
	}
	
	// Metodos
	
	private void inicializar() {
		setVisible(true);
		this.inicializarBotones();
		this.inicializarLabels();
		this.inicializarListas();
		this.inicializarSeparators();
		this.inicializarTextFields();
	}
	
	private void inicializarBotones() {
		btnBuscar = new JButton(ManejadorProperties.verLiteral("SEARCH"));
		btnBuscar.setBounds(577, 151, 89, 23);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createFilter();
			}
		});
		this.panel.add(btnBuscar);
	}
	
	private void inicializarLabels() {
		
		lblEmail = new JLabel(ManejadorProperties.verLiteral("CLIENT_EMAIL"));
		lblEmail.setBounds(313, 111, 67, 14);
		this.panel.add(lblEmail);
		
		lblInstrucciones = new JLabel(ManejadorProperties.verLiteral("CLIENT_LIST_INSTRUCTIONS"));
		lblInstrucciones.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInstrucciones.setBounds(132, 203, 270, 14);
		this.panel.add(lblInstrucciones);
		
		lblLista = new JLabel(ManejadorProperties.verLiteral("CLIENT_LIST"));
		lblLista.setBounds(10, 203, 112, 14);
		this.panel.add(lblLista);
		
		lblLocalidad = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_LOCALITY"));
		lblLocalidad.setBounds(16, 76, 67, 14);
		this.panel.add(lblLocalidad);
		
		lblNombre = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_NAME"));
		lblNombre.setBounds(16, 36, 67, 14);
		this.panel.add(lblNombre);
		
		lblTelefono = new JLabel(ManejadorProperties.verLiteral("CLIENT_PHONE"));
		lblTelefono.setBounds(16, 111, 67, 14);
		this.panel.add(lblTelefono);
		
		lblDni = new JLabel(ManejadorProperties.verLiteral("DNI"));
		lblDni.setBounds(16, 146, 67, 14);
		this.panel.add(lblDni);
		
		lblProvincia = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_PROVINCE"));
		lblProvincia.setBounds(313, 76, 67, 14);
		this.panel.add(lblProvincia);
		
		lblApellidos = new JLabel(ManejadorProperties.verLiteral("NEW_CLIENT_SURNAMES"));
		lblApellidos.setBounds(313, 36, 67, 14);
		this.panel.add(lblApellidos);
		
		lblBusqueda = new JLabel(ManejadorProperties.verLiteral("MENU_ITEM_SEARCH_CLIENT"));
		lblBusqueda.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBusqueda.setBounds(294, 11, 95, 14);
		this.panel.add(lblBusqueda);
	}
	
	/**
	 * Initialize lists into frame
	 */
	private void inicializarListas() {
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 228, 664, 223);
		this.panel.add(scrollPane);
		
		listaClientes = new JList<String>();
		dlmClientes = new DefaultListModel<String>();
		listaClientes.setModel(dlmClientes);
		listaClientes.setSelectedIndex(-1);
		
		// Double click event
		listaClientes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					new DialogoEditarCliente(clientes.get(listaClientes.getSelectedValue()));
					try {
						clientes = new ManejadorSQLite().verTodosLosClientes();
						fillList();
					} catch (SQLException | ClassNotFoundException | IOException e) {
						Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_CLIENTS"), null);
					}
				}
			}
		});
		try {
			this.clientes = new ManejadorSQLite().verTodosLosClientes();
			this.fillList();
		} catch (SQLException | ClassNotFoundException | IOException e) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_CLIENTS"), null);
		}
		
		scrollPane.setViewportView(listaClientes);
	}

	private void inicializarSeparators() {
		separator = new JSeparator();
		separator.setBounds(195, 190, 294, 2);
		this.panel.add(separator); 
	}
	
	private void inicializarTextFields() {
		txtEmail = new JTextField();
		txtEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		txtEmail.setColumns(10);
		txtEmail.setBounds(396, 108, 270, 20);
		this.panel.add(txtEmail);
		
		txtLocalidad = new JTextField();
		txtLocalidad.setHorizontalAlignment(SwingConstants.RIGHT);
		txtLocalidad.setColumns(10);
		txtLocalidad.setBounds(99, 73, 198, 20);
		this.panel.add(txtLocalidad);
		
		txtNombre = new JTextField();
		txtNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNombre.setBounds(99, 33, 198, 20);
		this.panel.add(txtNombre);
		txtNombre.setColumns(10);
		 
		txtTelefono = new JTextField();
		txtTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(99, 108, 198, 20);
		this.panel.add(txtTelefono);
		
		txtDni = new JTextField();
		txtDni.setHorizontalAlignment(SwingConstants.RIGHT);
		txtDni.setColumns(10);
		txtDni.setBounds(99, 143, 198, 20);
		this.panel.add(txtDni);
		
		txtProvincia = new JTextField();
		txtProvincia.setHorizontalAlignment(SwingConstants.RIGHT);
		txtProvincia.setColumns(10);
		txtProvincia.setBounds(396, 73, 270, 20);
		this.panel.add(txtProvincia);
		
		txtApellidos = new JTextField();
		txtApellidos.setHorizontalAlignment(SwingConstants.RIGHT);
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(396, 33, 270, 20);
		this.panel.add(txtApellidos);
	}
	
	private void fillList() {
		
		this.clearList();
		
		for (Entry<String, Integer> i : clientes.entrySet()) {
			dlmClientes.addElement(i.getKey());
		}
		
	}
	
	private void clearList() {
		DefaultListModel<String> listModel = (DefaultListModel<String>) listaClientes.getModel();
		listModel.removeAllElements();
	}
	
	private void createFilter() {
		String[] filters 	= new String[7];
		String[] values 	= new String[7];
		String filter 	= "";
		String value 	= "";
		int filtersWrited = 0;
		
		values[0] = txtNombre.getText();
		values[1] = txtApellidos.getText();
		values[2] = txtLocalidad.getText();
		values[3] = txtProvincia.getText();
		values[4] = txtTelefono.getText();
		values[5] = txtEmail.getText();
		values[6] = txtDni.getText();
		
		filters[0] = "name";
		filters[1] = "surname";
		filters[2] = "locality";
		filters[3] = "province";
		filters[4] = "number";
		filters[5] = "email";
		filters[6] = "dni";
		
		for (int i = 0; i < filters.length; i++) {
			
			if (!values[i].equals("")) {
				filtersWrited++;
				filter 	= filters[i];
				value 	= values[i];
			}
			
		}
		
		if (filtersWrited > 1) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("MORE_THAN_ONE_FILTER"), null);
		}
		else if (filtersWrited == 1) {
			try {
				this.clientes = new ManejadorSQLite().verClientesFiltrados(filter, value);
				this.fillList();
			} catch (SQLException | ClassNotFoundException | IOException e) {
				Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_FILTERS"), null);
			}
		}
		else {
			try {
				this.clientes = new ManejadorSQLite().verTodosLosClientes();
				this.fillList();
			} catch (SQLException | ClassNotFoundException | IOException e) {
				Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_CLIENTS"), null);
			}
		}
	}
}
