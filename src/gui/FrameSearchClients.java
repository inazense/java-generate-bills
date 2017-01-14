package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import SuperClasses.MyFrame;
import database.SQLiteHelper;
import utils.UserMessages;

@SuppressWarnings("serial")
public class FrameSearchClients extends MyFrame {

	// Properties
	private HashMap<String, Integer> clients;
	
	private DefaultListModel<String> dlmClients;
	
	private JButton btnSearch;
	
	private JList<String> listClients;
	
	private JLabel lblDni;
	private JLabel lblEmail;
	private JLabel lblInstructions;
	private JLabel lblList;
	private JLabel lblLocality;
	private JLabel lblName;
	private JLabel lblPhone;
	private JLabel lblProvince;
	private JLabel lblSearch;
	private JLabel lblSurname;
	
	private JSeparator separator;
	
	private JScrollPane scrollPane;
	
	private JTextField txtDni;
	private JTextField txtEmail;
	private JTextField txtLocality;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtProvince;
	private JTextField txtSurname;
	
	// Constructor
	public FrameSearchClients(int width, int height, String title) {
		super(width, height, title);
		this.init();
	}
	
	// Methods
	
	/**
	 * Initialize all components of the frame
	 */
	private void init() {
		setVisible(true);
		this.initButtons();
		this.initLabels();
		this.initLists();
		this.initSeparators();
		this.initTextFields();
	}
	
	/**
	 * Initialize buttons into frame
	 */
	private void initButtons() {
		btnSearch = new JButton(UserMessages.SEARCH);
		btnSearch.setBounds(577, 151, 89, 23);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createFilter();
			}
		});
		contentPane.add(btnSearch);
	}
	
	/**
	 * Initialize labels into frame
	 */
	private void initLabels() {
		
		lblEmail = new JLabel(UserMessages.CLIENT_EMAIL);
		lblEmail.setBounds(313, 111, 67, 14);
		contentPane.add(lblEmail);
		
		lblInstructions = new JLabel(UserMessages.CLIENT_LIST_INSTRUCTIONS);
		lblInstructions.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInstructions.setBounds(132, 203, 270, 14);
		contentPane.add(lblInstructions);
		
		lblList = new JLabel(UserMessages.CLIENT_LIST);
		lblList.setBounds(10, 203, 112, 14);
		contentPane.add(lblList);
		
		lblLocality = new JLabel(UserMessages.NEW_CLIENT_LOCALITY);
		lblLocality.setBounds(16, 76, 67, 14);
		contentPane.add(lblLocality);
		
		lblName = new JLabel(UserMessages.NEW_CLIENT_NAME);
		lblName.setBounds(16, 36, 67, 14);
		contentPane.add(lblName);
		
		lblPhone = new JLabel(UserMessages.CLIENT_PHONE);
		lblPhone.setBounds(16, 111, 67, 14);
		contentPane.add(lblPhone);
		
		lblDni = new JLabel(UserMessages.DNI);
		lblDni.setBounds(16, 146, 67, 14);
		contentPane.add(lblDni);
		
		lblProvince = new JLabel(UserMessages.NEW_CLIENT_PROVINCE);
		lblProvince.setBounds(313, 76, 67, 14);
		contentPane.add(lblProvince);
		
		lblSurname = new JLabel(UserMessages.NEW_CLIENT_SURNAMES);
		lblSurname.setBounds(313, 36, 67, 14);
		contentPane.add(lblSurname);
		
		lblSearch = new JLabel(UserMessages.MENU_ITEM_SEARCH_CLIENT);
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSearch.setBounds(294, 11, 95, 14);
		contentPane.add(lblSearch);
	}
	
	/**
	 * Initialize lists into frame
	 */
	private void initLists() {
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 228, 664, 223);
		contentPane.add(scrollPane);
		
		listClients = new JList<String>();
		dlmClients = new DefaultListModel<String>();
		listClients.setModel(dlmClients);
		listClients.setSelectedIndex(-1);
		
		// Double click event
		listClients.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					new DialogEditClient(clients.get(listClients.getSelectedValue()));
					try {
						clients = new SQLiteHelper().showAllClients();
						fillList();
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_CLIENTS);
					}
				}
			}
		});
		try {
			this.clients = new SQLiteHelper().showAllClients();
			this.fillList();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_CLIENTS);
		}
		
		scrollPane.setViewportView(listClients);
	}
	

	/**
	 * Initialize separator into frame
	 */
	private void initSeparators() {
		separator = new JSeparator();
		separator.setBounds(195, 190, 294, 2);
		contentPane.add(separator); 
	}
	
	/**
	 * Initialize text fields into frame
	 */
	private void initTextFields() {
		txtEmail = new JTextField();
		txtEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		txtEmail.setColumns(10);
		txtEmail.setBounds(396, 108, 270, 20);
		contentPane.add(txtEmail);
		
		txtLocality = new JTextField();
		txtLocality.setHorizontalAlignment(SwingConstants.RIGHT);
		txtLocality.setColumns(10);
		txtLocality.setBounds(99, 73, 198, 20);
		contentPane.add(txtLocality);
		
		txtName = new JTextField();
		txtName.setHorizontalAlignment(SwingConstants.RIGHT);
		txtName.setBounds(99, 33, 198, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		 
		txtPhone = new JTextField();
		txtPhone.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPhone.setColumns(10);
		txtPhone.setBounds(99, 108, 198, 20);
		contentPane.add(txtPhone);
		
		txtDni = new JTextField();
		txtDni.setHorizontalAlignment(SwingConstants.RIGHT);
		txtDni.setColumns(10);
		txtDni.setBounds(99, 143, 198, 20);
		contentPane.add(txtDni);
		
		txtProvince = new JTextField();
		txtProvince.setHorizontalAlignment(SwingConstants.RIGHT);
		txtProvince.setColumns(10);
		txtProvince.setBounds(396, 73, 270, 20);
		contentPane.add(txtProvince);
		
		txtSurname = new JTextField();
		txtSurname.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSurname.setColumns(10);
		txtSurname.setBounds(396, 33, 270, 20);
		contentPane.add(txtSurname);
	}
	
	/**
	 * Fill list with clients of database
	 */
	private void fillList() {
		
		this.clearList();
		
		for (Entry<String, Integer> i : clients.entrySet()) {
			dlmClients.addElement(i.getKey());
		}
		
	}
	
	/**
	 * Remove all elements of the list
	 */
	private void clearList() {
		DefaultListModel<String> listModel = (DefaultListModel<String>) listClients.getModel();
		listModel.removeAllElements();
	}
	
	/**
	 * Catch values from textfields and pass corresponding name of field in database and the value to 
	 * get properly results into where clausure.
	 * Then, fill JList with them
	 */
	private void createFilter() {
		String[] filters 	= new String[7];
		String[] values 	= new String[7];
		String filter 	= "";
		String value 	= "";
		int filtersWrited = 0;
		
		values[0] = txtName.getText();
		values[1] = txtSurname.getText();
		values[2] = txtLocality.getText();
		values[3] = txtProvince.getText();
		values[4] = txtPhone.getText();
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
			JOptionPane.showMessageDialog(null, UserMessages.MORE_THAN_ONE_FILTER);
		}
		else if (filtersWrited == 1) {
			try {
				this.clients = new SQLiteHelper().showFilteredClients(filter, value);
				this.fillList();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_FILTERS);
			}
		}
		else {
			try {
				this.clients = new SQLiteHelper().showAllClients();
				this.fillList();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_CLIENTS);
			}
		}
	}
}
