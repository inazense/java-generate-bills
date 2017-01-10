package gui;

import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import SuperClasses.MyFrame;

@SuppressWarnings("serial")
public class FrameSearchBills extends MyFrame {

	// Properties
	private MaskFormatter mask;
	
	private JFormattedTextField txtDate;
	
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
