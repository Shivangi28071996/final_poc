package com.tcs.CustomerInsuranceProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.CustomerInsuranceProject.model.CustomerAddress;
import com.tcs.CustomerInsuranceProject.model.CustomerInfo;
import com.tcs.CustomerInsuranceProject.model.CustomerInsurance;
import com.tcs.CustomerInsuranceProject.model.LoginCustomer;
import com.tcs.CustomerInsuranceProject.model.Password;
import com.tcs.CustomerInsuranceProject.repository.CustomerInsuranceRepository;

@Service
public class CustomerInsuranceService {
	
	@Autowired
	private CustomerInsuranceRepository repository;
	
	@Autowired
	private InsuranceDetailService service;
	
	public String loginCustomer(LoginCustomer loginCustomer) {
		CustomerInfo customerInfo = new CustomerInfo();
		List<CustomerInfo> customerList=service.getCustomerDetail();
		for(int i=0;i<customerList.size();i++) {
			customerInfo = customerList.get(i);
			if(customerInfo.getEmailId().equals(loginCustomer.getUsername())) {
				if(customerInfo.getPassword().equals(loginCustomer.getPassword())) {
					if(customerInfo.getStatus().equals("Active")) {
						return customerInfo.getCustomerId();
					}
					else {
						return "Deactivate";
					}
				}
			}
		}
		return "Not Found";
	}
	
	public String addCustomerInfo(CustomerInfo customerInfo) {
		CustomerInfo customerDetail= new CustomerInfo();
		List<CustomerInfo> customerList=service.getCustomerDetail();
		int size=customerList.size();
		for(int i=0;i<size;i++) {
			customerDetail = customerList.get(i);
			if(customerDetail.getEmailId().equals(customerInfo.getEmailId())) {
				return "Exists";
			}
		}
		customerInfo.setStatus("Active");
		repository.save(customerInfo);
		return "Saved";
	}
	
	public void updateCustomerDetailByCustomer(String customerId, CustomerInfo customerInfo) {
		Optional<CustomerInfo> customerDetails = repository.findById(customerId);
		CustomerInfo customerDetail = customerDetails.get();
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
	
	public void addInsuranceToCustomer(String customerId, CustomerInsurance insurance) {
		Optional<CustomerInfo> customerDetail = repository.findById(customerId);
		CustomerInfo customerInfo = customerDetail.get();
		List<Object> insuranceDetail=customerInfo.getCustomerInsurance();
		insuranceDetail.add(insurance);
		customerInfo.setCustomerInsurance(insuranceDetail);
		repository.save(customerInfo);
	}
	
	public void updatePassword(String customerId, Password password) {
		Optional<CustomerInfo> customerDetail = repository.findById(customerId);
		CustomerInfo customerInfo = customerDetail.get();
		customerInfo.setPassword(password.getNewPassword());
		repository.save(customerInfo);
//		if(customerInfo.getPassword().equals(password.getOldPassword())){
//			if(password.getNewPassword().equals(password.getConfirmPassword())) {
//				customerInfo.setPassword(password.getNewPassword());
//				repository.save(customerInfo);
//				return "updated";
//			}
//			return "not matched";
//		}
//		return "wrong password";
	}
	

	public void deActivateCustomerAccount(String customerId) {
		Optional<CustomerInfo> customerDetail = repository.findById(customerId);
		CustomerInfo customerInfo = customerDetail.get();
		customerInfo.setStatus("Deactivate");
		repository.save(customerInfo);
	}
}
