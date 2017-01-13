package utils;

public class UserMessages {
	
	// Generic
	public static final String SAVE_DATA 						= "Guardar";
	public static final String SEARCH 							= "Buscar";
	public static final String CURRENCY_SYMBOL 					= "€";
	public static final String PERCENT 							= "%";
	public static final String NEW 								= "Nuevo";
	public static final String REMOVE_ITEM 						= "Borrar seleccionado";
	public static final String CLOSE 							= "Cerrar";
		
	// Exceptions
	public static final String VAT_EXCEPTION 					= "Debes rellenar el campo del IVA";
	public static final String NUM_DATE_EXCEPTION 				= "El formato de la fecha debe ser dd/mm/yyyy";
	
	// Bill Exceptions
	public static final String MANDATORY_BILL_NUMBER 			= "Debes escribir un número de factura";
	public static final String MORE_THAN_0_PAYMENTS 			= "Necesitas agregar algún trabajo antes de realizar esta tarea";
	public static final String INVALID_DATE 					= "Fecha invalida";
	
	// Catch fields GUI Exceptions
	public static final String MANDATORY_CLIENT_NAME 			= "Debes escribir un nombre";
	public static final String MANDATORY_CLIENT_SURNAME 		= "Debes escribir un apellido";
	public static final String MANDATORY_SELECT_A_CLIENT 		= "Debes elegir un cliente al que facturar";
	public static final String MANDATORY_SELECT_A_DATE 			= "Debes escribir una fecha";
	public static final String MANDATORY_PAYMENTS 				= "No hay nada que facturar";
	
	// Email Exceptions
	public static final String EMAIL_NOT_VALID 					= "El correo electrónico tiene un formato NO válido";
	
	// NumberFormat Exceptions
	public static final String IS_NOT_DOUBLE 					= "El importe debe ser un número";
	
	// Payment Exceptions
	public static final String EMPTY_PAYMENT_CONCEPT 			= "El concepto no puede estar vacío"; 
	public static final String NEGATIVE_AMOUNT 					= "El precio no puede ser negativo";
	public static final String NO_AMOUNT 						= "Necesitas insertar un precio para este trabajo";
	
	// SQLite Exceptions
	public static final String FAIL_INSERT_CLIENT 				= "Error al guardar el cliente";
	public static final String FAIL_INSERT_EMAILS 				= "Error al guardar los correos electrónicos";
	public static final String FAIL_INSERT_PHONES 				= "Error al guardar los teléfonos";
	public static final String FAIL_LOAD_CLIENTS 				= "Error al cargar el listado de clientes";
	public static final String FAIL_LOAD_FILTERS 				= "Error al cargar el listado de clientes con los filtros";
	public static final String FAIL_LOAD_CLIENT_FIELDS 			= "Error al cargar los datos del cliente";
	public static final String FAIL_SAVE_BILLS 					= "Error al guardar la factura";
	public static final String FAIL_SAVE_PAYMENTS 				= "Error al guardar los conceptos a cobrar";
	public static final String FAIL_LOAD_BILLS 					= "Error al cargar el listado de facturas";
	public static final String FAIL_LOAD_BILLS_FILTERS 			= "Error al cargar el listado de facturas con los filtros";
	public static final String FAIL_LOAD_ONE_BILL 				= "Error al cargar la factura";
	
	// Telephone Exceptions
	public static final String PHONE_MORE_THAN_15_CHARACTERS 	= "El teléfono tiene más de 15 caracteres";
	public static final String PHONE_NUMBER_NOT_NUMERIC 		= "El teléfono contiene caracteres no numéricos";
	public static final String PREFIX_FORMAT_NOT_VALID 			= "El formato del prefijo necesita comenzar con + y solo acepta números después de ese signo";
	
	// PdfGenerator
	public static final String FAIL_CREATE_PDF 					= "Error al generar la factura en PDF";
	public static final String CREATE_PDF 						= "Factura generada correctamente";
	
	// PdfPrinter
	public static final String FAIL_PRINT_PDF 					= "Error al imprimir la factura";
	public static final String PRINT_PDF 						= "Factura mandada a imprimir";
	
