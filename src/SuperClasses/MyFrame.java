package SuperClasses;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MyFrame extends JFrame {

	// Properties
	protected JPanel contentPane;
	protected int width;
	protected int heigth;
	
	// Constructor
	public MyFrame(int width, int height) {
		this.width = width;
		this.heigth = height;
		this.init();
	}
	// Methods
	
	/**
	 * Init general characteristics
	 */
	private void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, this.width, this.heigth);
		setIconImage(new ImageIcon("resources/icon.png").getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		this.initJFrame();
		setContentPane(contentPane);
		
	}
	
	/**
	 * Init JFrame
	 */
	private void initJFrame() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
	}
}
