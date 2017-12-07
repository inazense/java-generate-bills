package com.programandoapasitos.facturador.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.programandoapasitos.facturador.db.ManejadorSQLite;
import com.programandoapasitos.facturador.gui.superclases.FramePadre;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;

@SuppressWarnings("serial")
public class FrameBuscarFacturas extends FramePadre {

	// Propiedades
	private HashMap<String, Integer> clientes;
	private Vector<String[]> facturas;
	
	private ManejadorSQLite manejadorSQLite;
	
	private DefaultListModel<String> modeloClientes;
	private DefaultTableModel modeloFacturas;
	
	private MaskFormatter mascara;
	
	private JFormattedTextField txtFecha;
	
	private JButton btnBuscar;
	
	private JLabel lblTitulo;
	private JLabel lblNumeroFactura;
	private JLabel lblFecha;
	private JLabel lblCliente;
	private JLabel lblListaFacturas;
	private JLabel lblAyuda;
	
	private JList<String> listaClientes;
	
	private JScrollPane scrollClientes;
	private JScrollPane scrollFacturas;
	
	private JSeparator separator;
	
	private JTable tblFacturas;
	
	private JTextField txtNumeroFactura;
	
	// Constructor
	public FrameBuscarFacturas(int ancho, int alto, String titulo) {
		super(ancho, alto, titulo);
		this.manejadorSQLite = new ManejadorSQLite();
		this.inicializar();
	}
	
	// Métodos
	private void inicializar() {
		setVisible(true);
		this.inicializarBotones();
		this.inicializarLabels();
		this.inicializarListas();
		this.inicializarSeparators();
		this.inicializarTablas();
		this.inicializarTextFields();
	}
	
