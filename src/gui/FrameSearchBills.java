package gui;

import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import SuperClasses.MyFrame;
import database.SQLiteHelper;
import utils.UserMessages;

@SuppressWarnings("serial")
public class FrameSearchBills extends MyFrame {

	// Properties
	private HashMap<String, Integer> clients;
	private Vector<String[]> bills;
	
	private SQLiteHelper sHelper;
	
	private DefaultListModel<String> modelClients;
	private DefaultTableModel modelBills;
	
	private MaskFormatter mask;
	
	private JFormattedTextField txtDate;
	
	private JLabel lblTitle;
	private JLabel lblBillNumber;
	private JLabel lblDate;
	private JLabel lblClient;
	private JLabel lblListBills;
	private JLabel lblHelp;
	
	private JList<String> listClients;
	
	private JScrollPane scrollClient;
	private JScrollPane scrollBill;
	
	private JSeparator separator;
	
	private JTable tblBills;
	
	private JTextField txtBillNumber;
	
	// Constructor
	public FrameSearchBills(int width, int height, String title) {
		super(width, height, title);
		this.sHelper = new SQLiteHelper();
		this.init();
	}
	
	// Methods
	
	/**
	 * Initialize all components of the frame
	 */
	private void init() {
		setVisible(true);
		this.initLabels();
		this.initLists();
		this.initSeparators();
		this.initTables();
		this.initTextFields();
	}
	
	/**
	 * Initialize labels
	 */
	private void initLabels() {
		this.lblTitle = new JLabel(UserMessages.MENU_ITEM_SEARCH_BILL);
		this.lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		this.lblTitle.setBounds(292, 11, 100, 14);
		contentPane.add(this.lblTitle);
		
		this.lblBillNumber = new JLabel(UserMessages.BILL_NUMBER + ":");
		this.lblBillNumber.setBounds(10, 54, 66, 14);
		contentPane.add(this.lblBillNumber);
		
		this.lblDate = new JLabel(UserMessages.BILL_DATE);
		this.lblDate.setBounds(348, 54, 103, 14);
		contentPane.add(this.lblDate);
		
		this.lblClient = new JLabel(UserMessages.MENU_CLIENTES);
		this.lblClient.setBounds(10, 91, 46, 14);
		contentPane.add(this.lblClient);
		
		this.lblListBills = new JLabel(UserMessages.BILL_LIST);
		this.lblListBills.setBounds(10, 240, 132, 14);
		contentPane.add(this.lblListBills);
		
		this.lblHelp = new JLabel(UserMessages.BILL_LIST_INSTRUCTIONS);
		this.lblHelp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.lblHelp.setBounds(131, 240, 306, 14);
		contentPane.add(this.lblHelp);
	}
	
	/**
	 * Initialize lists
	 */
	private void initLists() {
		this.scrollClient = new JScrollPane();
		this.scrollClient.setBounds(86, 89, 507, 110);
		contentPane.add(this.scrollClient);
		
		this.modelClients = new DefaultListModel<String>();
		
		this.listClients = new JList<String>();
		this.listClients.setModel(this.modelClients);
		this.listClients.setSelectedIndex(-1);
		
		// Fill clients list
		try {
			this.clients = sHelper.showAllClients();
			this.fillList();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_CLIENTS);
		}
		
		this.scrollClient.setViewportView(this.listClients);
	}
	
	/**
	 * Initialize separators
	 */
	private void initSeparators() {
		this.separator = new JSeparator();
		this.separator.setBounds(202, 225, 280, 2);
		contentPane.add(this.separator);
	}
	
	/**
	 * Initialize tables
	 */
	private void initTables() {
		this.scrollBill = new JScrollPane();
		this.scrollBill.setBounds(10, 265, 664, 186);
		contentPane.add(this.scrollBill);
		
		this.modelBills = new DefaultTableModel();
		this.modelBills.addColumn(UserMessages.BILL_NUMBER);
		this.modelBills.addColumn(UserMessages.BILL_CLIENT);
		this.modelBills.addColumn(UserMessages.BILL_DATE_TABLE);
		
		this.tblBills = new JTable(this.modelBills);
		this.tblBills.getColumnModel().getColumn(0).setPreferredWidth(150);
		this.tblBills.getColumnModel().getColumn(1).setPreferredWidth(280);
		this.tblBills.getColumnModel().getColumn(2).setPreferredWidth(60);
		
		try {
			this.bills = sHelper.showBills("", "");
			this.fillTable();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_BILLS);
		}
		
		this.scrollBill.setViewportView(this.tblBills);
	}
	
	/**
	 * Initialize text fields
	 */
	private void initTextFields() {
		this.txtBillNumber = new JTextField();
		this.txtBillNumber.setBounds(86, 51, 189, 20);
		contentPane.add(this.txtBillNumber);
		this.txtBillNumber.setColumns(10);
		
		try {
			mask = new MaskFormatter("##/##/####");
			this.txtDate = new JFormattedTextField(mask);
		} catch (ParseException e) {
			this.txtDate = new JFormattedTextField();
		}
		this.txtDate = new JFormattedTextField(mask);
		this.txtDate.setBounds(461, 51, 132, 20);
		contentPane.add(this.txtDate);
		this.txtDate.setColumns(10);
	}
	
	/**
	 * Fill list with clients from database
	 */
	private void fillList() {
		
		this.clearList();
		
		for (Entry<String, Integer> i : clients.entrySet()) {
			modelClients.addElement(i.getKey());
		}
		
	}
	
	/**
	 * Remove all elements from the list
	 */
	private void clearList() {
		DefaultListModel<String> listModel = (DefaultListModel<String>) this.listClients.getModel();
		listModel.removeAllElements();
	}
	
	/**
	 * Fill table with bill from database
	 */
	private void fillTable() {
		this.clearTable();
		for (String[] i : this.bills) {
			this.modelBills.addRow(new Object[]{i[0], i[1], i[2]});
		}
	}
	
	/**
	 *  Remove all items from the table
	 */
	private void clearTable() {
		DefaultTableModel dtm = (DefaultTableModel) this.tblBills.getModel();
		dtm.setRowCount(0);
	}
}
