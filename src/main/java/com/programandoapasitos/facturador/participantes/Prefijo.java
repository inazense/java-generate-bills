package com.programandoapasitos.facturador.participantes;

import java.util.Map;
import java.util.HashMap;

public class Prefijo {

	@SuppressWarnings("serial")
	public static final Map<String, String> INTERNACIONALES = new HashMap<String, String>() {
		{
			put("Alemania" 		, 	"+49");
			put("España" 		, 	"+34");
			put("Francia" 		, 	"+33");
			put("Gibraltar" 	, 	"+350");
			put("Grecia" 		, 	"+30");
			put("Irlanda" 		, 	"+353");
			put("Italia" 		, 	"+39");
			put("Reino Unido" 	, 	"+44");
			put("Rumania" 		, 	"+40");
			put("Paises Bajos" 	, 	"+41");
			put("Portugal" 		, 	"+351");
		}
	};
}
