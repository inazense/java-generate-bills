package main;

import gui.MainMenu;
import utils.UserMessages;

public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainMenu frame = new MainMenu(900, 633, UserMessages.MAIN_TITLE);
	}
}
