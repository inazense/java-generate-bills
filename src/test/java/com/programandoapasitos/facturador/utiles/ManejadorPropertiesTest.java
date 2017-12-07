package com.programandoapasitos.facturador.utiles;

import org.junit.Assert;
import org.junit.Test;

public class ManejadorPropertiesTest {
    
	@Test
    public void verLiteralTest()
    {
		String literal = ManejadorProperties.verLiteral("NEW");
		Assert.assertTrue(literal.equals("Nuevo"));
    }
	
	@Test
	public void verGuiTest() {	
		int gui = ManejadorProperties.verGui("SUBFRAMES_HEIGHT");
		Assert.assertTrue(gui == 500);
	}
	
	@Test
    public void verRutaTest()
    {
		String ruta = ManejadorProperties.verRuta("ICONO");
		Assert.assertTrue(ruta.equals("recursos/img/icon.png"));
    }
	
	@Test
	public void verPDFTest() {
		int pdf = ManejadorProperties.verPDF("SEPARADOR_X");
		Assert.assertTrue(pdf == 65);
	}
}
