package com.programandoapasitos.facturador.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.DocumentException;
import com.programandoapasitos.facturador.db.ManejadorSQLite;
import com.programandoapasitos.facturador.excepciones.ExcepcionEmailInvalido;
import com.programandoapasitos.facturador.excepciones.ExcepcionFacturaInvalida;
import com.programandoapasitos.facturador.excepciones.ExcepcionPagoInvalido;
import com.programandoapasitos.facturador.excepciones.ExcepcionTelefonoInvalido;
import com.programandoapasitos.facturador.participantes.Factura;
import com.programandoapasitos.facturador.participantes.Pago;
import com.programandoapasitos.facturador.utiles.ManejadorCalculos;
import com.programandoapasitos.facturador.utiles.ManejadorPDF;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;
import com.programandoapasitos.facturador.utiles.PdfPrinter;

@SuppressWarnings("serial")
public class DialogoEditarFactura extends JDialog{

	// Propiedades
	private ManejadorSQLite manejadorSQL;
	private Factura factura;
	
	private DefaultTableModel modeloPagos;
	
	private JButton btnCancelar;
	private JButton btnBorrar;
	private JButton btnImprimir;
	private JButton btnPdf;
	private JButton btnVerCliente;
	
	private JLabel lblFactura;
	private JLabel lblCliente;
	private JLabel lblFecha;
	private JLabel lblPorcentaje;
	private JLabel lblTotal;
	private JLabel lblIVA;
	
	private JPanel paneContenedor;
	private JPanel paneBoton;
	
	private JScrollPane scrollPagos;
	
	private JTable tblPagos;
	
	private JTextField txtFactura;
	private JTextField txtCliente;
	private JTextField txtFecha;
	private JTextField txtIVA;
	
	private String facturaId;
	
	// Constructor
	public DialogoEditarFactura(String facturaId) {
		
		this.manejadorSQL = new ManejadorSQLite();
		this.factura = new Factura();
		this.facturaId = facturaId;
		this.inicializar();
	}
	
	// Métodos
	
