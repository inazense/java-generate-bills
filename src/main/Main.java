package main;

import java.io.File;

import gui.MainMenu;
import utils.GeneralConfigurations;
import utils.UserMessages;

public class Main {

	public static void main(String[] args) {
		
		// Clean temporaly files before launch app
		File[] files = new File("temp/").listFiles();
		for (File file : files) {
			file.delete();
		}
		@SuppressWarnings("unused")
		MainMenu frame = new MainMenu(GeneralConfigurations.MAIN_FRAME_WIDTH, GeneralConfigurations.MAIN_FRAME_HEIGHT, UserMessages.MAIN_TITLE);
	}
}