	private void inicializarBotones() {
		this.btnBuscar = new JButton(ManejadorProperties.verLiteral("SEARCH"));
		this.btnBuscar.setBounds(585, 220, 89, 23);
		
		// Search
		this.btnBuscar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				crearFiltro();
				txtNumeroFactura.setText("");
				txtFecha.setText("");
				listaClientes.setSelectedIndex(-1);
				listaClientes.clearSelection();
			}
		});
		this.panel.add(this.btnBuscar);
	}
	
	private void inicializarLabels() {
		this.lblTitulo = new JLabel(ManejadorProperties.verLiteral("MENU_ITEM_SEARCH_BILL"));
		this.lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		this.lblTitulo.setBounds(292, 11, 100, 14); 	
		this.panel.add(this.lblTitulo);
		
		this.lblNumeroFactura = new JLabel(ManejadorProperties.verLiteral("BILL_NUMBER") + ":");
		this.lblNumeroFactura.setBounds(10, 54, 66, 14);
		this.panel.add(this.lblNumeroFactura);
		
		this.lblFecha = new JLabel(ManejadorProperties.verLiteral("BILL_DATE"));
		this.lblFecha.setBounds(348, 54, 103, 14);
		this.panel.add(this.lblFecha);
		
		this.lblCliente = new JLabel(ManejadorProperties.verLiteral("MENU_CLIENTES"));
		this.lblCliente.setBounds(10, 91, 46, 14);
		this.panel.add(this.lblCliente);
		
		this.lblListaFacturas = new JLabel(ManejadorProperties.verLiteral("BILL_LIST"));
		this.lblListaFacturas.setBounds(10, 272, 132, 14);
		this.panel.add(this.lblListaFacturas);
		
		this.lblAyuda = new JLabel(ManejadorProperties.verLiteral("BILL_LIST_INSTRUCTIONS"));
		this.lblAyuda.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.lblAyuda.setBounds(131, 272, 306, 14);
		this.panel.add(this.lblAyuda);
	}
	
	private void inicializarListas() {
		this.scrollClientes = new JScrollPane();
		this.scrollClientes.setBounds(86, 89, 507, 110);
		this.panel.add(this.scrollClientes);
		
		this.modeloClientes = new DefaultListModel<String>();
		
		this.listaClientes = new JList<String>();
		this.listaClientes.setModel(this.modeloClientes);
		this.listaClientes.setSelectedIndex(-1);
		
		// Rellenar lista clientes
		try {
			this.clientes = this.manejadorSQLite.verTodosLosClientes();
			this.rellenarLista();
		} catch (SQLException | ClassNotFoundException | IOException e) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_CLIENTS"), null);
		}
		
		this.scrollClientes.setViewportView(this.listaClientes);
	}
	
	private void inicializarSeparators() {
		this.separator = new JSeparator();
		this.separator.setBounds(202, 259, 280, 2);
		this.panel.add(this.separator);
	}
	
	private void inicializarTablas() {
		this.scrollFacturas = new JScrollPane();
		this.scrollFacturas.setBounds(10, 297, 664, 154);
		this.panel.add(this.scrollFacturas);
		
		this.modeloFacturas = new DefaultTableModel();
		this.modeloFacturas.addColumn(ManejadorProperties.verLiteral("BILL_NUMBER"));
		this.modeloFacturas.addColumn(ManejadorProperties.verLiteral("BILL_CLIENT"));
		this.modeloFacturas.addColumn(ManejadorProperties.verLiteral("BILL_DATE_TABLE"));
		
		this.tblFacturas = new JTable(this.modeloFacturas) {
			// Celdas no editables
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.tblFacturas.getColumnModel().getColumn(0).setPreferredWidth(150);
		this.tblFacturas.getColumnModel().getColumn(1).setPreferredWidth(280);
		this.tblFacturas.getColumnModel().getColumn(2).setPreferredWidth(60);
		
		// Rellenar tabla
		try {
			this.facturas = this.manejadorSQLite.verTodasLasFacturas();
			this.rellenarTabla();
		} 
		catch (SQLException | ClassNotFoundException | IOException e) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_BILLS"), null);
		}
		
		// Evento doble click
		this.tblFacturas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					String idFactura = (String) modeloFacturas.getValueAt(tblFacturas.getSelectedRow(), 0);
					new DialogoEditarFactura(idFactura);
					try {
						facturas = manejadorSQLite.verTodasLasFacturas();
						rellenarTabla();
					}
					catch(SQLException | ClassNotFoundException | IOException e) {
						JOptionPane.showMessageDialog(null, ManejadorProperties.verLiteral("FAIL_LOAD_BILLS"));
					}
				}
			}
		});
		
		this.scrollFacturas.setViewportView(this.tblFacturas);
	}
	
	private void inicializarTextFields() {
		this.txtNumeroFactura = new JTextField();
		this.txtNumeroFactura.setBounds(86, 51, 189, 20);
		this.panel.add(this.txtNumeroFactura);
		this.txtNumeroFactura.setColumns(10);
		
		try {
			this.mascara = new MaskFormatter("##/##/####");
			this.txtFecha = new JFormattedTextField(this.mascara);
		} catch (ParseException e) {
			this.txtFecha = new JFormattedTextField();
		}
		this.txtFecha = new JFormattedTextField(this.mascara);
		this.txtFecha.setBounds(461, 51, 132, 20);
		this.txtFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		this.panel.add(this.txtFecha);
		this.txtFecha.setColumns(10);
	}
	
	private void rellenarLista() {
		this.limpiarLista();
		
		for (Entry<String, Integer> i : this.clientes.entrySet()) {
			this.modeloClientes.addElement(i.getKey());
		}
	}
	
	private void limpiarLista() {
		DefaultListModel<String> modeloLista = (DefaultListModel<String>) this.listaClientes.getModel();
		modeloLista.removeAllElements();
	}
	
	private void rellenarTabla() {
		this.limpiarTabla();
		for (String[] i : this.facturas) {
			this.modeloFacturas.addRow(new Object[]{i[0], i[1], i[2]});
		}
	}
	
	private void limpiarTabla() {
		DefaultTableModel dtm = (DefaultTableModel) this.tblFacturas.getModel();
		dtm.setRowCount(0);
	}
	
	/**
	 * Captura los valores de los campos para realizar un filtro en la base de datos y
	 * devolver los resultados correspondientes.
	 * Luego, rellena la tabla con dicha información
	 */
	private void crearFiltro() {
		Integer cliente = this.listaClientes.getSelectedIndex();
		String fecha = this.txtFecha.getText();
		String factura = this.txtNumeroFactura.getText();
		String filtro = "";
		String valor = "";
		int i = 0;
		
		if (!String.valueOf(fecha.toCharArray()[0]).equals(" ")) {
			filtro = "date";
			valor = fecha;
			i++;
		}
		if (!factura.equals("")) {
			filtro = "id";
			valor = factura;
			i++;
		}
		if (cliente != -1) {
			filtro = "client";
			valor = String.valueOf(this.clientes.get(listaClientes.getSelectedValue()));
			i++;
		}
		
		if (i == 0) {
			try {
				this.facturas = null;
				this.facturas= this.manejadorSQLite.verTodasLasFacturas();
				this.rellenarTabla();
			}
			catch(SQLException | ClassNotFoundException | IOException e) {
				Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_BILLS"), null);
			}
		}
		else if (i == 1) {
			try {
				this.facturas = null;
				this.facturas = this.manejadorSQLite.verFacturasFiltradas(filtro, valor);
				this.rellenarTabla();
			}
			catch(SQLException | ClassNotFoundException | IOException e) {
				Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_BILLS_FILTERS"), null);
			}
		}
		else {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("MORE_THAN_ONE_FILTER"), null);
		}
	}
	
	
}
