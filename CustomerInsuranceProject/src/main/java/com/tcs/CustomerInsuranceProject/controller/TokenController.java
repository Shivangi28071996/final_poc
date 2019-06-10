package com.tcs.CustomerInsuranceProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.tcs.CustomerInsuranceProject.model.CustomerInfo;
import com.tcs.CustomerInsuranceProject.model.LoginCustomer;
import com.tcs.CustomerInsuranceProject.security.JwtGenerator;
import com.tcs.CustomerInsuranceProject.service.CustomerInsuranceService;

@RestController
public class TokenController {
	
	@Autowired
	private CustomerInsuranceService service;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PostMapping("/token")
	public String generate(@RequestBody LoginCustomer loginCustomer) {
		String token=jwtGenerator.generate(loginCustomer);
		return service.getCustomer(token);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PostMapping("/createCustomer")
	public String addCustomerInfo(@RequestBody CustomerInfo customerInfo) {
		return service.addCustomerInfo(customerInfo);
	}
}



