package com.tcs.CustomerInsuranceProject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public List<CustomerInfo> getCustomerDetail() {	
//		System.out.println("getCustomerDetail  :  application is up on port : " + port);
		return service.getCustomerDetail();
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getCustomerDetailById/{customerId}")
	public CustomerInfo getCustomerDetailById(@PathVariable("customerId") String customerId) {
		return service.getCustomerDetailById(customerId);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateCustomerDetailByAdministrator/{customerId}")
	public void updateCustomerDetailByAdministrator(@PathVariable("customerId") String customerId,@RequestBody CustomerInfo customerInfo) {	
		service.updateCustomerDetailByAdministrator(customerId,customerInfo);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/activateCustomerAccount/{customerId}")
	public void activateCustomerAccount(@PathVariable("customerId") String customerId) {
		service.activateCustomerAccount(customerId);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deleteCustomerAccount/{customerId}")
	public void deleteCustomerAccount(@PathVariable("customerId") String customerId) {
		service.deleteCustomerAccount(customerId);
	}
	
	
	
	/*Insurance Related Operation*/	
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getAllInsuranceDetails")
	public List<InsuranceDetail> getAllInsuranceDetails() {	
		return service.getAllInsuranceDetails();
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getInsuranceDetailById/{insuranceId}")
	public InsuranceDetail getInsuranceDetailById(@PathVariable("insuranceId") String insuranceId) {
		return service.getInsuranceDetailById(insuranceId);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")	
	@PostMapping("/createNewInsurance")
	public String createNewInsurance(@RequestBody InsuranceDetail insuranceDetail) {
		service.createNewInsurance(insuranceDetail);
		return "Insurance Added to the list";
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateInsuranceDetail/{insuranceId}")
	public void updateInsuranceDetail(@PathVariable("insuranceId") String insuranceId,@RequestBody InsuranceDetail insuranceDetail) {
		service.updateInsuranceDetail(insuranceId,insuranceDetail);
	}
	
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deleteInsuranceDetail/{insuranceId}")
	public void deleteInsuranceDetail(@PathVariable("insuranceId") String insuranceId ) {
		service.deleteInsuranceDetail(insuranceId);
	}
	
}
