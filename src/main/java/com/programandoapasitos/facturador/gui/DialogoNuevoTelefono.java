package com.programandoapasitos.facturador.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.programandoapasitos.facturador.excepciones.ExcepcionTelefonoInvalido;
import com.programandoapasitos.facturador.participantes.Prefijo;
import com.programandoapasitos.facturador.participantes.Telefono;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;
import com.programandoapasitos.facturador.utiles.Transferencia;

@SuppressWarnings("serial")
public class DialogoNuevoTelefono extends JDialog {

	// Propiedades
	private JPanel paneContenedor;
	private JPanel paneBoton;
	private JButton btnOk;
	private JButton btnCancelar;
	private JComboBox<String> comboPrefijo;
	private DefaultComboBoxModel<String> dcbmPrefijos;
	private JTextField txtNumeroTelefono;
	private JLabel lblTelefono;
	private JLabel lblPrefijo;
	
	// Constructor
	public DialogoNuevoTelefono() { this.inicializar(); }
	
	// Métodos
	public void inicializar() {
		setBounds(100, 100, ManejadorProperties.verGui("DIALOG_WIDTH"), ManejadorProperties.verGui("DIALOG_HEIGTH"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(ManejadorProperties.verRuta("ICONO")).getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(ManejadorProperties.verLiteral("DIALOG_ADD_PHONE_TITLE"));
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		this.inicializarJPanels();
		getContentPane().add(this.paneContenedor, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public void inicializarJPanels() {
		this.paneContenedor = new JPanel();
		this.paneContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.paneContenedor.setLayout(null);
		{
			// Button panel
			this.paneBoton = new JPanel();
			this.paneBoton.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(this.paneBoton, BorderLayout.SOUTH);
			{
				this.btnOk = new JButton(ManejadorProperties.verLiteral("DIALOG_OK_BUTTON"));
				this.btnOk.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						accionBotonOk();
					}
				});
				this.btnOk.setActionCommand("OK");
				this.paneBoton.add(btnOk);
			}
			{
				this.btnCancelar = new JButton(ManejadorProperties.verLiteral("DIALOG_CANCEL_BUTTON"));
				this.btnCancelar.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				this.btnCancelar.setActionCommand("Cancel");
				this.paneBoton.add(this.btnCancelar);
			}
		}
		
		this.inicializarComboBox();
		this.inicializarTextFields();
		this.inicializarLabels();
	}
	
	private void inicializarComboBox() {
		this.dcbmPrefijos = new DefaultComboBoxModel<String>();
		this.rellenarModeloCombobox();
		
		this.comboPrefijo = new JComboBox<String>();
		this.comboPrefijo.setModel(this.dcbmPrefijos);
		this.comboPrefijo.setSelectedIndex(3);
		this.comboPrefijo.setBounds(42, 48, 114, 20);
		this.paneContenedor.add(this.comboPrefijo);
	}
	
	private void rellenarModeloCombobox() {
		for (Entry<String, String> i : Prefijo.INTERNACIONALES.entrySet()) {
			this.dcbmPrefijos.addElement(i.getKey());
		}
	}
	
	private void inicializarLabels() {
		this.lblTelefono = new JLabel(ManejadorProperties.verLiteral("DIALOG_PHONE"));
		this.lblTelefono.setBounds(198, 23, 137, 14);
		this.paneContenedor.add(this.lblTelefono);
		
		this.lblPrefijo = new JLabel(ManejadorProperties.verLiteral("DIALOG_PREFIX"));
		this.lblPrefijo.setBounds(42, 23, 78, 14);
		this.paneContenedor.add(this.lblPrefijo);
	}
	
	private void inicializarTextFields() {
		this.txtNumeroTelefono = new JTextField();
		this.txtNumeroTelefono.setBounds(198, 48, 137, 20);
		this.paneContenedor.add(this.txtNumeroTelefono);
		this.txtNumeroTelefono.setColumns(10);
	}
	
	private void accionBotonOk() {
		try {
			Telefono tl = new Telefono(Prefijo.INTERNACIONALES.get(this.comboPrefijo.getSelectedItem()), this.txtNumeroTelefono.getText());
			Transferencia.TELEFONO = tl.getNumero();
			Transferencia.PREFIJO = tl.getPrefijo();
			dispose();
		}
		catch(ExcepcionTelefonoInvalido e) {
			Mensajes.verMensajeError(e.getMessage(), null);
		}
	}
}
