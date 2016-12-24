package gui;

import SuperClasses.MyFrame;

@SuppressWarnings("serial")
public class NewClientFrame extends MyFrame {

	// Properties
	
	// Constructor
	public NewClientFrame(int width, int height, String title) {
		super(width, height, title);
		this.init();
	}
	
	// Methods
	public void init() {
		setVisible(true);
	}
}
