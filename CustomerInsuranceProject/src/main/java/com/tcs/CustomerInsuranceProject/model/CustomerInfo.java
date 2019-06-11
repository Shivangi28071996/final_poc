package com.tcs.CustomerInsuranceProject.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="CustomerDetail")
public class CustomerInfo {
	@Id
	private String customerId;
	private String customerName;
	private String gender;
	private long phone;
	private long mobileNo;
	private String dob;
	private String occupation;
	private long annualIncome;
	private String pan;
	private String nationality;
	private String emailId;
	private String password;
	private String maritalStatus;
	private String status;
	private CustomerAddress customerAddress;
	private List<Object> customerInsurance=new ArrayList<Object>();
	
	public CustomerInfo() {
		super();
	}
	
	public CustomerInfo(String customerId, String customerName, String gender, long phone, long mobileNo, String dob,
			String occupation, long annualIncome, String pan, String nationality, String emailId, String password,
			String maritalStatus, String status, CustomerAddress customerAddress) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.gender = gender;
		this.phone = phone;
		this.mobileNo = mobileNo;
		this.dob = dob;
		this.occupation = occupation;
		this.annualIncome = annualIncome;
		this.pan = pan;
		this.nationality = nationality;
		this.emailId = emailId;
		this.password = password;
		this.maritalStatus = maritalStatus;
		this.status = status;
		this.customerAddress = customerAddress;
	}
	
	public CustomerInfo(String customerName, long phone, long mobileNo, String occupation, long annualIncome,
			String emailId, String password, String maritalStatus, CustomerAddress customerAddress) {
		super();
		this.customerName = customerName;
		this.phone = phone;
		this.mobileNo = mobileNo;
		this.occupation = occupation;
		this.annualIncome = annualIncome;
		this.emailId = emailId;
		this.password = password;
		this.maritalStatus = maritalStatus;
		this.customerAddress = customerAddress;
	}

	public String getCustomerId() {
		return customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public long getAnnualIncome() {
		return annualIncome;
	}
	public void setAnnualIncome(long annualIncome) {
		this.annualIncome = annualIncome;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public CustomerAddress getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(CustomerAddress customerAddress) {
		this.customerAddress = customerAddress;
	}
	public List<Object> getCustomerInsurance() {
		return customerInsurance;
	}
	public void setCustomerInsurance(List<Object> customerInsurance) {
		this.customerInsurance = customerInsurance;
	}
	
	
}
