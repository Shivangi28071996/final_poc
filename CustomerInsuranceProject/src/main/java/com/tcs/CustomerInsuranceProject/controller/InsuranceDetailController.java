package com.tcs.CustomerInsuranceProject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tcs.CustomerInsuranceProject.model.CustomerInfo;
import com.tcs.CustomerInsuranceProject.model.InsuranceDetail;
import com.tcs.CustomerInsuranceProject.service.InsuranceDetailService;

@RestController
@RequestMapping("/admin")
public class InsuranceDetailController {
	
	@Autowired
	private InsuranceDetailService service;
	
//	@Value("${server.port}")
//	private String port;

	/*Customer Related Operation*/	
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getAllCustomer")
	public List<CustomerInfo> getCustomerDetail(@RequestHeader("Authorization") String token) {	
//		System.out.println("getCustomerDetail  :  application is up on port : " + port);
		return service.getCustomerDetail(token);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getCustomerDetailById")
	public CustomerInfo getCustomerDetailById(@RequestHeader("Authorization") String token,@RequestHeader("customerId") String customerId) {
		return service.getCustomerDetailById(token,customerId);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateCustomerDetailByAdministrator")
	public void updateCustomerDetailByAdministrator(@RequestHeader("Authorization") String token,@RequestHeader("customerId") String customerId,@RequestBody CustomerInfo customerInfo) {	
		service.updateCustomerDetailByAdministrator(token,customerId,customerInfo);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/activateCustomerAccount")
	public void activateCustomerAccount(@RequestHeader("Authorization") String token,@RequestHeader("customerId") String customerId) {
		service.activateCustomerAccount(token,customerId);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deleteCustomerAccount")
	public void deleteCustomerAccount(@RequestHeader("Authorization") String token,@RequestHeader("customerId") String customerId) {
		service.deleteCustomerAccount(token,customerId);
	}
	
	
	
	/*Insurance Related Operation*/	
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getAllInsuranceDetails")
	public List<InsuranceDetail> getAllInsuranceDetails(@RequestHeader("Authorization") String token) {	
		return service.getAllInsuranceDetails(token);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getInsuranceDetailById")
	public InsuranceDetail getInsuranceDetailById(@RequestHeader("Authorization") String token,@RequestHeader("insuranceId") String insuranceId) {
		return service.getInsuranceDetailById(token,insuranceId);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")	
	@PostMapping("/createNewInsurance")
	public String createNewInsurance(@RequestHeader("Authorization") String token,@RequestBody InsuranceDetail insuranceDetail) {
		service.createNewInsurance(token,insuranceDetail);
		return "Insurance Added to the list";
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateInsuranceDetail")
	public void updateInsuranceDetail(@RequestHeader("Authorization") String token,@RequestHeader("insuranceId") String insuranceId,@RequestBody InsuranceDetail insuranceDetail) {
		service.updateInsuranceDetail(token,insuranceId,insuranceDetail);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deleteInsuranceDetail")
	public void deleteInsuranceDetail(@RequestHeader("Authorization") String token,@RequestHeader("insuranceId") String insuranceId) {
		service.deleteInsuranceDetail(token,insuranceId);
	}
	
}
