package SuperClasses;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.UserMessages;

/**
 * Superclass used to create JDialogs
 * @author Inazio
 *
 */
@SuppressWarnings("serial")
public class MyDialog extends JDialog {

	// Properties
	protected JPanel contentPanel;
	protected JPanel buttonPane;
	protected JButton okButton;
	protected JButton cancelButton;
	protected int width;
	protected int height;
	protected String title;
	
	// Constructor
	public MyDialog(int width, int height, String title) {
		this.width 	= width;
		this.height = height;
		this.title = title;
		this.init();
		
	}
	
	// Methods
	
	/**
	 * Init general characteristics
	 */
	private void init() {
		setBounds(100, 100, width, height);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("resources/icon.png").getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(this.title);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		this.initJPanels();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Init JFrame
	 */
	protected void initJPanels() {
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		{
			// Second JPanel
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton(UserMessages.DIALOG_OK_BUTTON);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton(UserMessages.DIALOG_CANCEL_BUTTON);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
}
