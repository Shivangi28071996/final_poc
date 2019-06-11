package com.tcs.CustomerInsuranceProject.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tcs.CustomerInsuranceProject.model.CustomerAddress;
import com.tcs.CustomerInsuranceProject.model.CustomerInfo;
import com.tcs.CustomerInsuranceProject.model.InsuranceDetail;
import com.tcs.CustomerInsuranceProject.model.LoginCustomer;
import com.tcs.CustomerInsuranceProject.repository.CustomerInsuranceRepository;
import com.tcs.CustomerInsuranceProject.repository.InsuranceDetailRepository;
import com.tcs.CustomerInsuranceProject.security.JwtValidator;

@Service
public class InsuranceDetailService {
	@Autowired
	private CustomerInsuranceRepository customerRepository;
	
	@Autowired
	private InsuranceDetailRepository insuranceRepository;
	
	@Autowired 
	private JwtValidator validator;

	/*Customer Related Operations*/
	
//	public String getAdmin(String token) {
//		return token;
//	}
	
	public boolean validateAdmin(String token) {
		String authenticationToken = token.substring(7);
		LoginCustomer loginCustomer=validator.validate(authenticationToken);
		if(loginCustomer.getUsername().equals("admin001")) {
			if(loginCustomer.getPassword().equals("admin123")) {
				return true;
			}
		}
		return false;
	}
	
	public List<CustomerInfo> getCustomerDetail(String token) {
		if(validateAdmin(token)) {
			return customerRepository.findAll();
		}
		return null;
		
	}

	public CustomerInfo getCustomerDetailById(String token,String customerId) {
		if(validateAdmin(token)) {
			Optional<CustomerInfo> customerDetail = customerRepository.findById(customerId);
			CustomerInfo customerInfo = customerDetail.get();
			return customerInfo;
		}
		return null;
	}

	public void updateCustomerDetailByAdministrator(String token,String customerId, CustomerInfo customerInfo) {
		if(validateAdmin(token)) {
			Optional<CustomerInfo> customerDetails = customerRepository.findById(customerId);
			CustomerInfo customerDetail = customerDetails.get();
			customerDetail.setAnnualIncome(customerInfo.getAnnualIncome());
			customerDetail.setCustomerName(customerInfo.getCustomerName());
			customerDetail.setEmailId(customerInfo.getEmailId());
			customerDetail.setMaritalStatus(customerInfo.getMaritalStatus());
			customerDetail.setMobileNo(customerInfo.getMobileNo());
			customerDetail.setOccupation(customerInfo.getOccupation());
			customerDetail.setPhone(customerInfo.getPhone());
			CustomerAddress customerAddress = new CustomerAddress();
			customerAddress.setAddress(customerInfo.getCustomerAddress().getAddress());
			customerAddress.setCity(customerInfo.getCustomerAddress().getCity());		
			customerAddress.setState(customerInfo.getCustomerAddress().getState());
			customerAddress.setPinCode(customerInfo.getCustomerAddress().getPinCode());
			customerDetail.setCustomerAddress(customerAddress);
			customerRepository.save(customerDetail);
		}
	}	
	
	public void deleteCustomerAccount(String token, String customerId) {
		if(validateAdmin(token)) {
			Optional<CustomerInfo> customerDetail = customerRepository.findById(customerId);
			CustomerInfo customerInfo = customerDetail.get();
			customerRepository.delete(customerInfo);
		}
	}
	
	public void activateCustomerAccount(String token, String customerId) {
		if(validateAdmin(token)) {
			Optional<CustomerInfo> customerDetail = customerRepository.findById(customerId);
			CustomerInfo customerInfo = customerDetail.get();
			if(customerInfo.getStatus().equals("Deactivate")) {
				customerInfo.setStatus("Active");
			}
			else {
				customerInfo.setStatus("Deactivate");
			}
			customerRepository.save(customerInfo);
		}
	}
	
	/*Insurance Related Operation*/	
	
	public List<InsuranceDetail> getAllInsuranceDetails(String token) {
		if(validateAdmin(token)) {
			return insuranceRepository.findAll();
		}
		return null;
	}

	public InsuranceDetail getInsuranceDetailById(String token,String insuranceId) {
		if(validateAdmin(token)) {
			Optional<InsuranceDetail> insuranceDetails = insuranceRepository.findById(insuranceId);
			InsuranceDetail insuranceInfo = insuranceDetails.get();
			return insuranceInfo;
		}
		return null;
	}
	
	public void createNewInsurance(String token, InsuranceDetail insuranceDetail) {
		if(validateAdmin(token)) {
			insuranceRepository.save(insuranceDetail);
		}
	}
	
	public void updateInsuranceDetail(String token,String insuranceId, InsuranceDetail insuranceDetail) {
		if(validateAdmin(token)) {
			Optional<InsuranceDetail> insuranceDetails = insuranceRepository.findById(insuranceId);
			InsuranceDetail insuranceInfo = insuranceDetails.get();
			insuranceInfo.setAmount(insuranceDetail.getAmount());
			insuranceInfo.setCoveragePeriod(insuranceDetail.getCoveragePeriod());
			insuranceRepository.save(insuranceInfo);
		}
	}

	public void deleteInsuranceDetail(String token,String insuranceId) {
		if(validateAdmin(token)) {
			Optional<InsuranceDetail> insuranceDetail = insuranceRepository.findById(insuranceId);
			InsuranceDetail insuranceInfo = insuranceDetail.get();
			insuranceRepository.delete(insuranceInfo);
		}
	}

		
}
