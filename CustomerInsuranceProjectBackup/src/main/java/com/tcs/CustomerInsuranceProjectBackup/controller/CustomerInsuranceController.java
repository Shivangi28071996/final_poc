package com.tcs.CustomerInsuranceProjectBackup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tcs.CustomerInsuranceProjectBackup.model.CustomerInfo;
import com.tcs.CustomerInsuranceProjectBackup.model.CustomerInsurance;
import com.tcs.CustomerInsuranceProjectBackup.model.InsuranceDetail;
import com.tcs.CustomerInsuranceProjectBackup.model.Password;
import com.tcs.CustomerInsuranceProjectBackup.service.CustomerInsuranceService;

@RestController
@RequestMapping("/customer")
public class CustomerInsuranceController{
	
	@Autowired
	private CustomerInsuranceService service;
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getCustomerById")
	public CustomerInfo getCustomerById(@RequestHeader("Authorization") String token) {
		return service.getCustomerById(token);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateCustomerDetailByCustomer")
	public void updateCustomerDetailByCustomer(@RequestHeader("Authorization") String token,@RequestBody CustomerInfo customerInfo ) {
		System.out.println(customerInfo.getEmailId());
		service.updateCustomerDetailByCustomer(token,customerInfo);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getInsuranceDetails")
	public List<InsuranceDetail> getInsuranceDetails(@RequestHeader("Authorization") String token) {
		return service.getInsuranceDetails(token);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getInsuranceDetailsById")
	public InsuranceDetail getInsuranceDetailsById(@RequestHeader("Authorization") String token,@RequestHeader("insuranceId") String insuranceId) {
		return service.getInsuranceDetailsById(token,insuranceId);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/addInsurance")
	public void addInsuranceToCustomer(@RequestHeader("Authorization") String token,@RequestBody CustomerInsurance insurance ) {
		service.addInsuranceToCustomer(token,insurance);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updatePassword")
	public void updatePassword(@RequestHeader("Authorization") String token,@RequestBody Password password) {
		service.updatePassword(token,password);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deActivateCustomerAccount")
	public void deActivateCustomerAccount(@RequestHeader("Authorization") String token ) {
		service.deActivateCustomerAccount(token);
	}
	
}
	

	
	
	

