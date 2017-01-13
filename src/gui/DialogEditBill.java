package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.DocumentException;

import database.SQLiteHelper;
import exceptions.InvalidBillException;
import exceptions.InvalidEmailException;
import exceptions.InvalidPaymentException;
import exceptions.InvalidTelephoneException;
import participants.Bill;
import participants.Payment;
import utils.GeneralConfigurations;
import utils.PdfGenerator;
import utils.PdfPrinter;
import utils.UserMessages;

@SuppressWarnings("serial")
public class DialogEditBill extends JDialog {

	// Properties
	private SQLiteHelper sHelper;
	private Bill b;
	
	private DefaultTableModel modelPayments;
	
	private JButton btnCancel;
	private JButton btnDelete;
	private JButton btnPrint;
	private JButton btnPDF;
	private JButton btnSeeClient;
	
	private JLabel lblBill;
	private JLabel lblClient;
	private JLabel lblDate;
	private JLabel lblPercent;
	private JLabel lblTotal;
	private JLabel lblVAT;
	
	private JPanel contentPane;
	private JPanel buttonPane;
	
	private JScrollPane scrollPayments;
	
	private JTable tblPayments;
	
	private JTextField txtBill;
	private JTextField txtClient;
	private JTextField txtDate;
	private JTextField txtVAT;
	
	private String billId;
	
	// Constructor
	public DialogEditBill(String billId) {
		sHelper = new SQLiteHelper();
		b = new Bill();
		this.billId = billId;
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
		setTitle(UserMessages.BILL_EDIT);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		this.initJPanels();
		getContentPane().add(contentPane, BorderLayout.CENTER);
		setVisible(true);
	}
	
