package com.tcs.CustomerInsuranceProjectBackup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tcs.CustomerInsuranceProjectBackup.model.CustomerAddress;
import com.tcs.CustomerInsuranceProjectBackup.model.CustomerInfo;
import com.tcs.CustomerInsuranceProjectBackup.model.CustomerInsurance;
import com.tcs.CustomerInsuranceProjectBackup.model.InsuranceDetail;
import com.tcs.CustomerInsuranceProjectBackup.model.LoginCustomer;
import com.tcs.CustomerInsuranceProjectBackup.model.Password;
import com.tcs.CustomerInsuranceProjectBackup.repository.CustomerInsuranceRepository;
import com.tcs.CustomerInsuranceProjectBackup.repository.InsuranceDetailRepository;
import com.tcs.CustomerInsuranceProjectBackup.security.JwtValidator;

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
		customerInfo.setPassword(customerInfo.getPassword());
		repository.save(customerInfo);
		return "Saved";
	}
	
	public String getCustomer(String token) {
		LoginCustomer loginCustomer = validator.validate(token);
		List<CustomerInfo> customerList=getCustomerDetail();
		for(int i=0;i<customerList.size();i++) {
			CustomerInfo customerInfo = customerList.get(i);
			if(customerInfo.getEmailId().equals(loginCustomer.getUsername())) {
				if(customerInfo.getPassword().equals(loginCustomer.getPassword())) {
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
				if(customerInfo.getPassword().equals(loginCustomer.getPassword())) {
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
		repository.save(customerDetail);
	}

	public void updatePassword(String token, Password password) {
		CustomerInfo customerDetail = validateCustomer(token);
		System.out.println(customerDetail.getEmailId());
		customerDetail.setPassword(password.getNewPassword());
	    repository.save(customerDetail);
	}
	
	public void deActivateCustomerAccount(String token) {
		CustomerInfo customerDetail = validateCustomer(token);
		customerDetail.setStatus("Deactivate");
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
