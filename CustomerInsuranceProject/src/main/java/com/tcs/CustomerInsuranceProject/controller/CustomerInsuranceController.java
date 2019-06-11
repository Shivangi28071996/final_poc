package com.tcs.CustomerInsuranceProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.CustomerInsuranceProject.model.CustomerInfo;
import com.tcs.CustomerInsuranceProject.model.CustomerInsurance;
import com.tcs.CustomerInsuranceProject.model.LoginCustomer;
import com.tcs.CustomerInsuranceProject.model.Password;
import com.tcs.CustomerInsuranceProject.service.CustomerInsuranceService;

@RestController
public class CustomerInsuranceController{
	
	@Autowired
	private CustomerInsuranceService service;
	
//	@Value("${server.port}")
//	private String port;
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PostMapping("/loginCustomer")
	public String loginCustomer(@RequestBody LoginCustomer loginCustomer) {
//		System.out.println("loginCustomer  :  application is up on port : " + port);
		return service.loginCustomer(loginCustomer);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PostMapping("/createCustomer")
	public String addCustomerInfo(@RequestBody CustomerInfo customerInfo) {
		return service.addCustomerInfo(customerInfo);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateCustomerDetailByCustomer/{customerId}")
	public void updateCustomerDetailByCustomer(@PathVariable("customerId") String customerId,@RequestBody CustomerInfo customerInfo ) {
		service.updateCustomerDetailByCustomer(customerId,customerInfo);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/addInsurance/{customerId}")
	public void addInsuranceToCustomer(@PathVariable("customerId") String customerId,@RequestBody CustomerInsurance insurance ) {
		service.addInsuranceToCustomer(customerId,insurance);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updatePassword/{customerId}")
	public void updatePassword(@PathVariable("customerId") String customerId,@RequestBody Password password) {
		service.updatePassword(customerId,password);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deActivateCustomerAccount/{customerId}")
	public void deActivateCustomerAccount(@PathVariable("customerId") String customerId ) {
		service.deActivateCustomerAccount(customerId);
	}
	
}
	

	
	
	

