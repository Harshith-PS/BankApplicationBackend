package com.app.entity;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {

	@Id
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getAddressId() == null) ? 0 : this.getAddressId().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (this.getAddressId() == null) {
			if (other.getAddressId() != null)
				return false;
		}else if (!this.getAddressId().equals(other.getAddressId()))
			return false;
		return true;
	}
}
