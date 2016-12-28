package SuperClasses;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Superclass used to create JFrames
 * @author Inazio
 *
 */
@SuppressWarnings("serial")
public class MyFrame extends JFrame {

	// Properties
	protected JPanel contentPane;
	protected int width;
	protected int heigth;
	protected String title;
	
	// Constructor
	public MyFrame(int width, int height, String title) {
		this.width = width;
		this.heigth = height;
		this.title = title;
		this.init();
	}
	
	// Methods
	
	/**
	 * Init general characteristics
	 */
	private void init() {
		setBounds(100, 100, this.width, this.heigth);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("resources/icon.png").getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(this.title);
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
