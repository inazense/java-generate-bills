package gui;

import SuperClasses.MyFrame;
import utils.UserMessages;

@SuppressWarnings("serial")
public class NewBillFrame extends MyFrame {

	// Properties
	
	// Constructor
	public NewBillFrame(int width, int height) {
		super(width, height);
		this.init();
	}
	
	// Methods
	public void init() {
		setTitle(UserMessages.MENU_ITEM_NEW_BILL);
		setVisible(true);
	}
}
