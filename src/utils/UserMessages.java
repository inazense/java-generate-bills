package utils;

public class UserMessages {
	
	// Generic
	public static final String SAVE_DATA 						= "Guardar";
		
	// Bill Exceptions
	public static final String MANDATORY_BILL_NUMBER 			= "Debes escribir un número de factura";
	public static final String MORE_THAN_0_PAYMENTS 			= "Necesitas agregar algún trabajo antes de realizar esta tarea";
	
	// Catch new client fields GUI Exceptions
	public static final String MANDATORY_CLIENT_NAME 			= "Debes escribir un nombre";
	public static final String MANDATORY_CLIENT_SURNAME 		= "Debes escribir un apellido";
	
	// Email Exceptions
	public static final String EMAIL_NOT_VALID 					= "El correo electrónico tiene un formato NO válido";
	
	// Neodatis Exceptions
	public static final String NEODATIS_NOT_CLOSED 				= "ERROR. La base de datos no está cerrada";
	public static final String NEODATIS_IS_CLOSED 				= "ERROR. La base de datos ya está cerrada";
	
	// Payment Exceptions
	public static final String EMPTY_PAYMENT_CONCEPT 			= "El pago no puede estar vacío"; 
	public static final String NEGATIVE_AMOUNT 					= "El precio no puede ser negativo";
	public static final String NO_AMOUNT 						= "Necesitas insertar un precio para este trabajo";
	
	// SQLite Exceptions
	public static final String FAIL_INSERT_CLIENT 				= "Error al guardar el cliente";
	public static final String FAIL_INSERT_EMAILS 				= "Error al guardar los correos electrónicos";
	public static final String FAIL_INSERT_PHONES 				= "Error al guardar los teléfonos";
	
	// Telephone Exceptions
	public static final String PHONE_MORE_THAN_15_CHARACTERS 	= "El teléfono tiene más de 15 caracteres";
	public static final String PHONE_NUMBER_NOT_NUMERIC 		= "El teléfono contiene caracteres no numéricos";
	public static final String PREFIX_FORMAT_NOT_VALID 			= "El formato del prefijo necesita comenzar con + y solo acepta números después de ese signo";
	
	// MainMenu Frame
	public static final String MAIN_TITLE 						= "MP Reformas - Facturas";
	public static final String MENU_FACTURAS 					= "Facturas";
	public static final String MENU_CLIENTES 					= "Clientes" ;
	public static final String MENU_ITEM_NEW_BILL	 			= "Nueva factura";
	public static final String MENU_ITEM_NEW_CLIENT 			= "Nuevo cliente";
	public static final String MENU_ITEM_SEARCH_BILL 			= "Buscar factura";
	public static final String MENU_ITEM_SEARCH_CLIENT 			= "Buscar cliente";
	public static final String MENU_ITEM_SEE_BILLS	 			= "Ver facturas";
	
	// NewClient Frame
	public static final String NEW_CLIENT_SURNAMES 				= "Apellidos:";
	public static final String NEW_CLIENT_NAME 					= "Nombre:";
	public static final String NEW_CLIENT_STREET 				= "Calle:";
	public static final String NEW_CLIENT_LOCALITY 				= "Localidad:";
	public static final String NEW_CLIENT_PROVINCE 				= "Provincia:";
	public static final String NEW_CLIENT_POSTAL_CODE 			= "Código postal:";
	public static final String NEW_CLIENT_ADD_PHONE 			= "Agregar teléfono";
	public static final String NEW_CLIENT_ADD_EMAIL 			= "Agregar correo electrónico";
	public static final String NEW_CLIENT_REMOVE_ITEM 			= "Borrar seleccionado";
	public static final String NEW_CLIENT_REMOVED_EMAIL 		= "Correo electrónico eliminado";
	public static final String NEW_CLIENT_REMOVED_PHONE 		= "Teléfono eliminado";
	public static final String NEW_CLIENT_ERROR_REMOVE 			= "No hay nada seleccionado para borrar";
	
	// MyDialog buttons
	public static final String DIALOG_OK_BUTTON 				= "Aceptar";
	public static final String DIALOG_CANCEL_BUTTON 			= "Cancelar";
	public static final String DIALOG_ADD_PHONE_TITLE 			= "Agregar teléfono";
	public static final String DIALOG_PREFIX 					= "Prefijo";
	public static final String DIALOG_PHONE 					= "Teléfono";
	public static final String DIALOG_EMAIL 					= "Correo electrónico";
}
