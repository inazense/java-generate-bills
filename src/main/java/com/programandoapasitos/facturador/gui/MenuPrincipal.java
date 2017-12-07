package com.programandoapasitos.facturador.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.programandoapasitos.facturador.gui.superclases.FramePadre;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;

@SuppressWarnings("serial")
public class MenuPrincipal extends FramePadre {

	// Propiedades
	private JLabel lblIcono;
	private JMenuBar menuBar;
	private JMenu mnClientes;
	private JMenu mnFacturas;
	private JMenuItem mntmNuevaFactura;
	private JMenuItem mntmNuevoCliente;
	private JMenuItem mntmBuscarFactura;
	private JMenuItem mntmBuscarCliente;
	
	// Constructor
	public MenuPrincipal(int ancho, int alto, String titulo) {
		super(ancho, alto, titulo);
		this.inicializar();
	}
	
	// Métodos
	
	public void inicializar() {
		this.inicializarMenuBar();
		this.iniciarLabels();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Sobrescribe setDefaultCloseOperation de FramePadre
		setVisible(true);
	}
	
	public void inicializarMenuBar() {
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 900, 21);
		this.panel.add(menuBar);
		this.inicializarMenuFacturas();
		this.inicializarMenuClientes();
	}
	
	/**
	 * Initialize submenu Bills
	 */
	public void inicializarMenuFacturas() {
		mnFacturas = new JMenu(ManejadorProperties.verLiteral("MENU_FACTURAS"));
		menuBar.add(mnFacturas);
		
		mntmNuevaFactura = new JMenuItem(ManejadorProperties.verLiteral("MENU_ITEM_NEW_BILL"));
		mntmNuevaFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FrameNuevaFactura(ManejadorProperties.verGui("SUBFRAMES_WIDTH"), ManejadorProperties.verGui("SUBFRAMES_HEIGHT"), ManejadorProperties.verLiteral("MENU_ITEM_NEW_BILL"));
			}
		});
		mnFacturas.add(mntmNuevaFactura);
		
		mntmBuscarFactura = new JMenuItem(ManejadorProperties.verLiteral("MENU_ITEM_SEARCH_BILL"));
		mntmBuscarFactura.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new FrameBuscarFacturas(ManejadorProperties.verGui("SUBFRAMES_WIDTH"), ManejadorProperties.verGui("SUBFRAMES_HEIGHT"), ManejadorProperties.verLiteral("MENU_ITEM_SEARCH_BILL"));
			}
		});
		mnFacturas.add(mntmBuscarFactura);
	}
	
	public void inicializarMenuClientes() {
		mnClientes = new JMenu(ManejadorProperties.verLiteral("MENU_CLIENTES"));
		menuBar.add(mnClientes);
		
		mntmNuevoCliente = new JMenuItem(ManejadorProperties.verLiteral("MENU_ITEM_NEW_CLIENT"));
		mntmNuevoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrameNuevoCliente(ManejadorProperties.verGui("SUBFRAMES_WIDTH"), ManejadorProperties.verGui("SUBFRAMES_HEIGHT"), ManejadorProperties.verLiteral("MENU_ITEM_NEW_CLIENT"));
			}
		});
		mnClientes.add(mntmNuevoCliente);
		
		mntmBuscarCliente = new JMenuItem(ManejadorProperties.verLiteral("MENU_ITEM_SEARCH_CLIENT"));
		mntmBuscarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FrameBuscarClientes(ManejadorProperties.verGui("SUBFRAMES_WIDTH"), ManejadorProperties.verGui("SUBFRAMES_HEIGHT"), ManejadorProperties.verLiteral("MENU_ITEM_SEARCH_CLIENT"));
			}
		});
		mnClientes.add(mntmBuscarCliente);
	}
	
	/**
	 * Initialize icon label
	 */
	public void iniciarLabels() {
		lblIcono = new JLabel("");
		lblIcono.setIcon(new ImageIcon(ManejadorProperties.verRuta("MENU_ITEM_SEARCH_CLIENT")));
		lblIcono.setBounds(342, 240, 210, 124);
		this.panel.add(lblIcono);
	}
}
