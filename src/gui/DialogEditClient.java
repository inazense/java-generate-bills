package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import database.SQLiteHelper;
import exceptions.InvalidEmailException;
import exceptions.InvalidTelephoneException;
import participants.Client;
import utils.GeneralConfigurations;
import utils.TransferData;
import utils.UserMessages;

@SuppressWarnings("serial")
public class DialogEditClient extends JDialog {

	// Properties
	private DefaultListModel<String> dlmEmails;
	private DefaultListModel<String> dlmPhones;
	
	private JButton btnAddEmail;
	private JButton btnAddPhone;
	private JButton btnRemoveEmail;
	private JButton btnRemovePhone;
	private JButton btnOk;
	private JButton btnCancel;
	
	private JLabel lblLocality;
	private JLabel lblName;
	private JLabel lblPostalCode;
	private JLabel lblProvince;
	private JLabel lblStreet;
	private JLabel lblSurnames;
	
	private JList<String> listEmails;
	private JList<String> listPhones;
	
	private JPanel contentPane;
	private JPanel buttonPane;
	
	private JSeparator separator1;
	private JSeparator separator2;
	
	private JScrollPane scrollEmail;
	private JScrollPane scrollPhone;
	
	private JTextField txtLocality;
	private JTextField txtName;
	private JTextField txtPostalCode;
	private JTextField txtProvince;
	private JTextField txtStreet;
	private JTextField txtSurnames;

	private int clientCode;
	
	// Constructor
	public DialogEditClient(int clientCode) {
		this.clientCode = clientCode;
		this.init();
	}
	
	// Methods
	
