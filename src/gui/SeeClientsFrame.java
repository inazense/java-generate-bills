package gui;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import utils.UserMessages;

@SuppressWarnings("serial")
public class SeeClientsFrame extends MyFrame {

	// Properties
	private DefaultListModel<String> dlmClients;
	
	private JButton btnSearch;
	
	private JList<String> listClients;
	
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
	
	private JTextField txtEmail;
	private JTextField txtLocality;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtProvince;
	private JTextField txtSurname;
	
	// Constructor
	public SeeClientsFrame(int width, int height, String title) {
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
					JOptionPane.showMessageDialog(null, "Test");
				}
			}
		});
		
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
}
