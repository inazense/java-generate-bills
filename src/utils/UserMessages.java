package utils;

public class UserMessages {
	
	// Telephone Exceptions
	public static final String PHONE_MORE_THAN_15_CHARACTERS 	= "Phone have more than 15 characters";
	public static final String PHONE_NUMBER_NOT_NUMERIC 		= "Phone number is not numeric";
	public static final String PREFIX_FORMAT_NOT_VALID 			= "Prefix format needs to start with + and only accept numbers before that";
	
	// Email Exceptions
	public static final String EMAIL_NOT_VALID 					= "Email is not in a valid format";
	
	// Payment Exceptions
	public static final String NO_AMOUNT 						= "You need to insert an amount for this work";
	public static final String NEGATIVE_AMOUNT 					= "Amount cannot be negative";
	public static final String EMPTY_PAYMENT_CONCEPT 			= "Payment cannot be empty"; 
	
	// Bill Exceptions
	public static final String MORE_THAN_0_PAYMENTS 			= "You need to add a payment before that action";
	public static final String MANDATORY_BILL_NUMBER 			= "You must to write a bill number"; 
	
	// MainMenu Frame
	public static final String MAIN_TITLE 						= "MP Reformas - Facturas";
	public static final String MENU_FACTURAS 					= "Facturas";
	public static final String MENU_ITEM_SEE_BILLS	 			= "Ver facturas";
	public static final String MENU_ITEM_NEW_BILL	 			= "Nueva factura";
	public static final String MENU_ITEM_SEARCH_BILL 			= "Buscar factura";
}
