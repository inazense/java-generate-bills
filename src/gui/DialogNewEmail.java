package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import exceptions.InvalidEmailException;
import participants.Email;
import utils.GeneralConfigurations;
import utils.TransferData;
import utils.UserMessages;

@SuppressWarnings("serial")
public class DialogNewEmail extends JDialog {

	// Properties
	private JPanel contentPane;
	private JPanel buttonPane;
	private JButton btnOk;
	private JButton btnCancel;
	private JTextField txtEmail;
	private JLabel lblEmail;
	
	// Constructor
	public DialogNewEmail() {
		this.init();
	}
	
	// Methods
	
	/**
	 * Initialize the dialog
	 */
	private void init() {
		setBounds(100, 100, GeneralConfigurations.DIALOG_WIDTH, GeneralConfigurations.DIALOG_HEIGTH);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("resorces/icon.png").getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(UserMessages.DIALOG_ADD_PHONE_TITLE);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		this.initJPanels();
		getContentPane().add(contentPane, BorderLayout.CENTER);
		setVisible(true);
	}
	
	/**
	 * Initialize panels of dialog
	 */
	private void initJPanels() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		{
			// Button panel
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnOk = new JButton(UserMessages.DIALOG_OK_BUTTON);
				btnOk.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						okButtonAction();
					}
				});
				btnOk.setActionCommand("OK");
				buttonPane.add(btnOk);
			}
			{
				btnCancel = new JButton(UserMessages.DIALOG_CANCEL_BUTTON);
				btnCancel.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
		this.initTextFields();
		this.initLabels();
	}
	
	/**
	 * Initialize text fields
	 */
	private void initTextFields() {
		txtEmail = new JTextField();
		txtEmail.setBounds(89, 48, 199, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
	}
	
	/**
	 * Initialize labels
	 */
	private void initLabels() {
		lblEmail = new JLabel(UserMessages.DIALOG_EMAIL);
		lblEmail.setBounds(89, 23, 199, 14);
		contentPane.add(lblEmail);
	}
	
	/**
	 * Catch textfield value and stores them into TransferData
	 * Then, dispose the dialog
	 */
	private void okButtonAction() {
		try {
			Email em = new Email(txtEmail.getText());
			TransferData.EMAIL = em.getEmail();
			dispose();
		}
		catch(InvalidEmailException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
	}
}
