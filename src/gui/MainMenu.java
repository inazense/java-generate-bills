package gui;

import SuperClasses.MyFrame;
import utils.UserMessages;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MainMenu extends MyFrame {

	// Properties
	private JMenuBar menuBar;
	private JMenu mnFacturas;
	private JMenuItem mntmVerFacturas;
	private JMenuItem mntmNuevaFactura;
	private JMenuItem mntmBuscar;
	private JLabel lblIcon;
	
	// Constructor
	public MainMenu(int width, int height) {
		super(width, height);
		this.init();
	}
	
	// Methods
	
	/**
	 * General configurations
	 */
	public void init() {
		this.initMenuBar();
		this.initLabels();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(UserMessages.MAIN_TITLE);
		setVisible(true);
	}
	
	/**
	 * Init menu bar
	 */
	public void initMenuBar() {
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 900, 21);
		contentPane.add(menuBar);
		
		mnFacturas = new JMenu(UserMessages.MENU_FACTURAS);
		menuBar.add(mnFacturas);
		
		mntmVerFacturas = new JMenuItem(UserMessages.MENU_ITEM_SEE_BILLS);
		mntmVerFacturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SeeBillsFrame(700, 500);
			}
		});
		mnFacturas.add(mntmVerFacturas);
		
		mntmNuevaFactura = new JMenuItem(UserMessages.MENU_ITEM_NEW_BILL);
		mntmNuevaFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new NewBillFrame(700, 500);
			}
		});
		mnFacturas.add(mntmNuevaFactura);
		
		mntmBuscar = new JMenuItem(UserMessages.MENU_ITEM_SEARCH_BILL);
		mnFacturas.add(mntmBuscar);
	}
	
	/**
	 * Init icon label
	 */
	public void initLabels() {
		lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon("resources/fondo.png"));
		lblIcon.setBounds(342, 240, 210, 124);
		contentPane.add(lblIcon);
	}
}
