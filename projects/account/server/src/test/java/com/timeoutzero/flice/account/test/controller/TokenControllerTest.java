package com.timeoutzero.flice.account.test.controller;

import static com.timeoutzero.flice.account.test.Compose.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.timeoutzero.flice.account.entity.User;
import com.timeoutzero.flice.account.security.JwtAccount;
import com.timeoutzero.flice.account.test.ApplicationTest;

public class TokenControllerTest extends ApplicationTest {
	
	@Autowired
	private JwtAccount jwtAccount;
	
	@Test
	public void shouldCheckTokenWithEmail() throws Exception {
		
		User lucas = user("lucas.gmmartins@gmail.com").build();
		
		saveAll(lucas); 
		 
		get("/auth/token")
				.queryParam("token", jwtAccount.createToken(lucas))
				.expectedStatus(HttpStatus.OK);
		
	}
	
	@Test
	public void shouldCheckInvalidToken() throws Exception {
	
		get("/auth/token")
			.queryParam("token", "INVALID-TOKEN")
			.expectedStatus(HttpStatus.UNAUTHORIZED);
	}
}
