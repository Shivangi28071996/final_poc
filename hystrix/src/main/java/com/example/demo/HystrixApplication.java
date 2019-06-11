package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
	
	@HystrixCommand(fallbackMethod="fallBackLoginCustomer")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PostMapping("/token")
	public String token(@RequestBody Object loginCustomer) {
		try {
			System.out.println("generating token ....");
			String url="http://customerinsuranceproject/token";
			return template.postForObject(url,loginCustomer, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}	
	}
	
	public String fallBackLoginCustomer(@RequestBody Object loginCustomer) {
		String url="http://customerinsuranceproject-backup/token";
		return template.postForObject(url,loginCustomer, String.class);
	}
	
	@HystrixCommand(fallbackMethod="fallBackGetCustomerById")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getCustomerById")
	public ResponseEntity<String> getCustomerById(@RequestHeader("Authorization") String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    HttpEntity<String> entity = new HttpEntity<String>(headers);
			String url="http://customerinsuranceproject/customer/getCustomerById";
			return template.exchange(url, HttpMethod.GET, entity, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}	
	}
	
	public ResponseEntity<String> fallBackGetCustomerById(@RequestHeader("Authorization") String token) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url="http://customerinsuranceproject-backup/customer/getCustomerById";
		return template.exchange(url, HttpMethod.GET, entity, String.class);
	}
	
	@HystrixCommand(fallbackMethod="fallBackUpdateCustomerDetailByCustomer")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateCustomerDetailByCustomer")		
	public String updateCustomerDetailByCustomer(@RequestHeader("Authorization") String token,@RequestBody Object customerInfo ) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    HttpEntity<Object> entity = new HttpEntity<Object>(customerInfo,headers);
			String url="http://customerinsuranceproject/customer/updateCustomerDetailByCustomer";
			template.exchange(url, HttpMethod.PUT, entity, String.class);
			return "Details updated succesfully";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}

	public String fallBackUpdateCustomerDetailByCustomer(@RequestHeader("Authorization") String token,@RequestBody Object customerInfo ) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    HttpEntity<Object> entity = new HttpEntity<Object>(customerInfo,headers);
		String url="http://customerinsuranceproject-backup/customer/updateCustomerDetailByCustomer";
		template.exchange(url, HttpMethod.PUT, entity, String.class);
		return "Details updated succesfully";
	}
	
	@HystrixCommand(fallbackMethod="fallBackGetInsuranceDetails")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getInsuranceDetails")
	public ResponseEntity<String> getInsuranceDetails(@RequestHeader("Authorization") String token) {	
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		    String url="http://customerinsuranceproject/customer/getInsuranceDetails";
			return template.exchange(url, HttpMethod.GET, entity, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}

	public ResponseEntity<String> fallBackGetInsuranceDetails(@RequestHeader("Authorization") String token) {	
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	    String url="http://customerinsuranceproject-backup/customer/getInsuranceDetails";
		return template.exchange(url, HttpMethod.GET, entity, String.class);
	}
	
	@HystrixCommand(fallbackMethod="fallBackGetInsuranceDetailsById")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getInsuranceDetailsById")
	public Object getInsuranceDetailsById(@RequestHeader("Authorization") String token,@RequestHeader("insuranceId") String insuranceId) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    headers.set("insuranceId", insuranceId);
		    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		    String url="http://customerinsuranceproject/customer/getInsuranceDetailsById";
			return template.exchange(url, HttpMethod.GET, entity, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}	
	}
	
	public Object fallBackGetInsuranceDetailsById(@RequestHeader("Authorization") String token,@RequestHeader("insuranceId") String insuranceId) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    headers.set("insuranceId", insuranceId);
	    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	    String url="http://customerinsuranceproject-backup/customer/getInsuranceDetailsById";
		return template.exchange(url, HttpMethod.GET, entity, String.class);
	}
	
	@HystrixCommand(fallbackMethod="fallBackAddInsuranceToCustomer")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/addInsurance")
	public String addInsuranceToCustomer(@RequestHeader("Authorization") String token,@RequestBody Object insurance ) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    HttpEntity<Object> entity = new HttpEntity<Object>(insurance,headers);
		    String url="http://customerinsuranceproject/customer/addInsurance";
			template.exchange(url, HttpMethod.PUT, entity, String.class);
			return "Insurance added";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackAddInsuranceToCustomer(@RequestHeader("Authorization") String token,@RequestBody Object insurance ) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    HttpEntity<Object> entity = new HttpEntity<Object>(insurance,headers);
	    String url="http://customerinsuranceproject-backup/customer/addInsurance";
		template.exchange(url, HttpMethod.PUT, entity, String.class);
		return "Insurance added";
	}
	
	@HystrixCommand(fallbackMethod="fallBackUpdatePassword")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updatePassword")
	public String updatePassword(@RequestHeader("Authorization") String token,@RequestBody Object password) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    HttpEntity<Object> entity = new HttpEntity<Object>(password,headers);
		    String url="http://customerinsuranceproject/customer/updatePassword";
			template.exchange(url, HttpMethod.PUT, entity, String.class);
			return "updated";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackUpdatePassword(@RequestHeader("Authorization") String token,@RequestBody Object password) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    HttpEntity<Object> entity = new HttpEntity<Object>(password,headers);
	    String url="http://customerinsuranceproject-backup/customer/updatePassword";
		template.exchange(url, HttpMethod.PUT, entity, String.class);
		return "updated";
	}
	
	@HystrixCommand(fallbackMethod="fallBackDeActivateCustomerAccount")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deActivateCustomerAccount")
	public String deActivateCustomerAccount(@RequestHeader("Authorization") String token ) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		    String url="http://customerinsuranceproject/customer/deActivateCustomerAccount";
			template.exchange(url, HttpMethod.DELETE, entity, String.class);
			return "deactivated";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackDeActivateCustomerAccount(@RequestHeader("Authorization") String token ) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	    String url="http://customerinsuranceproject-backup/customer/deActivateCustomerAccount";
		template.exchange(url, HttpMethod.DELETE, entity, String.class);
		return "deactivated";
	}
	
	/*InsuranceDetailController*/
	
	/*Customer Related Operation*/	
	
	@HystrixCommand(fallbackMethod="fallBackGetAllCustomer")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getAllCustomer")
	public ResponseEntity<String> getAllCustomer(@RequestHeader("Authorization") String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		    String url="http://customerinsuranceproject/admin/getAllCustomer";
			return template.exchange(url, HttpMethod.GET, entity, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public ResponseEntity<String> fallBackGetAllCustomer(@RequestHeader("Authorization") String token) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	    String url="http://customerinsuranceproject-backup/admin/getAllCustomer";
		return template.exchange(url, HttpMethod.GET, entity, String.class);
	}
	
	@HystrixCommand(fallbackMethod="fallBackGetCustomerDetailById")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getCustomerDetailById")
	public Object getCustomerDetailById(@RequestHeader("Authorization") String token,@RequestHeader("customerId") String customerId) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    headers.set("customerId", customerId);
		    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		    String url="http://customerinsuranceproject/admin/getCustomerDetailById";
			return template.exchange(url, HttpMethod.GET, entity, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public Object fallBackGetCustomerDetailById(@RequestHeader("Authorization") String token,@RequestHeader("customerId") String customerId) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    headers.set("customerId", customerId);
	    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	    String url="http://customerinsuranceproject-backup/admin/getCustomerDetailById";
		return template.exchange(url, HttpMethod.GET, entity, String.class);
	}

	@HystrixCommand(fallbackMethod="fallBackUpdateCustomerDetailByAdministrator")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateCustomerDetailByAdministrator")
	public String updateCustomerDetailByAdministrator(@RequestHeader("Authorization") String token,@RequestHeader("customerId") String customerId,@RequestBody Object customerInfo) {	
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    headers.set("customerId", customerId);
		    HttpEntity<Object> entity = new HttpEntity<Object>(customerInfo,headers);
		    String url="http://customerinsuranceproject/admin/updateCustomerDetailByAdministrator";
			template.exchange(url, HttpMethod.PUT, entity, String.class);
			return "Details updated succesfully";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackUpdateCustomerDetailByAdministrator(@RequestHeader("Authorization") String token,@RequestHeader("customerId") String customerId,@RequestBody Object customerInfo) {	
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    headers.set("customerId", customerId);
	    HttpEntity<Object> entity = new HttpEntity<Object>(customerInfo,headers);
	    String url="http://customerinsuranceproject-backup/admin/updateCustomerDetailByAdministrator";
		template.exchange(url, HttpMethod.PUT, entity, String.class);
		return "Details updated succesfully";
	}

	@HystrixCommand(fallbackMethod="fallBackActivateCustomerAccount")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/activateCustomerAccount")
	public String activateCustomerAccount(@RequestHeader("Authorization") String token,@RequestHeader("customerId") String customerId) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    headers.set("customerId", customerId);
		    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		    String url="http://customerinsuranceproject/admin/activateCustomerAccount";
			template.exchange(url, HttpMethod.PUT, entity, String.class);
			return "status updated";
		}catch(Exception e) {
			throw new RuntimeException();
		}	
	}
	
	public String fallBackActivateCustomerAccount(@RequestHeader("Authorization") String token,@RequestHeader("customerId") String customerId) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    headers.set("customerId", customerId);
	    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	    String url="http://customerinsuranceproject-backup/admin/activateCustomerAccount";
		template.exchange(url, HttpMethod.PUT, entity, String.class);
		return "status updated";
	}

	@HystrixCommand(fallbackMethod="fallBackDeleteCustomerAccount")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deleteCustomerAccount")
	public String deleteCustomerAccount(@RequestHeader("Authorization") String token,@RequestHeader("customerId") String customerId) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    headers.set("customerId", customerId);
		    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		    String url="http://customerinsuranceproject/admin/deleteCustomerAccount";
			template.exchange(url, HttpMethod.DELETE, entity, String.class);
			return "deleted";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackDeleteCustomerAccount(@RequestHeader("Authorization") String token,@RequestHeader("customerId") String customerId) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    headers.set("customerId", customerId);
	    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	    String url="http://customerinsuranceproject-backup/admin/deleteCustomerAccount";
		template.exchange(url, HttpMethod.DELETE, entity, String.class);
		return "deleted";
	}
	
