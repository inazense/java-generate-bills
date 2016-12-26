package main;

import gui.MainMenu;
import utils.GeneralConfigurations;
import utils.UserMessages;

public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainMenu frame = new MainMenu(GeneralConfigurations.WIDTH_MAIN_FRAME, GeneralConfigurations.HEIGTH_MAIN_FRAME, UserMessages.MAIN_TITLE);
	}
}
