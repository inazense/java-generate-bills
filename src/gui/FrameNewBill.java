package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import SuperClasses.MyFrame;
import database.SQLiteHelper;
import exceptions.InvalidBillException;
import exceptions.InvalidEmailException;
import exceptions.InvalidPaymentException;
import exceptions.InvalidTelephoneException;
import participants.Bill;
import participants.Payment;
import utils.GeneralConfigurations;
import utils.UserMessages;

@SuppressWarnings("serial")
public class FrameNewBill extends MyFrame {

	// Properties
	private HashMap<String, Integer> clients;
	
	private SQLiteHelper sHelper;
	
	private DefaultListModel<String> modelClients;
	
	private DefaultTableModel modelPayments;
	
	private JButton btnAddPayment;
	private JButton btnAddVAT;
	private JButton btnDeletePayment;
	private JButton btnNewClient;
	private JButton btnRefreshClients;
	private JButton btnSeeClient;
	private JButton btnSave;
	private JButton btnToPDF;
	private JButton btnToPrint;
	
	private JLabel lblAmount;
	private JLabel lblClient;
	private JLabel lblConcept;
	private JLabel lblCurrency;
	private JLabel lblBillNumber;
	private JLabel lblPercent;
	private JLabel lblTotal;
	private JLabel lblVAT;
	private JLabel lblWithoutVAT;
	
	private JList<String> listClients;
	
	private JSeparator separator;
	
	private JScrollPane scrollClients;
	private JScrollPane scrollConcepts;
	private JScrollPane scrollPayments;
	
	private JTable tblPayments;
	
	private JTextArea txtConcept;
	
	private JTextField txtAmount;
	private JTextField txtBillNumber;
	private JTextField txtVAT;
	
	// Constructor
	public FrameNewBill(int width, int height, String title) {
		super(width, height, title);
		sHelper = new SQLiteHelper();
		this.init();
	}
	
	// Methods
	
