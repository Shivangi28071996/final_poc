package com.tcs.CustomerInsuranceProject.model;

public class CustomerAddress {
	private String address;
	private String city;
	private String state;
	private int pinCode;
	
	public CustomerAddress(String address,String city, String state, int pinCode) {
		super();
		this.address = address;
		this.city = city;
		this.state = state;
		this.pinCode = pinCode;
	}
	
	public CustomerAddress() {
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getPinCode() {
		return pinCode;
	}
	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}
	
	
}
