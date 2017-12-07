package com.programandoapasitos.facturador.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.programandoapasitos.facturador.excepciones.ExcepcionEmailInvalido;
import com.programandoapasitos.facturador.participantes.Email;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;
import com.programandoapasitos.facturador.utiles.Transferencia;

@SuppressWarnings("serial")
public class DialogoNuevoEmail extends JDialog {

	// Propiedades
	private JPanel paneContenedor;
	private JPanel paneBoton;
	private JButton btnOk;
	private JButton btnCancelar;
	private JLabel lblEmail;
	private JTextField txtEmail;
	
	// Constructor
	public DialogoNuevoEmail() {
		this.inicializar();
	}
	
	// Métodos
	
	/**
	 * Inicializa el JDialog
	 */
	private void inicializar() {
		setBounds(100, 100, ManejadorProperties.verGui("DIALOG_WIDTH"), ManejadorProperties.verGui("DIALOG_HEIGTH"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(ManejadorProperties.verRuta("ICONO")).getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(ManejadorProperties.verLiteral("DIALOG_ADD_PHONE_TITLE"));
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		this.inicializarPaneles();
		getContentPane().add(this.paneContenedor, BorderLayout.CENTER);
		setVisible(true);
		
	}
	
	/**
	 * Inicializa los JPanels
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
				this.btnOk = new JButton(ManejadorProperties.verLiteral("DIALOG_OK_BUTTON"));
				this.btnOk.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						accionBtnOk();
					}
				});
				this.btnOk.setActionCommand("OK");
				this.paneBoton.add(this.btnOk);
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
		this.inicializarLabels();
		this.inicializarTextFields();
	}
	
	/**
	 * Inicializa los JTextFields
	 */
	private void inicializarTextFields() {
		
		this.txtEmail = new JTextField();
		this.txtEmail.setBounds(89, 48, 199, 20);
		this.txtEmail.setColumns(10);
		this.paneContenedor.add(this.txtEmail);
		
	}
	
	/**
	 * Inicializa los JLabels
	 */
	private void inicializarLabels() {
		this.lblEmail = new JLabel(ManejadorProperties.verLiteral("DIALOG_EMAIL"));
		this.lblEmail.setBounds(89, 23, 199, 14);
		this.paneContenedor.add(this.lblEmail);
	}
	
	/**
	 * Captura el valor del nuevo email y 
	 * lo almacena en la clase estática de Transferencia.
	 * Luego cierra el JDialog
	 */
	private void accionBtnOk() {
		
		try {
			Email email = new Email(this.txtEmail.getText());
			Transferencia.EMAIL = email.getEmail();
			dispose();
		} 
		catch (ExcepcionEmailInvalido e) {
			Mensajes.verMensajeError(e.getMessage(), null);
		}
	}
}
