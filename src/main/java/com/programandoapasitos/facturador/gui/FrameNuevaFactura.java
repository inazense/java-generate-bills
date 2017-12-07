package com.programandoapasitos.facturador.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.itextpdf.text.DocumentException;
import com.programandoapasitos.facturador.db.ManejadorSQLite;
import com.programandoapasitos.facturador.excepciones.ExcepcionEmailInvalido;
import com.programandoapasitos.facturador.excepciones.ExcepcionFacturaInvalida;
import com.programandoapasitos.facturador.excepciones.ExcepcionPagoInvalido;
import com.programandoapasitos.facturador.excepciones.ExcepcionTelefonoInvalido;
import com.programandoapasitos.facturador.gui.superclases.FramePadre;
import com.programandoapasitos.facturador.participantes.Factura;
import com.programandoapasitos.facturador.participantes.Pago;
import com.programandoapasitos.facturador.utiles.ManejadorCalculos;
import com.programandoapasitos.facturador.utiles.ManejadorPDF;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;
import com.programandoapasitos.facturador.utiles.PdfPrinter;

@SuppressWarnings("serial")
public class FrameNuevaFactura extends FramePadre {

	// Propiedades
	private HashMap<String, Integer> clientes;
	
	private ManejadorSQLite manejadorSQLite;
	
	private DefaultListModel<String> modeloClientes;
	
	private DefaultTableModel modeloPagos;
	
	private MaskFormatter mask;
	
	private JButton btnAgregarPago;
	private JButton btnAgregarIVA;
	private JButton btnBorrarPago;
	private JButton btnNuevoCliente;
	private JButton btnRefrescarClientes;
	private JButton btnVerCliente;
	private JButton btnGuardar;
	private JButton btnPDF;
	private JButton btnImprimir;
	
	private JFormattedTextField txtFecha;
	
	private JLabel lblCantidad;
	private JLabel lblCliente;
	private JLabel lblConcepto;
	private JLabel lblMoneda;
	private JLabel lblFecha;
	private JLabel lblNumeroFactura;
	private JLabel lblPorcentaje;
	private JLabel lblTotal;
	private JLabel lblIVA;
	private JLabel lblSinIVA;
	
	private JList<String> listClientes;
	
	private JSeparator separator;
	
	private JScrollPane scrollClientes;
	private JScrollPane scrollConceptos;
	private JScrollPane scrollPagos;
	
	private JTable tblPagos;
	
	private JTextArea txtConcepto;
	
	private JTextField txtCantidad;
	private JTextField txtNumeroFactura;
	private JTextField txtIVA;
	
	// Constructor
	public FrameNuevaFactura(int ancho, int alto, String titulo) {
		super(ancho, alto, titulo);
		this.manejadorSQLite = new ManejadorSQLite();
		this.inicializar();
	}
	
	// Métodos
	private void inicializar() {
		setVisible(true);
		this.inicializarBotones();
		this.inicializarLabels();
		this.inicializarLists();
		this.inicializarSeparators();
		this.inicializarTables();
		this.inicializarTextFields();
	}
	
