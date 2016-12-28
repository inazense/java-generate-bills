package main;

import gui.MainMenu;
import utils.GeneralConfigurations;
import utils.UserMessages;

public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainMenu frame = new MainMenu(GeneralConfigurations.MAIN_FRAME_WIDTH, GeneralConfigurations.MAIN_FRAME_HEIGHT, UserMessages.MAIN_TITLE);
	}
}
