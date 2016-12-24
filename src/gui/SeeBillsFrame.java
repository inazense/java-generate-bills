package gui;

import SuperClasses.MyFrame;
import utils.UserMessages;

@SuppressWarnings("serial")
public class SeeBillsFrame extends MyFrame {

	// Properties
	
	// Constructor
	public SeeBillsFrame(int width, int height) {
		super(width, height);
		this.init();
	}
	
	// Methods
	public void init() {
		setTitle(UserMessages.MENU_ITEM_SEE_BILLS);
		setVisible(true);
	}
}
