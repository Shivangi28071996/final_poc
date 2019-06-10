package com.tcs.CustomerInsuranceProject.security;

import org.springframework.stereotype.Component;
import com.tcs.CustomerInsuranceProject.model.LoginCustomer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {

	public String generate(LoginCustomer jwtUser) {
		Claims claims=Jwts.claims().setSubject(jwtUser.getUsername());
		claims.put("password", jwtUser.getPassword());		
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, "insurance").compact();
	}

}
