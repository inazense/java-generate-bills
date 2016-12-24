package participants;

public class Address {

	// Properties
	private String street;
	private String postalCode;
	private String locality;
	private String province;
	
	// Constructors
	public Address() {}
	
	public Address(String street, String postalCode, String locality, String province) {
		this.street = street;
		this.postalCode = postalCode;
		this.locality = locality;
		this.province = province;
	}

	// Getters and setters
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
}
