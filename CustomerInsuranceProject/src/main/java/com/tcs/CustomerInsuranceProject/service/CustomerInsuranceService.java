package com.tcs.CustomerInsuranceProject.service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tcs.CustomerInsuranceProject.model.CustomerAddress;
import com.tcs.CustomerInsuranceProject.model.CustomerInfo;
import com.tcs.CustomerInsuranceProject.model.CustomerInsurance;
import com.tcs.CustomerInsuranceProject.model.InsuranceDetail;
import com.tcs.CustomerInsuranceProject.model.LoginCustomer;
import com.tcs.CustomerInsuranceProject.model.Password;
import com.tcs.CustomerInsuranceProject.repository.CustomerInsuranceRepository;
import com.tcs.CustomerInsuranceProject.repository.InsuranceDetailRepository;
import com.tcs.CustomerInsuranceProject.security.JwtValidator;

@Service
public class CustomerInsuranceService {
	
	@Autowired
	private CustomerInsuranceRepository repository;
	
	@Autowired 
	private JwtValidator validator;
	
	@Autowired
	private InsuranceDetailRepository insuranceRepository;
	
	public List<CustomerInfo> getCustomerDetail(){
		return repository.findAll();
	}
	
	public String addCustomerInfo(CustomerInfo customerInfo) {
		CustomerInfo customerDetail= new CustomerInfo();
		List<CustomerInfo> customerList=getCustomerDetail();
		int size=customerList.size();
		for(int i=0;i<size;i++) {
			customerDetail = customerList.get(i);
			if(customerDetail.getEmailId().equals(customerInfo.getEmailId())) {
				return "Exists";
			}
		}
		customerInfo.setStatus("Active");
		String encodedString=Base64.getEncoder().encodeToString(customerInfo.getPassword().getBytes());
		customerInfo.setPassword(encodedString);
		repository.save(customerInfo);
		return "Saved";
	}
	
	public String getCustomer(String token) {
		LoginCustomer loginCustomer = validator.validate(token);
		List<CustomerInfo> customerList=getCustomerDetail();
		for(int i=0;i<customerList.size();i++) {
			CustomerInfo customerInfo = customerList.get(i);
			byte[] decodedBytes=Base64.getDecoder().decode(customerInfo.getPassword());
			String decodedString = new String(decodedBytes);
			if(customerInfo.getEmailId().equals(loginCustomer.getUsername())) {
				if(decodedString.equals(loginCustomer.getPassword())){
					if(customerInfo.getStatus().equals("Active")) {
						return token;
					}
					else {
						return "Deactivate";
					}
				}
				else {
					return "Not Found";
				}
			}
		}
		return "Not registered";
	}
	
	public CustomerInfo validateCustomer(String token) {
		String authenticationToken = token.substring(7);
		LoginCustomer loginCustomer=validator.validate(authenticationToken);
		List<CustomerInfo> customerList=getCustomerDetail();
		for(int i=0;i<customerList.size();i++) {
			CustomerInfo customerInfo = customerList.get(i);
			if(customerInfo.getEmailId().equals(loginCustomer.getUsername())) {
				byte[] decodedBytes=Base64.getDecoder().decode(customerInfo.getPassword());
				String decodedString = new String(decodedBytes);
				if(decodedString.equals(loginCustomer.getPassword())){
					customerInfo.setPassword(decodedString);
					return customerInfo;
				}
			}
		}
		return null;
	}
	
	public CustomerInfo getCustomerById(String token){
		CustomerInfo customerInfo=validateCustomer(token);
		return customerInfo;
	}
	
	public void updateCustomerDetailByCustomer(String token, CustomerInfo customerInfo) {
		CustomerInfo customerDetail = validateCustomer(token);
		customerDetail.setAnnualIncome(customerInfo.getAnnualIncome());
		customerDetail.setCustomerName(customerInfo.getCustomerName());
		customerDetail.setEmailId(customerInfo.getEmailId());
		customerDetail.setMaritalStatus(customerInfo.getMaritalStatus());
		customerDetail.setMobileNo(customerInfo.getMobileNo());
		customerDetail.setOccupation(customerInfo.getOccupation());
		customerDetail.setPhone(customerInfo.getPhone());
		customerDetail.setDob(customerInfo.getDob());
		CustomerAddress customerAddress = new CustomerAddress();
		customerAddress.setAddress(customerInfo.getCustomerAddress().getAddress());
		customerAddress.setCity(customerInfo.getCustomerAddress().getCity());
		customerAddress.setState(customerInfo.getCustomerAddress().getState());
		customerAddress.setPinCode(customerInfo.getCustomerAddress().getPinCode());
		customerDetail.setCustomerAddress(customerAddress);
		String encodedString=Base64.getEncoder().encodeToString(customerInfo.getPassword().getBytes());
		customerDetail.setPassword(encodedString);
		repository.save(customerDetail);	
	}	
	
	public List<InsuranceDetail> getInsuranceDetails(String token) {
		CustomerInfo customerDetail = validateCustomer(token);
		if(customerDetail!=null) {
			return insuranceRepository.findAll();
		}
		return null;
	}
	
	public void addInsuranceToCustomer(String token, CustomerInsurance insurance) {
		CustomerInfo customerDetail = validateCustomer(token);
		List<Object> insuranceDetail=customerDetail.getCustomerInsurance();
		insuranceDetail.add(insurance);
		customerDetail.setCustomerInsurance(insuranceDetail);
		String encodedString=Base64.getEncoder().encodeToString(customerDetail.getPassword().getBytes());
		customerDetail.setPassword(encodedString);
		repository.save(customerDetail);
	}

	public void updatePassword(String token, Password password) {
		CustomerInfo customerDetail = validateCustomer(token);
		String encodedString=Base64.getEncoder().encodeToString(password.getNewPassword().getBytes());
		customerDetail.setPassword(encodedString);
	    repository.save(customerDetail);
	}
	
	public void deActivateCustomerAccount(String token) {
		CustomerInfo customerDetail = validateCustomer(token);
		customerDetail.setStatus("Deactivate");
		String encodedString=Base64.getEncoder().encodeToString(customerDetail.getPassword().getBytes());
		customerDetail.setPassword(encodedString);
		repository.save(customerDetail);
	}

	public InsuranceDetail getInsuranceDetailsById(String token, String insuranceId) {
		CustomerInfo customerDetail = validateCustomer(token);
		if(customerDetail!=null) {
			Optional<InsuranceDetail> insuranceDetails = insuranceRepository.findById(insuranceId);
			InsuranceDetail insuranceInfo = insuranceDetails.get();
			return insuranceInfo;
		}
		return null;
	}

	
	
}