	// MainMenu Frame
	public static final String MAIN_TITLE 						= "MP Reformas - Facturas";
	public static final String MENU_FACTURAS 					= "Facturas";
	public static final String MENU_CLIENTES 					= "Clientes" ;
	public static final String MENU_ITEM_NEW_BILL	 			= "Nueva factura";
	public static final String MENU_ITEM_NEW_CLIENT 			= "Nuevo cliente";
	public static final String MENU_ITEM_SEARCH_BILL 			= "Buscar factura";
	public static final String MENU_ITEM_SEARCH_CLIENT 			= "Buscar cliente";
	
	// NewBill Frame
	public static final String NEW_BILL_ADD_PAYMENT 			= "Agregar pago";
	public static final String NEW_BILL_ADD_VAT 				= "Agregar IVA";
	public static final String NEW_BILL_AMOUNT 					= "Importe";
	public static final String NEW_BILL_CLIENT 					= "Cliente:";
	public static final String NEW_BILL_CONCEPT 				= "Concepto";
	public static final String NEW_BILL_DATE 					= "Fecha:";
	public static final String NEW_BILL_NUMBER 					= "Factura Nº:";
	public static final String NEW_BILL_VIEW_CLIENT 			= "Ver ficha";
	public static final String NEW_BILL_TO_PDF 					= "¡A PDF!";
	public static final String NEW_BILL_TO_PRINT 				= "Imprimir";
	public static final String NEW_BILL_TOTAL 					= "Total: ";
	public static final String NEW_BILL_VAT 					= "IVA";
	public static final String NEW_BILL_WITHOUT_VAT 			= "IVA no aplicado";
	public static final String NEW_BILL_FAIL_SELECT_CLIENT 		= "Debes elegir algún cliente antes";
	public static final String NEW_BILL_SAVED 					= "Factura guardada correctamente";
	public static final String NEW_BILL_FAIL_SAVED 				= "Error al guardar la factura";
	public static final String NEW_BILL_NO_ROW_SELECTED 		= "No hay ningún pago seleccionado";
	
	// NewClient Frame
	public static final String NEW_CLIENT_SURNAMES 				= "Apellidos:";
	public static final String NEW_CLIENT_NAME 					= "Nombre:";
	public static final String NEW_CLIENT_STREET 				= "Calle:";
	public static final String NEW_CLIENT_LOCALITY 				= "Localidad:";
	public static final String NEW_CLIENT_PROVINCE 				= "Provincia:";
	public static final String NEW_CLIENT_POSTAL_CODE 			= "Código postal:";
	public static final String NEW_CLIENT_ADD_PHONE 			= "Agregar teléfono";
	public static final String NEW_CLIENT_ADD_EMAIL 			= "Agregar correo electrónico";
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
	
	// SearchBill Frame
	public static final String BILL_LIST 						= "Listado de facturas";
	public static final String BILL_LIST_INSTRUCTIONS 			= "(Doble clic a la factura del cliente para verla y editarla)";
	public static final String BILL_NUMBER 						= "Nº factura";
	public static final String BILL_DATE 						= "Fecha de factura";
	public static final String BILL_DATE_TABLE 					= "Fecha";
	public static final String BILL_CLIENT 						= "Cliente";
	
	// SeachClient Frame
	public static final String CLIENT_LIST_INSTRUCTIONS 		= "(Doble clic al nombre del cliente para verlo y editarlo)";
	public static final String CLIENT_LIST 						= "Listado de clientes";
	public static final String CLIENT_PHONE 					= "Teléfono:";
	public static final String CLIENT_EMAIL 					= "Email:";
	public static final String MORE_THAN_ONE_FILTER				= "La versión actual del programa solo soporta un filtro por cada búsqueda.\nEstamos trabajando en ello";
	
	// EditClient Dialog
	public static final String CLIENT_EDIT 						= "Editar cliente";
	
	// EditBill Dialog
	public static final String BILL_EDIT 						= "Ver factura";
	public static final String BILL_DELETE 						= "Borrar factura";
	public static final String BILL_DELETED 					= "Factura borrada";
	public static final String BILL_FAIL_DELETE 				= "Error al borrar la factura";
	public static final String BILL_FAIL_PAYMENTS 				= "Error al cargar los pagos";
}