	private void inicializarBotones() {
		
		// Agregar pago
		this.btnAgregarPago = new JButton(ManejadorProperties.verLiteral("NEW_BILL_ADD_PAYMENT"));
		this.btnAgregarPago.setBounds(159, 207, 117, 23);
		this.btnAgregarPago.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					Pago p = new Pago(txtConcepto.getText(), Double.parseDouble(txtCantidad.getText()));
					modeloPagos.addRow(new Object[]{p.getConcepto(), p.getCantidad()});
					txtConcepto.setText("");
					txtCantidad.setText("");
					lblTotal.setText(ManejadorProperties.verLiteral("NEW_BILL_TOTAL") + String.format("%.2f", calcularImporte()) + " " + ManejadorProperties.verLiteral("CURRENCY_SYMBOL"));
					lblSinIVA.setText(ManejadorProperties.verLiteral("NEW_BILL_WITHOUT_VAT"));
				} catch (NumberFormatException e1) {
					Mensajes.verMensajeError(ManejadorProperties.verLiteral("IS_NOT_DOUBLE"), null);
				} catch (ExcepcionPagoInvalido e1) {
					Mensajes.verMensajeError(e1.getMessage(), null);
				}
			}
		});
		this.panel.add(this.btnAgregarPago);
		
		// Guardar factura
		this.btnGuardar = new JButton(ManejadorProperties.verLiteral("SAVE_DATA"));
		this.btnGuardar.setBounds(585, 428, 89, 23);
		this.btnGuardar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (guardarFactura()) {
					Mensajes.verMensajeInformacion(ManejadorProperties.verLiteral("NEW_BILL_SAVED"));
				}
			}
		});
		this.panel.add(this.btnGuardar);
		
		// Borrar fila
		this.btnBorrarPago = new JButton(ManejadorProperties.verLiteral("REMOVE_ITEM"));
		this.btnBorrarPago.setBounds(10, 382, 153, 23);
		this.btnBorrarPago.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (tblPagos.getSelectedRow() == -1) {
					Mensajes.verMensajeInformacion(ManejadorProperties.verLiteral("NEW_BILL_NO_ROW_SELECTED"));
				}
				else {
					modeloPagos.removeRow(tblPagos.getSelectedRow());
					lblTotal.setText(ManejadorProperties.verLiteral("NEW_BILL_TOTAL") + String.format("%.2f", calcularImporte()) + " " + ManejadorProperties.verLiteral("CURRENCY_SYMBOL"));
					if (modeloPagos.getRowCount() > 0) {
						lblSinIVA.setText(ManejadorProperties.verLiteral("NEW_BILL_WITHOUT_VAT"));
					}
				}
				
				
			}
		});
		this.panel.add(this.btnBorrarPago);
		
		// Agregar IVA
		this.btnAgregarIVA = new JButton(ManejadorProperties.verLiteral("NEW_BILL_ADD_VAT"));
		this.btnAgregarIVA.setBounds(563, 312, 111, 23);
		this.btnAgregarIVA.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					lblTotal.setText(ManejadorProperties.verLiteral("NEW_BILL_TOTAL") + String.format("%.2f", calcularIVA(calcularImporte())) + " " + ManejadorProperties.verLiteral("CURRENCY_SYMBOL"));
					lblSinIVA.setText("");
				} catch (Exception e1) {
					Mensajes.verMensajeError(e1.getMessage(), null);
				}
			}
		});
		this.panel.add(this.btnAgregarIVA);
		
		// PDF
		this.btnPDF = new JButton(ManejadorProperties.verLiteral("NEW_BILL_TO_PDF"));
		this.btnPDF.setBounds(486, 428, 89, 23);
		this.btnPDF.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				trabajarConPDF(1);
			}
		});
		this.panel.add(this.btnPDF);
		
		// Imprimir
		this.btnImprimir = new JButton(ManejadorProperties.verLiteral("NEW_BILL_TO_PRINT"));
		this.btnImprimir.setBounds(387, 428, 89, 23);
		this.btnImprimir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				trabajarConPDF(2);
			}
		});
		this.panel.add(this.btnImprimir);
		
		// Nuevo cliente
		this.btnNuevoCliente = new JButton(ManejadorProperties.verLiteral("NEW"));
		this.btnNuevoCliente.setBounds(564, 70, 73, 23);
		this.btnNuevoCliente.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new FrameNuevoCliente(ManejadorProperties.verGui("SUBFRAMES_WIDTH"), ManejadorProperties.verGui("SUBFRAMES_HEIGHT"), ManejadorProperties.verLiteral("MENU_ITEM_NEW_CLIENT"));
			}
		});
		this.panel.add(this.btnNuevoCliente);
		
		// Ver / Editar cliente
		this.btnVerCliente = new JButton(ManejadorProperties.verLiteral("NEW_BILL_VIEW_CLIENT"));
		this.btnVerCliente.setBounds(564, 104, 110, 23);
		this.btnVerCliente.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (listClientes.getSelectedIndex() != -1) {
					new DialogoEditarCliente(clientes.get(listClientes.getSelectedValue()));
					try {
						clientes = manejadorSQLite.verTodosLosClientes();
						rellenarLista();
					} catch (ClassNotFoundException | SQLException | IOException e1) {
						Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_CLIENTS"), null);
					}
				}
				else {
					Mensajes.verMensajeError(ManejadorProperties.verLiteral("NEW_BILL_FAIL_SELECT_CLIENT"), null);
				}
				
			}
		});
		this.panel.add(this.btnVerCliente);
		
		// Refrescar clientes
		this.btnRefrescarClientes = new JButton();
		this.btnRefrescarClientes.setIcon(new ImageIcon(ManejadorProperties.verRuta("RELOAD")));
		this.btnRefrescarClientes.setBounds(641, 70, 33, 23);
		this.btnRefrescarClientes.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					clientes = manejadorSQLite.verTodosLosClientes();
					rellenarLista();
				} catch (ClassNotFoundException | SQLException | IOException e1) {
					Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_CLIENTS"), null);
				}
			}
		});
		this.panel.add(this.btnRefrescarClientes);
	}
	
	private void inicializarLabels() {
		this.lblConcepto = new JLabel(ManejadorProperties.verLiteral("NEW_BILL_CONCEPT") + ":");
		this.lblConcepto.setBounds(10, 45, 90, 14);
		this.panel.add(this.lblConcepto);
		
		this.lblCantidad = new JLabel(ManejadorProperties.verLiteral("NEW_BILL_AMOUNT") + ":");
		this.lblCantidad.setBounds(10, 211, 73, 14);
		this.panel.add(this.lblCantidad);
		
		this.lblMoneda = new JLabel(ManejadorProperties.verLiteral("CURRENCY_SYMBOL"));
		this.lblMoneda.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblMoneda.setBounds(135, 213, 12, 10);
		this.panel.add(this.lblMoneda);
		
		this.lblIVA = new JLabel(ManejadorProperties.verLiteral("NEW_BILL_VAT"));
		this.lblIVA.setBounds(563, 256, 46, 14);
		this.panel.add(this.lblIVA);
		
		this.lblPorcentaje = new JLabel(ManejadorProperties.verLiteral("PERCENT"));
		this.lblPorcentaje.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblPorcentaje.setBounds(641, 284, 12, 14);
		this.panel.add(this.lblPorcentaje);
		
		this.lblTotal = new JLabel(ManejadorProperties.verLiteral("NEW_BILL_TOTAL"));
		this.lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblTotal.setBounds(333, 386, 220, 14);
		this.panel.add(this.lblTotal);
		
		this.lblCliente = new JLabel(ManejadorProperties.verLiteral("NEW_BILL_CLIENT"));
		this.lblCliente.setBounds(350, 45, 90, 14);
		this.panel.add(this.lblCliente);
		
		this.lblNumeroFactura = new JLabel(ManejadorProperties.verLiteral("NEW_BILL_NUMBER"));
		this.lblNumeroFactura.setBounds(10, 11, 73, 14);
		this.panel.add(this.lblNumeroFactura);
		
		this.lblSinIVA = new JLabel();
		this.lblSinIVA.setForeground(Color.RED);
		this.lblSinIVA.setBounds(288, 386, 98, 14);
		this.panel.add(this.lblSinIVA);
		
		this.lblFecha = new JLabel(ManejadorProperties.verLiteral("NEW_BILL_DATE"));
		this.lblFecha.setBounds(495, 11, 46, 14);
		this.panel.add(this.lblFecha);
	}
	
	private void inicializarLists() {
		this.scrollClientes = new JScrollPane();
		this.scrollClientes.setBounds(349, 70, 204, 127);
		this.panel.add(this.scrollClientes);
		
		modeloClientes = new DefaultListModel<String>();

		this.listClientes = new JList<String>();
		this.listClientes.setModel(modeloClientes);
		
		try {
			this.clientes = manejadorSQLite.verTodosLosClientes();
			this.rellenarLista();
		} catch (SQLException | ClassNotFoundException | IOException e) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_CLIENTS"), null);
		}
		
		this.scrollClientes.setViewportView(this.listClientes);
	}
	
	private void inicializarSeparators() {
		this.separator = new JSeparator();
		this.separator.setOrientation(SwingConstants.VERTICAL);
		this.separator.setBounds(333, 45, 18, 150);
		this.panel.add(this.separator);
	}
	
	private void inicializarTables() {
		this.scrollPagos = new JScrollPane();
		this.scrollPagos .setBounds(10, 252, 543, 115);
		this.panel.add(this.scrollPagos);
		
		this.modeloPagos = new DefaultTableModel();
		this.modeloPagos.addColumn(ManejadorProperties.verLiteral("NEW_BILL_CONCEPT"));
		this.modeloPagos.addColumn(ManejadorProperties.verLiteral("NEW_BILL_AMOUNT"));
		
		this.tblPagos = new JTable(this.modeloPagos) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.tblPagos.getColumnModel().getColumn(0).setPreferredWidth(266);
		this.tblPagos.getColumnModel().getColumn(1).setPreferredWidth(34);
		this.tblPagos.getColumnModel().getColumn(0).setResizable(false);
		this.tblPagos.getColumnModel().getColumn(1).setResizable(false);
		
		this.scrollPagos.setViewportView(this.tblPagos);
	}
	
	private void inicializarTextFields() {
		this.scrollConceptos = new JScrollPane();
		this.scrollConceptos.setBounds(10, 70, 266, 127);
		this.panel.add(this.scrollConceptos);
		
		this.txtConcepto = new JTextArea();
		this.scrollConceptos.setViewportView(this.txtConcepto);
		
		this.txtCantidad = new JTextField();
		this.txtCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		this.txtCantidad.setBounds(63, 208, 73, 20);
		this.panel.add(this.txtCantidad);
		
		this.txtIVA = new JTextField();
		this.txtIVA.setBounds(563, 281, 73, 20);
		this.txtIVA.setColumns(10);
		this.panel.add(this.txtIVA);
		
		this.txtNumeroFactura = new JTextField();
		this.txtNumeroFactura.setHorizontalAlignment(SwingConstants.RIGHT);
		this.txtNumeroFactura.setBounds(93, 8, 307, 20);
		this.txtNumeroFactura.setColumns(10);
		this.panel.add(this.txtNumeroFactura);
		
		try {
			mask = new MaskFormatter("##/##/####");
			this.txtFecha = new JFormattedTextField(mask);
		} catch (ParseException e) {
			this.txtFecha = new JFormattedTextField();
		}
		this.txtFecha = new JFormattedTextField(mask);
		this.txtFecha.setBounds(551, 8, 123, 20);
		this.txtFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		this.panel.add(this.txtFecha);
	}
	
	/**
	 * Rellenar la lista con los clientes de la base de datos
	 */
	private void rellenarLista() {
		this.limpiarLista();
		
		for (Entry<String, Integer> entrada : this.clientes.entrySet()) {
			this.modeloClientes.addElement(entrada.getKey());
		}
	}
	
	/**
	 * Borra todos los elementos de una lista
	 */
	private void limpiarLista() {
		DefaultListModel<String> modeloLista = (DefaultListModel<String>)this.listClientes.getModel();
		modeloLista.removeAllElements();
	}
	
	private void trabajarConPDF(int modo) {
		if (this.listClientes.getSelectedIndex() == -1) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("MANDATORY_SELECT_A_CLIENT"), null);
		}
		else if (this.txtFecha.getText().equals("")) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("MANDATORY_SELECT_A_DATE"), null);
		}
		else if (this.tblPagos.getRowCount() < 1) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("MANDATORY_PAYMENTS"), null);
		}
		else {
			try {
				Factura factura = new Factura();
				factura.setNumeroFactura(this.txtNumeroFactura.getText());
				factura.setIva(Double.parseDouble(this.txtIVA.getText()));
				factura.setFecha(this.txtFecha.getText());
				Vector<Pago> pagos = new Vector<Pago>();
				for (int i = 0; i < this.tblPagos.getRowCount(); i++) {
					Pago p = new Pago();
					p.setConcepto((String)this.tblPagos.getValueAt(i, 0));
					p.setCantidad((Double)this.tblPagos.getValueAt(i, 1));
					pagos.add(p);
				}
				factura.setPagos(pagos);
				factura.setCliente(this.manejadorSQLite.devolverClienteDesdeId(this.clientes.get(this.listClientes.getSelectedValue())));
				
				if (modo == 1 ){
					new ManejadorPDF(factura.getNumeroFactura() + ".pdf").crearFactura(factura, 0);
					Mensajes.verMensajeInformacion(ManejadorProperties.verLiteral("CREATE_PDF"));
				}
				else if (modo == 2) {
					FileInputStream fis;
					try {
						new ManejadorPDF(factura.getNumeroFactura() + ".pdf").crearFactura(factura, 1);
						fis = new FileInputStream("temp/" + factura.getNumeroFactura() + ".pdf");
						PdfPrinter printer = new PdfPrinter(fis, factura.getNumeroFactura());
						printer.print();
						Mensajes.verMensajeInformacion(ManejadorProperties.verLiteral("PRINT_PDF"));
					} catch (IOException | PrinterException | DocumentException e1) {
						Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_PRINT_PDF"), null);
					}
				}
			}
			catch(ExcepcionFacturaInvalida | ExcepcionPagoInvalido | ExcepcionTelefonoInvalido | ExcepcionEmailInvalido e) {
				Mensajes.verMensajeError(e.getMessage(), null);
			}
			catch (SQLException e) {
				Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_CLIENTS"), null);
			} 
			catch (IOException | DocumentException | ClassNotFoundException e) {
				Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_CREATE_PDF"), null);
			}
		}
	}
	
	private boolean guardarFactura() {
		if (this.listClientes.getSelectedIndex() == -1) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("MANDATORY_SELECT_A_CLIENT"), null);
			return false;
		}
		else if (this.txtFecha.getText().equals("")) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("MANDATORY_SELECT_A_DATE"), null);
			return false;
		}
		else if (this.tblPagos.getRowCount() < 1) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("MANDATORY_PAYMENTS"), null);
			return false;
		}
		else {
			try {
				
				// Create the object
				Factura factura = new Factura();
				factura.setNumeroFactura(this.txtNumeroFactura.getText());
				factura.setIva(Double.parseDouble(this.txtIVA.getText()));
				factura.setFecha(this.txtFecha.getText());
				Vector<Pago> pagos = new Vector<Pago>();
				for (int i = 0; i < this.tblPagos.getRowCount(); i++) {
					Pago p = new Pago();
					p.setConcepto((String)this.tblPagos.getValueAt(i, 0));
					p.setCantidad((Double)this.tblPagos.getValueAt(i, 1));
					pagos.add(p);
				}
				factura.setPagos(pagos);
				factura.setCliente(this.manejadorSQLite.devolverClienteDesdeId(this.clientes.get(this.listClientes.getSelectedValue())));
				
				// Save Bill into SQLite database
				this.manejadorSQLite.insertarFactura(factura);
				this.manejadorSQLite.insertarPagos(factura.getNumeroFactura(), factura.getPagos());
				
				return true;
			} 
			catch (ExcepcionFacturaInvalida | ExcepcionPagoInvalido | ExcepcionTelefonoInvalido | ExcepcionEmailInvalido e) {
				Mensajes.verMensajeError(e.getMessage(), null);
				return false;
			}
			catch (ClassNotFoundException | IOException | SQLException e) {
				Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_CLIENTS"), null);
				return false;
			}
		}
	}
	
	/**
	 * Calcula el importe total de los pagos de las tablas
	 * @return Double. Pago total de la factura
	 */
	private double calcularImporte() {
		
		double resultado = 0;
		
		for (int i = 0; i < this.tblPagos.getRowCount(); i++) {
			resultado += (Double)this.tblPagos.getValueAt(i, 1);
		}
		
		return resultado;
	}
	
	/**
	 * Calcula el importe total + IVA
	 * @param importe Importe sin IVA
	 * @return Importe total + IVA
	 * @throws Exception IVA no incluido 
	 */
	private double calcularIVA(double importe) throws Exception {
		if (!this.txtIVA.getText().equals("")) {
			if (importe > 0.0) {
				try {
					return importe + ManejadorCalculos.calcularIVA(importe, Double.parseDouble(this.txtIVA.getText()));
				}
				catch(NumberFormatException e) {
					Mensajes.verMensajeError(ManejadorProperties.verLiteral("IS_NOT_DOUBLE"), null);
				}
			}
		}
		else {
			throw new Exception(ManejadorProperties.verLiteral("VAT_EXCEPTION"));
		}
		
		return importe;
	}
}