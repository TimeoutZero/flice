package com.timeoutzero.flice.account.test.controller;

import static com.timeoutzero.flice.account.builder.UserBuilder.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.timeoutzero.flice.account.builder.UserBuilder;
import com.timeoutzero.flice.account.test.BasicControllerTest;
import com.timeoutzero.flice.account.test.ControllerBase;

@ControllerBase("/auth/token")
public class TokenControllerTest extends BasicControllerTest {
	
	@Test
	public void shouldCheckTokenWithEmail() throws Exception {
		
		UserBuilder lucas = user("lucas.gmmartins@gmai.com");
		
		saveAll(); 
		
		MockHttpServletRequestBuilder get = get();
		get.param("token", jwtAccount.createToken(lucas.get()));
		
		perform(get, status().isOk());
	}
	
	@Test
	public void shouldCheckInvalidToken() throws Exception {
	
		MockHttpServletRequestBuilder get = get();
		get.param("token", "INVALID-TOKEN");
		
		perform(get, status().isUnauthorized());
	}
}
