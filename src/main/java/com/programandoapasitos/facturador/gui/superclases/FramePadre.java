package com.programandoapasitos.facturador.gui.superclases;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.programandoapasitos.facturador.utiles.ManejadorProperties;

/**
 * Superclase usada para crear JFrames
 * @author Inazio
 *
 */
@SuppressWarnings("serial")
public class FramePadre extends JFrame{

	// Propiedades
	protected JPanel panel;
	protected int ancho;
	protected int alto;
	protected String titulo;
	
	// Constructor
	public FramePadre(int ancho, int alto, String titulo) {
		this.ancho = ancho;
		this.alto = alto;
		this.titulo = titulo;
	}
	
	// Métodos
	
	/**
	 * Inicializa las características generales
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void inicializarGeneral() {
		setBounds(100, 100, this.ancho, this.alto);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(ManejadorProperties.verRuta("ICONO")).getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(this.titulo);
		this.inicializarJFrame();
		setContentPane(panel);
	}
	
	/**
	 * Inicializa el JFrame
	 */
	public void inicializarJFrame() {
		this.panel = new JPanel();
		this.panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.panel.setLayout(null);
	}
}
