package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.GeneralConfigurations;
import utils.UserMessages;

@SuppressWarnings("serial")
public class DialogEditClient extends JDialog {

	// Properties
	private JPanel contentPane;
	private JPanel buttonPane;
	private JButton btnOk;
	private JButton btnCancel;
	private int clientCode;
	
	// Constructor
	public DialogEditClient(int clientCode) {
		this.clientCode = clientCode;
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
		setTitle(UserMessages.CLIENT_EDIT);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		this.initJPanels();
		getContentPane().add(contentPane, BorderLayout.CENTER);
		setVisible(true);
	}
	
	/**
	 * Initialize panels of dialog
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
				btnOk = new JButton(UserMessages.DIALOG_OK_BUTTON);
				btnOk.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						// TODO Implement Action
						
					}
				});
				btnOk.setActionCommand("OK");
				buttonPane.add(btnOk);
			}
			{
				btnCancel = new JButton(UserMessages.DIALOG_CANCEL_BUTTON);
				btnCancel.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						// TODO Implement dispose action
						
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
		// TODO Implements initialization of components
	}
	
}
