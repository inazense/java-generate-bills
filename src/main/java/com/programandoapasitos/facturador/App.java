package com.programandoapasitos.facturador;

import java.io.File;

import com.programandoapasitos.facturador.gui.MenuPrincipal;
import com.programandoapasitos.facturador.utiles.ManejadorProperties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	// Clean temporaly files before launch app
		File[] files = new File(ManejadorProperties.verRuta("PDF_TMP")).listFiles();
		for (File file : files) {
			file.delete();
		}
		@SuppressWarnings("unused")
		MenuPrincipal frame = new MenuPrincipal(ManejadorProperties.verGui("MAIN_FRAME_WIDTH"), ManejadorProperties.verGui("MAIN_FRAME_HEIGHT"), ManejadorProperties.verLiteral("MAIN_TITLE"));
    }
}