	/**
	 * Inicializa el diálogo
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private void inicializar() {
		setBounds(100, 100, ManejadorProperties.verGui("SUBFRAMES_WIDTH"), ManejadorProperties.verGui("SUBFRAMES_HEIGHT"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(ManejadorProperties.verRuta("ICONO")).getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(ManejadorProperties.verLiteral("BILL_EDIT"));
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		this.inicializarJPanels();
		getContentPane().add(this.paneContenedor, BorderLayout.CENTER);
		setVisible(true);
	}
	
	/**
	 * Inicializa los JPanels
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private void inicializarJPanels() {
		this.paneContenedor = new JPanel();
		this.paneContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.paneContenedor.setLayout(null);
		{
			// Panel botonera
			this.paneBoton = new JPanel();
			this.paneBoton.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(this.paneBoton, BorderLayout.SOUTH);
			{
				this.btnBorrar = new JButton(ManejadorProperties.verLiteral("BILL_DELETE"));
				this.btnBorrar.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							manejadorSQL.borrarFacturaPorId(factura.getNumeroFactura());
							Mensajes.verMensajeInformacion(ManejadorProperties.verLiteral("BILL_DELETED"));
							dispose();
						} 
						catch (SQLException | ClassNotFoundException | IOException err) {
							Mensajes.verMensajeError(ManejadorProperties.verLiteral("BILL_FAIL_DELETE"), null);
						}
					}
				});
				this.btnBorrar.setActionCommand("Delete");
				this.paneBoton.add(this.btnBorrar);
			}
			{
				this.btnImprimir = new JButton(ManejadorProperties.verLiteral("NEW_BILL_TO_PRINT"));
				this.btnImprimir.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						FileInputStream fis;
						try {
							new ManejadorPDF(factura.getNumeroFactura() + ".pdf").crearFactura(factura, 1);
							fis = new FileInputStream(ManejadorProperties.verRuta("PDF_TMP") + factura.getNumeroFactura() + ".pdf");
							PdfPrinter printer = new PdfPrinter(fis, factura.getNumeroFactura());
							printer.print();
							Mensajes.verMensajeInformacion(ManejadorProperties.verLiteral("PRINT_PDF"));
						} 
						catch (IOException | DocumentException | PrinterException e1) {
							Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_PRINT_PDF"), null);
						}
					}
				});
				this.btnImprimir.setActionCommand("Print");
				this.paneBoton.add(this.btnImprimir);
			}
			{
				this.btnPdf = new JButton(ManejadorProperties.verLiteral("NEW_BILL_TO_PDF"));
				this.btnPdf.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							new ManejadorPDF(factura.getNumeroFactura() + ".pdf").crearFactura(factura, 0);
							Mensajes.verMensajeInformacion(ManejadorProperties.verLiteral("CREATE_PDF"));
						} 
						catch (IOException | DocumentException e1) {
							Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_CREATE_PDF"), null);
						}
						
					}
				});
				this.btnPdf.setActionCommand("PDF");
				this.paneBoton.add(this.btnPdf);
			}
			{
				this.btnCancelar = new JButton(ManejadorProperties.verLiteral("CLOSE"));
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
		
		// Inicializar el resto de componentes
		this.inicializarBotones();
		this.inicializarLabels();
		this.inicializarTablas();
		this.inicializarTextFields();
		this.cargarDatos();
	}
	
	/**
	 * Inicializa los JButtons
	 */
	private void inicializarBotones() {
		this.btnVerCliente = new JButton(ManejadorProperties.verLiteral("NEW_BILL_VIEW_CLIENT"));
		this.btnVerCliente.setBounds(495, 44, 110, 23);
		this.btnVerCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new DialogoEditarCliente(factura.getCliente().getCodigoCliente());
				cargarDatos();
			}
		});
		this.paneContenedor.add(this.btnVerCliente);
	}
	
	/**
	 * Inicializa los JLabels
	 */
	private void inicializarLabels() {
		this.lblFactura = new JLabel(ManejadorProperties.verLiteral("NEW_BILL_NUMBER"));
		this.lblFactura.setBounds(10, 14, 73, 14);
		this.paneContenedor.add(this.lblFactura);
		
		this.lblFecha = new JLabel(ManejadorProperties.verLiteral("NEW_BILL_DATE"));
		this.lblFecha.setBounds(495, 17, 46, 14);
		this.paneContenedor.add(this.lblFecha);
		
		this.lblCliente = new JLabel(ManejadorProperties.verLiteral("NEW_BILL_CLIENT"));
		this.lblCliente .setBounds(10, 48, 46, 14);
		this.paneContenedor.add(this.lblCliente );
		
		this.lblIVA = new JLabel(ManejadorProperties.verLiteral("NEW_BILL_VAT"));
		this.lblIVA.setBounds(10, 379, 46, 14);
		this.paneContenedor.add(this.lblIVA);
		
		this.lblPorcentaje = new JLabel(ManejadorProperties.verLiteral("PERCENT"));
		this.lblPorcentaje.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblPorcentaje.setBounds(128, 379, 12, 14);
		this.paneContenedor.add(this.lblPorcentaje);
		
		this.lblTotal = new JLabel(ManejadorProperties.verLiteral("NEW_BILL_TOTAL"));
		this.lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblTotal.setBounds(441, 379, 220, 14);
		this.paneContenedor.add(this.lblTotal);
	}
	
	/**
	 * Inicializa todos los elementos que se usan para las tablas
	 */
	private void inicializarTablas() {
		this.scrollPagos = new JScrollPane();
		this.scrollPagos.setBounds(10, 103, 651, 248);
		this.paneContenedor.add(this.scrollPagos);
		
		this.modeloPagos = new DefaultTableModel();
		this.modeloPagos.addColumn(ManejadorProperties.verLiteral("NEW_BILL_CONCEPT"));
		this.modeloPagos.addColumn(ManejadorProperties.verLiteral("NEW_BILL_AMOUNT"));
		
		this.tblPagos = new JTable(this.modeloPagos) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.tblPagos.getColumnModel().getColumn(0).setPreferredWidth(410);
		this.scrollPagos.setViewportView(this.tblPagos);
	}
	
	/**
	 * Inicializa los JTextFields
	 */
	private void inicializarTextFields() {
		this.txtFactura = new JTextField();
		this.txtFactura.setHorizontalAlignment(SwingConstants.RIGHT);
		this.txtFactura.setColumns(10);
		this.txtFactura.setBounds(93, 11, 307, 20);
		this.paneContenedor.add(this.txtFactura);
		
		this.txtCliente = new JTextField();
		this.txtCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		this.txtCliente.setColumns(10);
		this.txtCliente.setBounds(93, 45, 307, 20);
		this.paneContenedor.add(this.txtCliente);
		
		this.txtFecha = new JTextField();
		this.txtFecha.setColumns(10);
		this.txtFecha.setBounds(551, 11, 110, 20);
		this.paneContenedor.add(this.txtFecha);
		
		this.txtIVA = new JTextField();
		this.txtIVA.setColumns(10);
		this.txtIVA.setBounds(50, 376, 73, 20);
		this.paneContenedor.add(this.txtIVA);
	}
	
	/**
	 * Carga los datos desde la DB a los campos de la interfaz gráfica
	 */
	private void cargarDatos() {
		this.limpiarCampos();
		try {
			this.factura = this.manejadorSQL.devolverFacturaDesdeId(this.facturaId);
			this.txtFactura.setText(this.factura.getNumeroFactura());
			this.txtFecha.setText(this.factura.getFecha());
			this.txtIVA.setText(String.valueOf(this.factura.getIva()));
			
			String cliente = this.factura.getCliente().getNombre() + " " + this.factura.getCliente().getApellidos();
			if (this.factura.getCliente().getDireccion() != null) {
				cliente += " - " + this.factura.getCliente().getDireccion().getLocalidad();
			}
			this.txtCliente.setText(cliente);
			
			double total = 0;
			for (Pago pago : this.factura.getPagos()) {
				this.modeloPagos.addRow(new Object[] {pago.getConcepto(), pago.getCantidad()});
				total += pago.getCantidad();
			}
			this.lblTotal.setText(ManejadorProperties.verLiteral("NEW_BILL_TOTAL") + String.format("%.2f", this.calcularIvaGui(total)) + " " + ManejadorProperties.verLiteral("CURRENCY_SYMBOL"));
			
		} 
		catch (SQLException eSQL) {
			Mensajes.verMensajeError(ManejadorProperties.verLiteral("FAIL_LOAD_ONE_BILL"), this);
		}
		catch (ClassNotFoundException | ExcepcionFacturaInvalida | IOException
				| ExcepcionTelefonoInvalido | ExcepcionEmailInvalido | ExcepcionPagoInvalido err) {
			Mensajes.verMensajeError(err.getMessage(), this);
		}
		catch (Exception e) {
			Mensajes.verMensajeError(e.getMessage(), this);
		}
		
	}
	
	/**
	 * Limpia los campos de la GUI
	 */
	private void limpiarCampos() {
		this.txtFactura.setText("");
		this.txtCliente.setText("");
		this.txtFecha.setText("");
		DefaultTableModel dtm = (DefaultTableModel) this.tblPagos.getModel();
		dtm.setRowCount(0);
	}
	
	/**
	 * Calcula el importe total + iva
	 * @param cantidad Importe total
	 * @return Importe total + iva
	 * @throws Exception si IVA está vacío
	 */
	private double calcularIvaGui(double cantidad) throws Exception {
		if (!this.txtIVA.getText().equals("")) {
			if (cantidad > 0.0) {
				try {
					Double iva = Double.parseDouble(this.txtIVA.getText());
					return ManejadorCalculos.calcularIVA(cantidad, iva) + cantidad;
				}
				catch(NumberFormatException e) {
					Mensajes.verMensajeError(ManejadorProperties.verLiteral("IS_NOT_DOUBLE"), this);
				}
			}
		}
		else {
			throw new Exception(ManejadorProperties.verLiteral("VAT_EXCEPTION"));
		}
		
		return cantidad;
	}
}


































