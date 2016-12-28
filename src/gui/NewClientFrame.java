package gui;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import SuperClasses.MyFrame;
import utils.TransferData;
import utils.UserMessages;

@SuppressWarnings("serial")
public class NewClientFrame extends MyFrame {

	// Properties
	private DefaultListModel<String> dlmEmails;
	private DefaultListModel<String> dlmPhones;
	
	private JButton btnAddEmail;
	private JButton btnAddPhone;
	private JButton btnRemoveEmail;
	private JButton btnRemovePhone;
	
	private JLabel lblLocality;
	private JLabel lblName;
	private JLabel lblPostalCode;
	private JLabel lblProvince;
	private JLabel lblStreet;
	private JLabel lblSurnames;
	
	private JList<String> listEmails;
	private JList<String> listPhones;
	
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
	
	// Constructor
	public NewClientFrame(int width, int height, String title) {
		super(width, height, title);
		this.init();
	}
	
	// Methods
	
	/**
	 * Init all the components of the frame
	 */
	private void init() {
		setVisible(true);
		this.initLabels();
		this.initSeparators();
		this.initTextFields();
		this.initLists();
		this.initButtons();
	}
	
	/**
	 * Init buttons into frame
	 */
	private void initButtons() {
		// Add mail
		btnAddEmail = new JButton(UserMessages.NEW_CLIENT_ADD_EMAIL);
		btnAddEmail.setBounds(389, 147, 199, 23);
		btnAddEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Email");
			}
		});
		contentPane.add(btnAddEmail);
		
		// Add phone
		btnAddPhone = new JButton(UserMessages.NEW_CLIENT_ADD_PHONE);
		btnAddPhone.setBounds(95, 147, 199, 23);
		btnAddPhone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				NewPhoneDialog newPhone = new NewPhoneDialog();
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
	 * Init labels into jframe
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
	 * Init lists and scrollpanes into frame
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
	 * Init separators into frame
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
	 * Init text fields int frame
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
	 * @param list List wich I want to erase item
	 * @param message Message if remove item is ok
	 */
	private void removeItemList(int index, JList<String> list, String message) {
		if (index != -1) {
			DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
            model.remove(index);
            JOptionPane.showMessageDialog(null, message);
		}
		else {
			JOptionPane.showMessageDialog(null, UserMessages.NEW_CLIENT_ERROR_REMOVE);
		}
	}
}
