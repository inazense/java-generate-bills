package gui;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import SuperClasses.MyFrame;

@SuppressWarnings("serial")
public class FrameSearchBills extends MyFrame {

	// Properties
	private MaskFormatter mask;
	
	private JFormattedTextField txtDate;
	
	private JLabel lblTitle;
	private JLabel lblBill;
	private JLabel lblDate;
	private JLabel lblClient;
	private JLabel lblHelp;
	
	private JScrollPane scrollClient;
	private JScrollPane scrollBill;
	
	private JSeparator separator;
	
	private JTable tblBills;
	
	private JTextField txtBillNumber;
	
	// Constructor
	public FrameSearchBills(int width, int height, String title) {
		super(width, height, title);
		this.init();
	}
	
	// Methods
	private void init() {
		setVisible(true);
	}
}