	/**
	 * Initialize the dialog
	 */
	private void init() {
		setBounds(100, 100, GeneralConfigurations.SUBFRAMES_WIDTH, GeneralConfigurations.SUBFRAMES_HEIGHT);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("resources/icon.png").getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(UserMessages.CLIENT_EDIT);
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
				btnOk = new JButton(UserMessages.SAVE_DATA);
				btnOk.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						// TODO Implement Action
						
					}
				});
				btnOk.setActionCommand("OK");
				buttonPane.add(btnOk);
			}
			{
				btnCancel = new JButton(UserMessages.DIALOG_CANCEL_BUTTON);
				btnCancel.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						// TODO Implement dispose action
						
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
		// TODO Implements initialization of components
		this.initButtons();
		this.initLabels();
		this.initLists();
		this.initSeparators();
		this.initTextFields();
		this.loadClient();
	}
	
	/**
	 * Initialize buttons into frame
	 */
	private void initButtons() {
		// Add mail
		btnAddEmail = new JButton(UserMessages.NEW_CLIENT_ADD_EMAIL);
		btnAddEmail.setBounds(389, 147, 199, 23);
		btnAddEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				DialogNewEmail newEmail = new DialogNewEmail();
				if (!TransferData.EMAIL.equals("")) {
					dlmEmails.addElement(TransferData.EMAIL);
					TransferData.EMAIL = "";
				}
			}
		});
		contentPane.add(btnAddEmail);
		
		// Add phone
		btnAddPhone = new JButton(UserMessages.NEW_CLIENT_ADD_PHONE);
		btnAddPhone.setBounds(95, 147, 199, 23);
		btnAddPhone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				DialogNewPhone newPhone = new DialogNewPhone();
				if (!TransferData.PREFIX.equals("") && !TransferData.PHONE.equals("")) {
					dlmPhones.addElement(TransferData.PREFIX + " " + TransferData.PHONE);
					TransferData.PREFIX = "";
					TransferData.PHONE = "";
				}
				
			}
		});
		contentPane.add(btnAddPhone);
		
		// Remove mail
		btnRemoveEmail = new JButton(UserMessages.NEW_CLIENT_REMOVE_ITEM);
		btnRemoveEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeItemList(listEmails.getSelectedIndex(), listEmails, UserMessages.NEW_CLIENT_REMOVED_EMAIL);
			}
		});
		btnRemoveEmail.setBounds(401, 334, 174, 23);
		contentPane.add(btnRemoveEmail);
		
		// Remove phone
		btnRemovePhone = new JButton(UserMessages.NEW_CLIENT_REMOVE_ITEM);
		btnRemovePhone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeItemList(listPhones.getSelectedIndex(), listPhones, UserMessages.NEW_CLIENT_REMOVED_PHONE);
			}
		});
		btnRemovePhone.setBounds(107, 334, 174, 23);
		contentPane.add(btnRemovePhone);
	}
	
	/**
	 * Initialize labels into jframe
	 */
	private void initLabels() {
		lblLocality = new JLabel(UserMessages.NEW_CLIENT_LOCALITY);
		lblLocality.setBounds(283, 82, 92, 14);
		contentPane.add(lblLocality);
		
		lblName = new JLabel(UserMessages.NEW_CLIENT_NAME);
		lblName.setBounds(10, 11, 92, 14);
		contentPane.add(lblName);
		
		lblPostalCode = new JLabel(UserMessages.NEW_CLIENT_POSTAL_CODE);
		lblPostalCode.setBounds(10, 82, 92, 14);
		contentPane.add(lblPostalCode);
		
		lblProvince = new JLabel(UserMessages.NEW_CLIENT_PROVINCE);
		lblProvince.setBounds(10, 107, 92, 14);
		contentPane.add(lblProvince);
		
		lblStreet = new JLabel(UserMessages.NEW_CLIENT_STREET);
		lblStreet.setBounds(10, 54, 92, 14);
		contentPane.add(lblStreet);
		
		lblSurnames = new JLabel(UserMessages.NEW_CLIENT_SURNAMES);
		lblSurnames.setBounds(283, 11, 92, 14);
		contentPane.add(lblSurnames);
	}
	
	/**
	 * Initialize lists and scrollpanes into frame
	 */
	private void initLists() {
		scrollPhone = new JScrollPane();
		scrollPhone.setBounds(95, 189, 199, 134);
		contentPane.add(scrollPhone);
		
		listPhones = new JList<String>();
		dlmPhones = new DefaultListModel<String>();
		listPhones.setModel(dlmPhones);
		listPhones.setSelectedIndex(-1);
		scrollPhone.setViewportView(listPhones);
		
		scrollEmail = new JScrollPane();
		scrollEmail.setBounds(389, 189, 199, 134);
		contentPane.add(scrollEmail);
		
		listEmails = new JList<String>();
		dlmEmails = new DefaultListModel<String>();
		listEmails.setModel(dlmEmails);
		listEmails.setSelectedIndex(-1);
		scrollEmail.setViewportView(listEmails);
	}
	
	/**
	 * Initialize separators into frame
	 */
	private void initSeparators() {
		separator1 = new JSeparator();
		separator1.setBounds(104, 36, 475, 2);
		contentPane.add(separator1);
		
		separator2 = new JSeparator();
		separator2.setBounds(104, 132, 475, 2);
		contentPane.add(separator2);
	}
	
	/**
	 * Initialize text fields int frame
	 */
	private void initTextFields() {
		txtLocality = new JTextField();
		txtLocality.setBounds(346, 79, 262, 20);
		txtLocality.setColumns(10);
		contentPane.add(txtLocality);
		
		txtName = new JTextField();
		txtName.setBounds(104, 8, 169, 20);
		txtName.setColumns(10);
		contentPane.add(txtName);
		
		txtPostalCode = new JTextField();
		txtPostalCode.setBounds(104, 79, 169, 20);
		txtPostalCode.setColumns(10);
		contentPane.add(txtPostalCode);
		
		txtProvince = new JTextField();
		txtProvince.setBounds(104, 104, 169, 20);
		txtProvince.setColumns(10);
		contentPane.add(txtProvince);
		
		txtStreet = new JTextField();
		txtStreet.setBounds(104, 51, 504, 20);
		txtStreet.setColumns(10);
		contentPane.add(txtStreet);
		
		txtSurnames = new JTextField();
		txtSurnames.setBounds(346, 8, 262, 20);
		txtSurnames.setColumns(10);
		contentPane.add(txtSurnames);
	}
	
	/**
	 * Remove selected item from a list
	 * @param index Integer index of the item.
	 * @param list List which I want to erase item
	 * @param message Message if remove item is OK
	 */
	private void removeItemList(int index, JList<String> list, String message) {
		if (index != -1) {
			DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
            model.remove(index);
            JOptionPane.showMessageDialog(null, message);
		}
		else {
			JOptionPane.showMessageDialog(null, UserMessages.NEW_CLIENT_ERROR_REMOVE);
			TransferData.CLIENT = null;
		}
	}
	
	/**
	 * Substract prefix and phone number of a String
	 * @param completeNumber String with prefix and phone separated by a space
	 * @return
	 */
	private String[] substractPhone(String completeNumber) {
		String prefix = "";
		String number = "";
		boolean turn = true; // True = prefix turn, false = number turn
		String[] phone = new String[2];
		
		for (char i : completeNumber.toCharArray()) {
			if (String.valueOf(i).equals(" ")) {
				turn = false;
			}
			else {
				if (turn) {
					prefix += String.valueOf(i);
				}
				else {
					number += String.valueOf(i);
				}
			}
		}
		phone[0] = prefix;
		phone[1] = number;
		
		return phone;
	}
	
	private void loadClient() {
		try {
			Client c = new SQLiteHelper().getClientFromId(this.clientCode);
			this.txtName.setText(c.getName());
			this.txtSurnames.setText(c.getSurname());
			this.txtStreet.setText(c.getAddress().getStreet());
			this.txtPostalCode.setText(c.getAddress().getPostalCode());
			this.txtLocality.setText(c.getAddress().getLocality());
			this.txtProvince.setText(c.getAddress().getProvince());
			for (int i = 0; i < c.getPhones().size(); i++) {
				dlmPhones.addElement(c.getPhones().elementAt(i).getPrefix() + " " + c.getPhones().elementAt(i).getNumber());
			}
			for (int i = 0; i < c.getEmails().size(); i++) {
				dlmEmails.addElement(c.getEmails().elementAt(i).getEmail());
			}
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_CLIENT_FIELDS);
		} catch (InvalidTelephoneException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		} catch (InvalidEmailException ex1) {
			JOptionPane.showMessageDialog(null, ex1.getMessage());
		}
		
	}
}
