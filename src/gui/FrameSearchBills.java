package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
	
	private JButton btnSearch;
	
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
		this.initButtons();
		this.initLabels();
		this.initLists();
		this.initSeparators();
		this.initTables();
		this.initTextFields();
	}
	
	private void initButtons() {
		this.btnSearch = new JButton(UserMessages.SEARCH);
		this.btnSearch.setBounds(585, 220, 89, 23);
		
		// Search
		this.btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createFilter();
				txtBillNumber.setText("");
				txtDate.setText("");
				listClients.setSelectedIndex(-1);
				listClients.clearSelection();
			}
		});
		contentPane.add(this.btnSearch);
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
		this.lblListBills.setBounds(10, 272, 132, 14);
		contentPane.add(this.lblListBills);
		
		this.lblHelp = new JLabel(UserMessages.BILL_LIST_INSTRUCTIONS);
		this.lblHelp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.lblHelp.setBounds(131, 272, 306, 14);
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
		this.separator.setBounds(202, 259, 280, 2);
		contentPane.add(this.separator);
	}
	
	/**
	 * Initialize tables
	 */
	private void initTables() {
		this.scrollBill = new JScrollPane();
		this.scrollBill.setBounds(10, 297, 664, 154);
		contentPane.add(this.scrollBill);
		
		this.modelBills = new DefaultTableModel();
		this.modelBills.addColumn(UserMessages.BILL_NUMBER);
		this.modelBills.addColumn(UserMessages.BILL_CLIENT);
		this.modelBills.addColumn(UserMessages.BILL_DATE_TABLE);
		
		this.tblBills = new JTable(this.modelBills) {
			// Cells not editable
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.tblBills.getColumnModel().getColumn(0).setPreferredWidth(150);
		this.tblBills.getColumnModel().getColumn(1).setPreferredWidth(280);
		this.tblBills.getColumnModel().getColumn(2).setPreferredWidth(60);
		
		// Fill table
		try {
			this.bills = sHelper.showAllBills();
			this.fillTable();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_BILLS);
		}
		
		// Double click event
		this.tblBills.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					String billId = (String) modelBills.getValueAt(tblBills.getSelectedRow(), 0);
					new DialogEditBill(billId);
					try {
						bills = sHelper.showAllBills();
						fillTable();
					}
					catch(SQLException e) {
						JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_BILLS);
					}
				}
			}
		});
		
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
		this.txtDate.setHorizontalAlignment(SwingConstants.RIGHT);
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
	
	/**
	 * Catch values from fields and pass corresponding name of field in database and the value to 
	 * get properly results into where clausure.
	 * Then, fill JTable with them
	 */
	private void createFilter() {
		Integer client = listClients.getSelectedIndex();
		String date = this.txtDate.getText();
		String bill = this.txtBillNumber.getText();
		String filter = "";
		String value = "";
		int i = 0;
		
		if (!String.valueOf(date.toCharArray()[0]).equals(" ")) {
			filter = "date";
			value = date;
			i++;
		}
		if (!bill.equals("")) {
			filter = "id";
			value = bill;
			i++;
		}
		if (client != -1) {
			filter = "client";
			value = String.valueOf(this.clients.get(listClients.getSelectedValue()));
			i++;
		}
		
		if (i == 0) {
			try {
				this.bills = null;
				this.bills = sHelper.showAllBills();
				this.fillTable();
			}
			catch(SQLException e) {
				JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_BILLS);
			}
		}
		else if (i == 1) {
			try {
				this.bills = null;
				this.bills = sHelper.showFilteredBills(filter, value);
				this.fillTable();
			}
			catch(SQLException e) {
				JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_BILLS_FILTERS);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, UserMessages.MORE_THAN_ONE_FILTER);
		}
	}
}