	/**
	 * Initialize all components into frame
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
	
	/**
	 * Initialize buttons
	 */
	private void initButtons() {
		
		// Add Payment
		this.btnAddPayment = new JButton(UserMessages.NEW_BILL_ADD_PAYMENT);
		this.btnAddPayment.setBounds(159, 207, 117, 23);
		this.btnAddPayment.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					Payment p = new Payment(txtConcept.getText(), Double.parseDouble(txtAmount.getText()));
					modelPayments.addRow(new Object[]{p.getPaymentConcept(), p.getAmount()});
					txtConcept.setText("");
					txtAmount.setText("");
					lblTotal.setText(UserMessages.NEW_BILL_TOTAL + calculatesImport() + " " + UserMessages.CURRENCY_SYMBOL);
					lblWithoutVAT.setText(UserMessages.NEW_BILL_WITHOUT_VAT);
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, UserMessages.IS_NOT_DOUBLE);
				} catch (InvalidPaymentException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		contentPane.add(this.btnAddPayment);
		
		// Save Bill
		this.btnSave = new JButton(UserMessages.SAVE_DATA);
		this.btnSave.setBounds(585, 428, 89, 23);
		this.btnSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (saveBill()) {
					JOptionPane.showMessageDialog(null, UserMessages.NEW_BILL_SAVED);
				}
			}
		});
		contentPane.add(this.btnSave);
		
		// Remove row
		this.btnDeletePayment = new JButton(UserMessages.REMOVE_ITEM);
		this.btnDeletePayment.setBounds(10, 382, 153, 23);
		this.btnDeletePayment.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				modelPayments.removeRow(tblPayments.getSelectedRow());
				lblTotal.setText(UserMessages.NEW_BILL_TOTAL + calculatesImport() + " " + UserMessages.CURRENCY_SYMBOL);
				if (modelPayments.getRowCount() > 0) {
					lblWithoutVAT.setText(UserMessages.NEW_BILL_WITHOUT_VAT);
				}
				
			}
		});
		contentPane.add(this.btnDeletePayment);
		
		// Add VAT
		this.btnAddVAT = new JButton(UserMessages.NEW_BILL_ADD_VAT);
		this.btnAddVAT.setBounds(563, 312, 111, 23);
		this.btnAddVAT.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					lblTotal.setText(UserMessages.NEW_BILL_TOTAL + calculatesVAT(calculatesImport()) + " " + UserMessages.CURRENCY_SYMBOL);
					lblWithoutVAT.setText("");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		contentPane.add(this.btnAddVAT);
		
		this.btnToPDF = new JButton(UserMessages.NEW_BILL_TO_PDF);
		this.btnToPDF.setBounds(486, 428, 89, 23);
		contentPane.add(this.btnToPDF);
		
		this.btnToPrint = new JButton(UserMessages.NEW_BILL_TO_PRINT);
		this.btnToPrint.setBounds(387, 428, 89, 23);
		contentPane.add(this.btnToPrint);
		
		// New Client
		this.btnNewClient = new JButton(UserMessages.NEW);
		this.btnNewClient.setBounds(564, 70, 73, 23);
		this.btnNewClient.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new FrameNewClient(GeneralConfigurations.SUBFRAMES_WIDTH, GeneralConfigurations.SUBFRAMES_HEIGHT, UserMessages.MENU_ITEM_NEW_CLIENT);
			}
		});
		contentPane.add(this.btnNewClient);
		
		// See / Edit client
		this.btnSeeClient = new JButton(UserMessages.NEW_BILL_VIEW_CLIENT);
		this.btnSeeClient.setBounds(564, 104, 110, 23);
		this.btnSeeClient.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (listClients.getSelectedIndex() != -1) {
					new DialogEditClient(clients.get(listClients.getSelectedValue()));
					try {
						clients = sHelper.showAllClients();
						fillList();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_CLIENTS);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, UserMessages.NEW_BILL_FAIL_SELECT_CLIENT);
				}
				
			}
		});
		contentPane.add(this.btnSeeClient);
		
		// Refresh clients
		this.btnRefreshClients = new JButton();
		this.btnRefreshClients.setIcon(new ImageIcon("resources/reload.png"));
		this.btnRefreshClients.setBounds(641, 70, 33, 23);
		this.btnRefreshClients.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					clients = sHelper.showAllClients();
					fillList();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_CLIENTS);
				}
			}
		});
		contentPane.add(this.btnRefreshClients);
	}
	
	/**
	 * Initialize labels
	 */
	private void initLabels() {
		this.lblConcept = new JLabel(UserMessages.NEW_BILL_CONCEPT + ":");
		this.lblConcept.setBounds(10, 45, 90, 14);
		contentPane.add(this.lblConcept);
		
		this.lblAmount = new JLabel(UserMessages.NEW_BILL_AMOUNT + ":");
		this.lblAmount.setBounds(10, 211, 73, 14);
		contentPane.add(this.lblAmount);
		
		this.lblCurrency = new JLabel(UserMessages.CURRENCY_SYMBOL);
		this.lblCurrency.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblCurrency.setBounds(135, 213, 12, 10);
		contentPane.add(this.lblCurrency);
		
		this.lblVAT = new JLabel(UserMessages.NEW_BILL_VAT);
		this.lblVAT.setBounds(563, 256, 46, 14);
		contentPane.add(this.lblVAT);
		
		this.lblPercent = new JLabel("%");
		this.lblPercent.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblPercent.setBounds(641, 284, 12, 14);
		contentPane.add(this.lblPercent);
		
		this.lblTotal = new JLabel(UserMessages.NEW_BILL_TOTAL);
		this.lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblTotal.setBounds(333, 386, 220, 14);
		contentPane.add(this.lblTotal);
		
		this.lblClient = new JLabel(UserMessages.NEW_BILL_CLIENT);
		this.lblClient.setBounds(350, 45, 90, 14);
		contentPane.add(this.lblClient);
		
		this.lblBillNumber = new JLabel(UserMessages.NEW_BILL_NUMBER);
		this.lblBillNumber.setBounds(10, 11, 73, 14);
		contentPane.add(this.lblBillNumber);
		
		this.lblWithoutVAT = new JLabel();
		this.lblWithoutVAT.setForeground(Color.RED);
		this.lblWithoutVAT.setBounds(288, 386, 98, 14);
		contentPane.add(this.lblWithoutVAT);
	}

	/**
	 * Initialize list
	 */
	private void initLists() {
		this.scrollClients = new JScrollPane();
		this.scrollClients.setBounds(349, 70, 204, 127);
		contentPane.add(this.scrollClients);
		
		modelClients = new DefaultListModel<String>();

		this.listClients = new JList<String>();
		this.listClients.setModel(modelClients);
		
		try {
			this.clients = sHelper.showAllClients();
			this.fillList();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_CLIENTS);
		}
		
		this.scrollClients.setViewportView(this.listClients);
	}

	/**
	 * Initialize separators
	 */
	private void initSeparators() {
		this.separator = new JSeparator();
		this.separator.setOrientation(SwingConstants.VERTICAL);
		this.separator.setBounds(333, 45, 18, 150);
		contentPane.add(this.separator);
	}

	/**
	 * Initialize tables
	 */
	private void initTables() {
		this.scrollPayments = new JScrollPane();
		this.scrollPayments .setBounds(10, 252, 543, 115);
		contentPane.add(this.scrollPayments );
		
		this.modelPayments = new DefaultTableModel();
		this.modelPayments.addColumn(UserMessages.NEW_BILL_CONCEPT);
		this.modelPayments.addColumn(UserMessages.NEW_BILL_AMOUNT);
		
		this.tblPayments = new JTable(this.modelPayments);
		this.tblPayments.getColumnModel().getColumn(0).setPreferredWidth(266);
		this.tblPayments.getColumnModel().getColumn(1).setPreferredWidth(34);
		this.tblPayments.getColumnModel().getColumn(0).setResizable(false);
		this.tblPayments.getColumnModel().getColumn(1).setResizable(false);
		
		this.scrollPayments.setViewportView(this.tblPayments);
	}

	/**
	 * Initialize text fields
	 */
	private void initTextFields() {
		// Concepts
		this.scrollConcepts = new JScrollPane();
		this.scrollConcepts.setBounds(10, 70, 266, 127);
		contentPane.add(this.scrollConcepts);
		
		this.txtConcept = new JTextArea();
		this.scrollConcepts.setViewportView(this.txtConcept);
		
		this.txtAmount = new JTextField();
		this.txtAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		this.txtAmount.setBounds(63, 208, 73, 20);
		contentPane.add(this.txtAmount);
		
		this.txtVAT = new JTextField();
		this.txtVAT.setBounds(563, 281, 73, 20);
		this.txtVAT.setColumns(10);
		contentPane.add(this.txtVAT);
		
		this.txtBillNumber = new JTextField();
		this.txtBillNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		this.txtBillNumber.setBounds(93, 8, 307, 20);
		this.txtBillNumber.setColumns(10);
		contentPane.add(this.txtBillNumber);
	}
	
	/**
	 * Calculates total import of payments and then add VAT if exists
	 * @return double. Total import of bill
	 */
	private double calculatesImport() {
		double r = 0;
		
		for (int i = 0; i < this.tblPayments.getRowCount(); i++) {
			r += (Double)this.tblPayments.getValueAt(i, 1);
		}

		return r;
	}
	
	/**
	 * Calculates total import + VAT
	 * @param amount double. Total import in table + VAT
	 * @return
	 * @throws Exception 
	 */
	private double calculatesVAT(double amount) throws Exception {
		if (!this.txtVAT.getText().equals("")) {
			if (amount > 0.0) {
				try {
					double vat = (amount / 100) * Double.parseDouble(this.txtVAT.getText());
					return amount + vat;
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, UserMessages.IS_NOT_DOUBLE);
				}
			}
		}
		else {
			throw new Exception(UserMessages.VAT_EXCEPTION);
		}
		
		return amount;
	}
	
	/**
	 * Fill list with clients of database
	 */
	private void fillList() {
		
		this.clearList();
		
		for (Entry<String, Integer> i : clients.entrySet()) {
			modelClients.addElement(i.getKey());
		}
		
	}
	
	/**
	 * Remove all elements of the list
	 */
	private void clearList() {
		DefaultListModel<String> listModel = (DefaultListModel<String>) this.listClients.getModel();
		listModel.removeAllElements();
	}
	
	/**
	 * Save bill into database, in tables bills and payments
	 * @return boolean. true if there is not an exception, false if not
	 */
	private boolean saveBill() {
		
		try {
			Bill b = new Bill();
			b.setBillNumber(this.txtBillNumber.getText());
			b.setVat(Double.parseDouble(this.txtVAT.getText()));
			b.setPayments(new Vector<Payment>());
			for (int i = 0; i < tblPayments.getRowCount(); i++) {
				Payment p = new Payment();
				p.setPaymentConcept((String)tblPayments.getValueAt(i, 0));
				p.setAmount((Double)tblPayments.getValueAt(i, 1));
				b.getPayments().add(p);
			}
			b.setClient(sHelper.getClientFromId(clients.get(listClients.getSelectedValue())));
			
			// TODO Save Bill into SQLite database
			return true;
		} 
		catch (InvalidBillException | InvalidPaymentException | InvalidTelephoneException | InvalidEmailException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_CLIENTS);
			return false;
		}
		
	}
}
