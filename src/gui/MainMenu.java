package gui;

import SuperClasses.MyFrame;
import utils.GeneralConfigurations;
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
	private JLabel lblIcon;
	private JMenuBar menuBar;
	private JMenu mnClients;
	private JMenu mnBills;
	private JMenuItem mntmNewBill;
	private JMenuItem mntmNewClient;
	private JMenuItem mntmSearchBill;
	private JMenuItem mntmSearchClient;
	private JMenuItem mntmSeeBills;
	
	// Constructor
	public MainMenu(int width, int height, String title) {
		super(width, height, title);
		this.init();
	}
	
	// Methods
	
	/**
	 * General configurations
	 */
	public void init() {
		this.initMenuBar();
		this.initLabels();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Overwrite setDefaultCloseOperation of MyFrame
		setVisible(true);
	}
	
	/**
	 * Initialize menu bar
	 */
	public void initMenuBar() {
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 900, 21);
		contentPane.add(menuBar);
		this.initMenuBills();
		this.initMenuClients();
	}
	
	/**
	 * Initialize submenu Bills
	 */
	public void initMenuBills() {
		mnBills = new JMenu(UserMessages.MENU_FACTURAS);
		menuBar.add(mnBills);
		
		mntmSeeBills = new JMenuItem(UserMessages.MENU_ITEM_SEE_BILLS);
		mntmSeeBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SeeBillsFrame(GeneralConfigurations.SUBFRAMES_WIDTH, GeneralConfigurations.SUBFRAMES_HEIGHT, UserMessages.MENU_ITEM_SEE_BILLS);
			}
		});
		mnBills.add(mntmSeeBills);
		
		mntmNewBill = new JMenuItem(UserMessages.MENU_ITEM_NEW_BILL);
		mntmNewBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new NewBillFrame(GeneralConfigurations.SUBFRAMES_WIDTH, GeneralConfigurations.SUBFRAMES_HEIGHT, UserMessages.MENU_ITEM_NEW_BILL);
			}
		});
		mnBills.add(mntmNewBill);
		
		mntmSearchBill = new JMenuItem(UserMessages.MENU_ITEM_SEARCH_BILL);
		mnBills.add(mntmSearchBill);
	}
	
	/**
	 * Initialize submenu Clients
	 */
	public void initMenuClients() {
		mnClients = new JMenu(UserMessages.MENU_CLIENTES);
		menuBar.add(mnClients);
		
		mntmNewClient = new JMenuItem(UserMessages.MENU_ITEM_NEW_CLIENT);
		mntmNewClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewClientFrame(GeneralConfigurations.SUBFRAMES_WIDTH, GeneralConfigurations.SUBFRAMES_HEIGHT, UserMessages.MENU_ITEM_NEW_CLIENT);
			}
		});
		mnClients.add(mntmNewClient);
		
		mntmSearchClient = new JMenuItem(UserMessages.MENU_ITEM_SEARCH_CLIENT);
		mntmSearchClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SeeClientsFrame(GeneralConfigurations.SUBFRAMES_WIDTH, GeneralConfigurations.SUBFRAMES_HEIGHT, UserMessages.MENU_ITEM_SEARCH_CLIENT);
			}
		});
		mnClients.add(mntmSearchClient);
	}
	
	/**
	 * Initialize icon label
	 */
	public void initLabels() {
		lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon("resources/fondo.png"));
		lblIcon.setBounds(342, 240, 210, 124);
		contentPane.add(lblIcon);
	}
}
