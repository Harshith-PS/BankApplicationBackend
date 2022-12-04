package com.app.dto;

public class AddressDTO {
	
	private Long addressId;
	private String city;
	private String street;
	
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	@Override
	public String toString() {
		return "AddressDTO [addressId=" + addressId + ", street=" + street + ", city=" + city + "]";
	}
}
	

