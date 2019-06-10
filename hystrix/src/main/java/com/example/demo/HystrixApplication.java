package com.example.demo;

import org.apache.tomcat.util.json.JSONParser;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication(scanBasePackages= {"com.example.demo"})
@EnableEurekaClient
@RestController
@EnableCircuitBreaker
@EnableHystrix
public class HystrixApplication {
	
	@Autowired
	private RestTemplate template;
	
	/* CustomerInsuranceController*/
	
	@HystrixCommand(fallbackMethod="fallBackLoginCustomer")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/loginCustomer")
	public String loginCustomer(@RequestBody Object loginCustomer) {
		try {
			String url="http://customerinsuranceproject/loginCustomer";
			return template.getForObject(url,String.class,loginCustomer);
		}catch(Exception e) {
			System.out.println(e);
			throw new RuntimeException();
		}
	}
	
	public String fallBackLoginCustomer(@RequestBody Object loginCustomer) {
		String url="http://customerinsuranceproject-backup/customer/loginCustomer";
		return template.postForObject(url,loginCustomer, String.class);
	}
	
	@HystrixCommand(fallbackMethod="fallBackAddCustomerInfo")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PostMapping("/createCustomer")
	public String addCustomerInfo(@RequestBody Object customerInfo) {
		try {
			String url="http://customerinsuranceproject/createCustomer";
			return template.postForObject(url,customerInfo, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	} 
	
	public String fallBackAddCustomerInfo(@RequestBody Object customerInfo) {
		String url="http://customerinsuranceproject-backup/createCustomer";
		return template.postForObject(url,customerInfo, String.class);
	} 
	
	@HystrixCommand(fallbackMethod="fallBackUpdateCustomerDetailByCustomer")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateCustomerDetailByCustomer/{customerId}")		
	public String updateCustomerDetailByCustomer(@PathVariable("customerId") String customerId,@RequestBody Object customerInfo ) {
		try {
			String url="http://customerinsuranceproject/updateCustomerDetailByCustomer/{customerId}";
			template.put(url, customerInfo, customerId);
			return "Details updated succesfully";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}

	public String fallBackUpdateCustomerDetailByCustomer(@PathVariable("customerId") String customerId,@RequestBody Object customerInfo ) {
		String url="http://customerinsuranceproject-backup/updateCustomerDetailByCustomer/{customerId}";
		template.put(url, customerInfo, customerId);
		return "Details updated succesfully";
	}
	
	@HystrixCommand(fallbackMethod="fallBackAddInsuranceToCustomer")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/addInsurance/{customerId}")
	public String addInsuranceToCustomer(@PathVariable("customerId") String customerId,@RequestBody Object insurance ) {
		try {
			String url="http://customerinsuranceproject/customer/addInsurance/{customerId}";
			template.put(url, insurance, customerId);
			return "Insurance added";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackAddInsuranceToCustomer(@PathVariable("customerId") String customerId,@RequestBody Object insurance ) {
		String url="http://customerinsuranceproject-backup/addInsurance/{customerId}";
		template.put(url, insurance , customerId);
		return "Insurance added";
	}
	
	@HystrixCommand(fallbackMethod="fallBackUpdatePassword")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updatePassword/{customerId}")
	public String updatePassword(@PathVariable("customerId") String customerId,@RequestBody Object password) {
		try {
			String url="http://customerinsuranceproject/updatePassword/{customerId}";
			template.put(url, password , customerId);
			return "updated";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackUpdatePassword(@PathVariable("customerId") String customerId,@RequestBody Object password) {
		String url="http://customerinjwtUserDetailssuranceproject-backup/updatePassword/{customerId}";
		template.put(url, password , customerId);
		return "updated";
	}
	
	@HystrixCommand(fallbackMethod="fallBackDeActivateCustomerAccount")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deActivateCustomerAccount/{customerId}")
	public String deActivateCustomerAccount(@PathVariable("customerId") String customerId ) {
		try {
			String url="http://customerinsuranceproject/deActivateCustomerAccount/{customerId}";
			template.delete(url, customerId);
			return "deactivated";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackDeActivateCustomerAccount(@PathVariable("customerId") String customerId ) {
		String url="http://customerinsuranceproject-backup/deActivateCustomerAccount/{customerId}";
		template.delete(url, customerId);
		return "deactivated";
	}
	
	/*InsuranceDetailController*/
	
	/*Customer Related Operation*/	
	
	@HystrixCommand(fallbackMethod="fallBackGetAllCustomer")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getAllCustomer")
	public String getAllCustomer() {
		try {
			String url="http://customerinsuranceproject/getAllCustomer";
			return template.getForObject(url, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackGetAllCustomer() {
		String url="http://customerinsuranceproject-backup/getAllCustomer";
		return template.getForObject(url, String.class);
	}
	
	@HystrixCommand(fallbackMethod="fallBackGetCustomerDetailById")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getCustomerDetailById/{customerId}")
	public Object getCustomerDetailById(@PathVariable("customerId") String customerId) {
		try {
			String url="http://customerinsuranceproject/getCustomerDetailById/{customerId}";
			return template.getForEntity(url, String.class, customerId);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public Object fallBackGetCustomerDetailById(@PathVariable("customerId") String customerId) {
		String url="http://customerinsuranceproject-backup/getCustomerDetailById/{customerId}";
		return template.getForEntity(url, String.class, customerId);
	}

	@HystrixCommand(fallbackMethod="fallBackUpdateCustomerDetailByAdministrator")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateCustomerDetailByAdministrator/{customerId}")
	public String updateCustomerDetailByAdministrator(@PathVariable("customerId") String customerId ,@RequestBody Object customerInfo) {	
		try {
			String url="http://customerinsuranceproject/updateCustomerDetailByAdministrator/{customerId}";
			template.put(url, customerInfo, customerId);
			return "Details updated succesfully";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackUpdateCustomerDetailByAdministrator(@PathVariable("customerId") String customerId ,@RequestBody Object customerInfo) {	
		String url="http://customerinsuranceproject-backup/updateCustomerDetailByAdministrator/{customerId}";
		template.put(url, customerInfo, customerId);
		return "Details updated succesfully";
	}

	@HystrixCommand(fallbackMethod="fallBackActivateCustomerAccount")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/activateCustomerAccount/{customerId}")
	public String activateCustomerAccount(@PathVariable("customerId") String customerId ) {
		try {
			String url="http://customerinsuranceproject/activateCustomerAccount/{customerId}";
			template.put(url, String.class, customerId);
			return "status updated";
		}catch(Exception e) {
			throw new RuntimeException();
		}	
	}
	
	public String fallBackActivateCustomerAccount(@PathVariable("customerId") String customerId ) {
		String url="http://customerinsuranceproject-backup/activateCustomerAccount/{customerId}";
		template.put(url, String.class, customerId);
		return "status updated";
	}

	@HystrixCommand(fallbackMethod="fallBackDeleteCustomerAccount")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deleteCustomerAccount/{customerId}")
	public String deleteCustomerAccount(@PathVariable("customerId") String customerId ) {
		try {
			String url="http://customerinsuranceproject/deleteCustomerAccount/{customerId}";
			template.delete(url, customerId);
			return "deleted";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackDeleteCustomerAccount(@PathVariable("customerId") String customerId ) {
		String url="http://customerinsuranceproject-backup/deleteCustomerAccount/{customerId}";
		template.delete(url, customerId);
		return "deleted";
	}
	
/*Insurance Related Operation*/	
	
	@HystrixCommand(fallbackMethod="fallBackGetAllInsuranceDetails")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getAllInsuranceDetails")
	public String getAllInsuranceDetails() {	
		try {
			String url="http://customerinsuranceproject/getAllInsuranceDetails";
			return template.getForObject(url, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}

	public String fallBackGetAllInsuranceDetails() {	
		String url="http://customerinsuranceproject-backup/getAllInsuranceDetails";
		return template.getForObject(url, String.class);
	}
	
	@HystrixCommand(fallbackMethod="fallBackGetInsuranceDetailById")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getInsuranceDetailById/{insuranceId}")
	public Object getInsuranceDetailById(@PathVariable("insuranceId") String insuranceId) {
		try {
			String url="http://customerinsuranceproject/getInsuranceDetailById/{insuranceId}";
			return template.getForEntity(url, String.class, insuranceId);
		}catch(Exception e) {
			throw new RuntimeException();
		}	
	}
	
	public Object fallBackGetInsuranceDetailById(@PathVariable("insuranceId") String insuranceId) {
		String url="http://customerinsuranceproject-backup/getInsuranceDetailById/{insuranceId}";
		return template.getForEntity(url, String.class, insuranceId);
	}

	@HystrixCommand(fallbackMethod="fallBackCreateNewInsurance")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")	
	@PostMapping("/createNewInsurance")
	public String createNewInsurance(@RequestBody Object insuranceDetail) {
		try {
			String url="http://customerinsuranceproject/createNewInsurance";
			return template.postForObject(url,insuranceDetail, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackCreateNewInsurance(@RequestBody Object insuranceDetail) {
		String url="http://customerinsuranceproject-backup/createNewInsurance";
		return template.postForObject(url,insuranceDetail, String.class);
	}

	@HystrixCommand(fallbackMethod="fallBackUpdateInsuranceDetail")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateInsuranceDetail/{insuranceId}")
	public String updateInsuranceDetail(@PathVariable("insuranceId") String insuranceId,@RequestBody Object insuranceDetail) {
		try {
			String url="http://customerinsuranceproject/updateInsuranceDetail/{insuranceId}";
			template.put(url, insuranceDetail, insuranceId);
			return "Insurance updated succesfully";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackUpdateInsuranceDetail(@PathVariable("insuranceId") String insuranceId,@RequestBody Object insuranceDetail) {
		String url="http://customerinsuranceproject-backup/updateInsuranceDetail/{insuranceId}";
		template.put(url, insuranceDetail, insuranceId);
		return "Insurance updated succesfully";
	}
	
	@HystrixCommand(fallbackMethod="fallBackDeleteInsuranceDetail")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deleteInsuranceDetail/{insuranceId}")
	public String deleteInsuranceDetail(@PathVariable("insuranceId") String insuranceId ) {
		try {
			String url="http://customerinsuranceproject/deleteInsuranceDetail/{insuranceId}";
			template.delete(url, insuranceId);
			return "Insurance Deleted Successfully";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackDeleteInsuranceDetail(@PathVariable("insuranceId") String insuranceId ) {
		String url="http://customerinsuranceproject-backup/deleteInsuranceDetail/{insuranceId}";
		template.delete(url, insuranceId);
		return "Insurance Deleted Successfully";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HystrixApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate template() {
		return new RestTemplate();
	}

}
