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
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@HystrixCommand(fallbackMethod="fallBackAddInsuranceToCustomer")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/addInsurance")//	@HystrixCommand(fallbackMethod="fallBackDeleteInsuranceDetail")

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
	public String getAllCustomer() {
		try {
			String url="http://customerinsuranceproject/admin/getAllCustomer";
			return template.getForObject(url, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackGetAllCustomer() {
		String url="http://customerinsuranceproject-backup/admin/getAllCustomer";
		return template.getForObject(url, String.class);
	}
	
	@HystrixCommand(fallbackMethod="fallBackGetCustomerDetailById")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getCustomerDetailById/{customerId}")
	public Object getCustomerDetailById(@PathVariable("customerId") String customerId) {
		try {
			String url="http://customerinsuranceproject/admin/getCustomerDetailById/{customerId}";
			return template.getForEntity(url, String.class, customerId);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public Object fallBackGetCustomerDetailById(@PathVariable("customerId") String customerId) {
		String url="http://customerinsuranceproject-backup/admin/getCustomerDetailById/{customerId}";
		return template.getForEntity(url, String.class, customerId);
	}

	@HystrixCommand(fallbackMethod="fallBackUpdateCustomerDetailByAdministrator")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateCustomerDetailByAdministrator/{customerId}")
	public String updateCustomerDetailByAdministrator(@PathVariable("customerId") String customerId ,@RequestBody Object customerInfo) {	
		try {
			String url="http://customerinsuranceproject/admin/updateCustomerDetailByAdministrator/{customerId}";
			template.put(url, customerInfo, customerId);
			return "Details updated succesfully";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackUpdateCustomerDetailByAdministrator(@PathVariable("customerId") String customerId ,@RequestBody Object customerInfo) {	
		String url="http://customerinsuranceproject-backup/admin/updateCustomerDetailByAdministrator/{customerId}";
		template.put(url, customerInfo, customerId);
		return "Details updated succesfully";
	}

	@HystrixCommand(fallbackMethod="fallBackActivateCustomerAccount")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/activateCustomerAccount/{customerId}")
	public String activateCustomerAccount(@PathVariable("customerId") String customerId ) {
		try {
			String url="http://customerinsuranceproject/admin/activateCustomerAccount/{customerId}";
			template.put(url, String.class, customerId);
			return "status updated";
		}catch(Exception e) {
			throw new RuntimeException();
		}	
	}
	
	public String fallBackActivateCustomerAccount(@PathVariable("customerId") String customerId ) {
		String url="http://customerinsuranceproject-backup/admin/activateCustomerAccount/{customerId}";
		template.put(url, String.class, customerId);
		return "status updated";
	}

	@HystrixCommand(fallbackMethod="fallBackDeleteCustomerAccount")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deleteCustomerAccount/{customerId}")
	public String deleteCustomerAccount(@PathVariable("customerId") String customerId ) {
		try {
			String url="http://customerinsuranceproject/admin/deleteCustomerAccount/{customerId}";
			template.delete(url, customerId);
			return "deleted";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackDeleteCustomerAccount(@PathVariable("customerId") String customerId ) {
		String url="http://customerinsuranceproject-backup/admin/deleteCustomerAccount/{customerId}";
		template.delete(url, customerId);
		return "deleted";
	}
	
/*Insurance Related Operation*/	
	
	@HystrixCommand(fallbackMethod="fallBackGetAllInsuranceDetails")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getAllInsuranceDetails")
	public String getAllInsuranceDetails() {	
		try {
			String url="http://customerinsuranceproject/admin/getAllInsuranceDetails";
			return template.getForObject(url, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}

	public String fallBackGetAllInsuranceDetails() {	
		String url="http://customerinsuranceproject-backup/admin/getAllInsuranceDetails";
		return template.getForObject(url, String.class);
	}
	
	@HystrixCommand(fallbackMethod="fallBackGetInsuranceDetailById")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@GetMapping("/getInsuranceDetailById/{insuranceId}")
	public Object getInsuranceDetailById(@PathVariable("insuranceId") String insuranceId) {
		try {
			String url="http://customerinsuranceproject/admin/getInsuranceDetailById/{insuranceId}";
			return template.getForEntity(url, String.class, insuranceId);
		}catch(Exception e) {
			throw new RuntimeException();
		}	
	}
	
	public Object fallBackGetInsuranceDetailById(@PathVariable("insuranceId") String insuranceId) {
		String url="http://customerinsuranceproject-backup/admin/getInsuranceDetailById/{insuranceId}";
		return template.getForEntity(url, String.class, insuranceId);
	}

	@HystrixCommand(fallbackMethod="fallBackCreateNewInsurance")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")	
	@PostMapping("/createNewInsurance")
	public String createNewInsurance(@RequestBody Object insuranceDetail) {
		try {
			String url="http://customerinsuranceproject/admin/createNewInsurance";
			return template.postForObject(url,insuranceDetail, String.class);
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackCreateNewInsurance(@RequestBody Object insuranceDetail) {
		String url="http://customerinsuranceproject-backup/admin/createNewInsurance";
		return template.postForObject(url,insuranceDetail, String.class);
	}

	@HystrixCommand(fallbackMethod="fallBackUpdateInsuranceDetail")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@PutMapping("/updateInsuranceDetail/{insuranceId}")
	public String updateInsuranceDetail(@PathVariable("insuranceId") String insuranceId,@RequestBody Object insuranceDetail) {
		try {
			String url="http://customerinsuranceproject/admin/updateInsuranceDetail/{insuranceId}";
			template.put(url, insuranceDetail, insuranceId);
			return "Insurance updated succesfully";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackUpdateInsuranceDetail(@PathVariable("insuranceId") String insuranceId,@RequestBody Object insuranceDetail) {
		String url="http://customerinsuranceproject-backup/admin/updateInsuranceDetail/{insuranceId}";
		template.put(url, insuranceDetail, insuranceId);
		return "Insurance updated succesfully";
	}
	
	@HystrixCommand(fallbackMethod="fallBackDeleteInsuranceDetail")
	@CrossOrigin(allowedHeaders= "*",allowCredentials="true")
	@DeleteMapping("/deleteInsuranceDetail/{insuranceId}")
	public String deleteInsuranceDetail(@PathVariable("insuranceId") String insuranceId ) {
		try {
			String url="http://customerinsuranceproject/admin/deleteInsuranceDetail/{insuranceId}";
			template.delete(url, insuranceId);
			return "Insurance Deleted Successfully";
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public String fallBackDeleteInsuranceDetail(@PathVariable("insuranceId") String insuranceId ) {
		String url="http://customerinsuranceproject-backup/admin/deleteInsuranceDetail/{insuranceId}";
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
