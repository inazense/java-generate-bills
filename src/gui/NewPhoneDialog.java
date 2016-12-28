package gui;

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

import utils.GeneralConfigurations;
import utils.Prefix;
import utils.TransferData;
import utils.UserMessages;

@SuppressWarnings("serial")
public class NewPhoneDialog extends JDialog {

	// Properties
	private JPanel contentPane;
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;
	private JComboBox<String> comboPrefix;
	private DefaultComboBoxModel<String> dcbmPrefixes;
	private JTextField txtPhoneNumber;
	private JLabel lblPhone;
	private JLabel lblPrefix;
	
	// Constructor
	public NewPhoneDialog() {
		this.init();
	}
	
	// Methods
	
	/**
	 * Init the dialog
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
	 * Init panels of dialog
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
				okButton = new JButton(UserMessages.DIALOG_OK_BUTTON);
				okButton.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						okButtonAction();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
			}
			{
				cancelButton = new JButton(UserMessages.DIALOG_CANCEL_BUTTON);
				cancelButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		this.initComboBox();
		this.initTextFields();
		this.initLabels();
	}
	
	/**
	 * Init combobox
	 */
	private void initComboBox() {
		dcbmPrefixes = new DefaultComboBoxModel<String>();
		this.fillComboBoxModel();
		
		comboPrefix = new JComboBox<String>();
		comboPrefix.setModel(dcbmPrefixes);
		comboPrefix.setSelectedIndex(3);
		comboPrefix.setBounds(42, 48, 114, 20);
		contentPane.add(comboPrefix);
	}
	
	/**
	 * Fill combobox with static countries telephone prefixes
	 */
	private void fillComboBoxModel() {
		for (Entry<String, String> i : Prefix.INTERNATIONAL_PREFIXS.entrySet()) {
			dcbmPrefixes.addElement(i.getKey());
		}
	}
	
	/**
	 * Init text fields
	 */
	private void initTextFields() {
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(198, 48, 137, 20);
		contentPane.add(txtPhoneNumber);
		txtPhoneNumber.setColumns(10);
	}
	
	/**
	 * Init labels
	 */
	private void initLabels() {
		lblPhone = new JLabel(UserMessages.DIALOG_PHONE);
		lblPhone.setBounds(198, 23, 137, 14);
		contentPane.add(lblPhone);
		
		lblPrefix = new JLabel(UserMessages.DIALOG_PREFIX);
		lblPrefix.setBounds(42, 23, 78, 14);
		contentPane.add(lblPrefix);
	}
	
	/**
	 * Catch combobox and textfield values and stores them into TransferData
	 * Then, dispose the dialog
	 */
	private void okButtonAction() {
		TransferData.PHONE = txtPhoneNumber.getText();
		TransferData.PREFIX = Prefix.INTERNATIONAL_PREFIXS.get(comboPrefix.getSelectedItem());
		dispose();
	}
}