/*Insurance Related Operation*/	
	
	@HystrixCommand(fallbackMethod="fallBackCreateNewInsurance")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")	
	@PostMapping("/createNewInsurance")
	public ResponseEntity<String> createNewInsurance(@RequestHeader("Authorization") String token,@RequestBody Object insuranceDetail) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    HttpEntity<Object> entity = new HttpEntity<Object>(insuranceDetail,headers);
		    String url="http://customerinsuranceproject/admin/createNewInsurance";
			return template.exchange(url, HttpMethod.POST, entity, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public ResponseEntity<String> fallBackCreateNewInsurance(@RequestHeader("Authorization") String token,@RequestBody Object insuranceDetail) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    HttpEntity<Object> entity = new HttpEntity<Object>(insuranceDetail,headers);
	    String url="http://customerinsuranceproject-backup/admin/createNewInsurance";
		return template.exchange(url, HttpMethod.POST, entity, String.class);
	}

	@HystrixCommand(fallbackMethod="fallBackGetAllInsuranceDetails")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getAllInsuranceDetails")
	public ResponseEntity<String> getAllInsuranceDetails(@RequestHeader("Authorization") String token) {	
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		    String url="http://customerinsuranceproject/admin/getAllInsuranceDetails";
			return template.exchange(url, HttpMethod.GET, entity, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}

	public ResponseEntity<String> fallBackGetAllInsuranceDetails(@RequestHeader("Authorization") String token) {	
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	    String url="http://customerinsuranceproject-backup/admin/getAllInsuranceDetails";
		return template.exchange(url, HttpMethod.GET, entity, String.class);
	}
	
	@HystrixCommand(fallbackMethod="fallBackGetInsuranceDetailById")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getInsuranceDetailById")
	public Object getInsuranceDetailById(@RequestHeader("Authorization") String token,@RequestHeader("insuranceId") String insuranceId) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    headers.set("insuranceId", insuranceId);
		    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		    String url="http://customerinsuranceproject/admin/getInsuranceDetailById";
			return template.exchange(url, HttpMethod.GET, entity, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}	
	}
	
	public Object fallBackGetInsuranceDetailById(@RequestHeader("Authorization") String token,@RequestHeader("insuranceId") String insuranceId) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    headers.set("insuranceId", insuranceId);
	    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	    String url="http://customerinsuranceproject-backup/admin/getInsuranceDetailById";
		return template.exchange(url, HttpMethod.GET, entity, String.class);
	}

	
	@HystrixCommand(fallbackMethod="fallBackUpdateInsuranceDetail")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateInsuranceDetail")
	public String updateInsuranceDetail(@RequestHeader("Authorization") String token,@RequestHeader("insuranceId") String insuranceId,@RequestBody Object insuranceDetail) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    headers.set("insuranceId", insuranceId);
		    HttpEntity<Object> entity = new HttpEntity<Object>(insuranceDetail,headers);
		    String url="http://customerinsuranceproject/admin/updateInsuranceDetail";
			template.exchange(url, HttpMethod.PUT, entity, String.class);
			return "Details updated successfully";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackUpdateInsuranceDetail(@RequestHeader("Authorization") String token,@RequestHeader("insuranceId") String insuranceId,@RequestBody Object insuranceDetail) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    headers.set("insuranceId", insuranceId);
	    HttpEntity<Object> entity = new HttpEntity<Object>(insuranceDetail,headers);
	    String url="http://customerinsuranceproject-backup/admin/updateInsuranceDetail";
		template.exchange(url, HttpMethod.PUT, entity, String.class);
		return "Details updated successfully";
	}
	
	@HystrixCommand(fallbackMethod="fallBackDeleteInsuranceDetail")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deleteInsuranceDetail")
	public String deleteInsuranceDetail(@RequestHeader("Authorization") String token,@RequestHeader("insuranceId") String insuranceId ) {
		try {
			HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", token);
		    headers.set("insuranceId", insuranceId);
		    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		    String url="http://customerinsuranceproject/admin/deleteInsuranceDetail";
			template.exchange(url, HttpMethod.DELETE, entity, String.class);
			return "Insurance Deleted Successfully";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackDeleteInsuranceDetail(@RequestHeader("Authorization") String token,@RequestHeader("insuranceId") String insuranceId) {
		HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", token);
	    headers.set("insuranceId", insuranceId);
	    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	    String url="http://customerinsuranceproject-backup/admin/deleteInsuranceDetail";
		template.exchange(url, HttpMethod.DELETE, entity, String.class);
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
