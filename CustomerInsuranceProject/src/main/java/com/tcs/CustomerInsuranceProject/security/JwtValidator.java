package com.tcs.CustomerInsuranceProject.security;

import org.springframework.stereotype.Component;
import com.tcs.CustomerInsuranceProject.model.LoginCustomer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtValidator {

	public LoginCustomer validate(String token) {
		LoginCustomer jwtUser=null;
		try {
			String secret="insurance";
			Claims body=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			jwtUser=new LoginCustomer();
			jwtUser.setUsername(body.getSubject());
			jwtUser.setPassword((String)body.get("password"));
		}catch(Exception e) {
			System.out.println(e);
		}
		return jwtUser;
	}

}
 