	/**
	 * Init panels and components
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
				this.btnDelete = new JButton(UserMessages.BILL_DELETE);
				this.btnDelete.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							sHelper.deleteBillById(b.getBillNumber());
							JOptionPane.showMessageDialog(null, UserMessages.BILL_DELETED);
							dispose();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, UserMessages.BILL_FAIL_DELETE);
						}
					}
				});
				this.btnDelete.setActionCommand("Delete");
				buttonPane.add(this.btnDelete);
			}
			{
				this.btnPrint = new JButton(UserMessages.NEW_BILL_TO_PRINT);
				this.btnPrint.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						FileInputStream fis;
						try {
							new PdfGenerator(b.getBillNumber() + ".pdf").createBill(b);
							fis = new FileInputStream("facturas/" + b.getBillNumber() + ".pdf");
							PdfPrinter printer = new PdfPrinter(fis, b.getBillNumber());
							printer.print();
							JOptionPane.showMessageDialog(null, UserMessages.PRINT_PDF);
						} catch (IOException | PrinterException | DocumentException e1) {
							JOptionPane.showMessageDialog(null, UserMessages.FAIL_PRINT_PDF);
						}
						
					}
				});
				this.btnPrint.setActionCommand("Print");
				buttonPane.add(this.btnPrint);
				
			}
			{
				this.btnPDF = new JButton(UserMessages.NEW_BILL_TO_PDF);
				this.btnPDF.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						try {
							new PdfGenerator(b.getBillNumber() + ".pdf").createBill(b);
							JOptionPane.showMessageDialog(null, UserMessages.CREATE_PDF);
						} catch (IOException | DocumentException e1) {
							JOptionPane.showMessageDialog(null, UserMessages.FAIL_CREATE_PDF);
						}
					}
				});
				this.btnPDF.setActionCommand("PDF");
				buttonPane.add(this.btnPDF);
			}
			{
				btnCancel = new JButton(UserMessages.CLOSE);
				btnCancel.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
		
		// Initialize components
		this.initButtons();
		this.initLabels();
		this.initTables();
		this.initTextFields();
		this.loadData();
	}
	
	/**
	 * Initialize buttons
	 */
	private void initButtons() {
		this.btnSeeClient = new JButton(UserMessages.NEW_BILL_VIEW_CLIENT);
		this.btnSeeClient.setBounds(495, 44, 110, 23);
		this.btnSeeClient.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new DialogEditClient(b.getClient().getClientCode());
				loadData();
			}
		});
		contentPane.add(this.btnSeeClient);
	}
	
	/**
	 * Initialize labels
	 */
	private void initLabels() {
		this.lblBill = new JLabel(UserMessages.NEW_BILL_NUMBER);
		this.lblBill.setBounds(10, 14, 73, 14);
		contentPane.add(this.lblBill);
		
		this.lblDate = new JLabel(UserMessages.NEW_BILL_DATE);
		this.lblDate.setBounds(495, 17, 46, 14);
		contentPane.add(this.lblDate);
		
		this.lblClient = new JLabel(UserMessages.NEW_BILL_CLIENT);
		this.lblClient.setBounds(10, 48, 46, 14);
		contentPane.add(this.lblClient);
		
		this.lblVAT = new JLabel(UserMessages.NEW_BILL_VAT);
		this.lblVAT.setBounds(10, 379, 46, 14);
		contentPane.add(this.lblVAT);
		
		this.lblPercent = new JLabel(UserMessages.PERCENT);
		this.lblPercent.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblPercent.setBounds(128, 379, 12, 14);
		contentPane.add(this.lblPercent);
		
		this.lblTotal = new JLabel(UserMessages.NEW_BILL_TOTAL);
		this.lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lblTotal.setBounds(441, 379, 220, 14);
		contentPane.add(this.lblTotal);
	}
	
	private void initTables() {
		this.scrollPayments = new JScrollPane();
		this.scrollPayments.setBounds(10, 103, 651, 248);
		contentPane.add(this.scrollPayments);
		
		this.modelPayments = new DefaultTableModel();
		this.modelPayments.addColumn(UserMessages.NEW_BILL_CONCEPT);
		this.modelPayments.addColumn(UserMessages.NEW_BILL_AMOUNT);
		
		this.tblPayments = new JTable(this.modelPayments) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.tblPayments.getColumnModel().getColumn(0).setPreferredWidth(410);
		this.scrollPayments.setViewportView(this.tblPayments);
	}
	
	/**
	 * Initialize text fields
	 */
	private void initTextFields() {
		this.txtBill = new JTextField();
		this.txtBill.setHorizontalAlignment(SwingConstants.RIGHT);
		this.txtBill.setColumns(10);
		this.txtBill.setBounds(93, 11, 307, 20);
		contentPane.add(this.txtBill);
		
		this.txtClient = new JTextField();
		this.txtClient.setHorizontalAlignment(SwingConstants.RIGHT);
		this.txtClient.setColumns(10);
		this.txtClient.setBounds(93, 45, 307, 20);
		contentPane.add(this.txtClient);
		
		this.txtDate = new JTextField();
		this.txtDate.setBounds(551, 11, 110, 20);
		contentPane.add(this.txtDate);
		this.txtDate.setColumns(10);
		
		this.txtVAT = new JTextField();
		this.txtVAT.setColumns(10);
		this.txtVAT.setBounds(50, 376, 73, 20);
		contentPane.add(this.txtVAT);
	}
	
	/**
	 * Load data into GUI fields from database
	 */
	private void loadData() {
		this.clearFields();
		try {
			this.b = sHelper.getBillFromId(this.billId);
			this.txtBill.setText(this.b.getBillNumber());
			this.txtDate.setText(this.b.getDate());
			this.txtVAT.setText(String.valueOf(this.b.getVat()));
			
			String client = this.b.getClient().getName() + " " + this.b.getClient().getSurname();
			if (this.b.getClient().getAddress() != null) {
				client += " - " + this.b.getClient().getAddress().getLocality();
			}
			this.txtClient.setText(client);
			
			double total = 0;
			for (Payment i : this.b.getPayments()) {
				this.modelPayments.addRow(new Object[]{i.getPaymentConcept(), i.getAmount()});
				total += i.getAmount();
			}
			this.lblTotal.setText(UserMessages.NEW_BILL_TOTAL + String.format("%.2f", calculatesVAT(total)) + " " + UserMessages.CURRENCY_SYMBOL);
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, UserMessages.FAIL_LOAD_ONE_BILL);
		} 
		catch (InvalidBillException | InvalidTelephoneException | InvalidEmailException | InvalidPaymentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	private void clearFields() {
		this.txtBill.setText("");
		this.txtClient.setText("");
		this.txtDate.setText("");
		DefaultTableModel dtm = (DefaultTableModel) this.tblPayments.getModel();
		dtm.setRowCount(0);
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
}
