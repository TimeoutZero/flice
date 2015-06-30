package com.timeoutzero.flice.account.test.controller;

import static com.timeoutzero.flice.account.builder.ProductBuilder.product;
import static com.timeoutzero.flice.account.builder.UserBuilder.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.timeoutzero.flice.account.builder.ProductBuilder;
import com.timeoutzero.flice.account.builder.UserBuilder;
import com.timeoutzero.flice.account.test.BasicControllerTest;
import com.timeoutzero.flice.account.test.ControllerBase;

@ControllerBase("/auth/token")
public class TokenControllerTest extends BasicControllerTest {
	
	private ProductBuilder fliceCommunity;

	@Before
	public void before() {
		
		fliceCommunity = product("flice-community");
	}
	
	@Test
	public void shouldGenerateTokenWithUsername() throws Exception {
		
		ProductBuilder flice = product("flice-community");
		user().username("lucas.martins");
		saveAll();
		
		MockHttpServletRequestBuilder post = post();
		post.param("username", "lucas.martins");
		post.param("password", "12345");
		post.param("clientId", flice.getId().toString());
		post.param("grantType", "password");
		
		MvcResult result = perform(post, status().isCreated());
		
		jsonAssert(result)
			.assertNotNull("$.token");
	}
	
	@Test
	public void shouldCheckTokenWithUsername() throws Exception {
		  
		UserBuilder lucas = user().username("lucas.martins");
		
		saveAll();
		 
		MockHttpServletRequestBuilder get = get();
		get.param("token", jwtAccount.createToken(fliceCommunity.get(), lucas.get()));
		
		perform(get, status().isOk());
	}
	
	@Test
	public void shouldCheckTokenWithEmail() throws Exception {
		
		ProductBuilder fliceCommunity = product("flice-community");
		UserBuilder lucas = user().email("lucas.gmmartins@gmai.com");
		
		saveAll(); 
		
		MockHttpServletRequestBuilder get = get();
		get.param("token", jwtAccount.createToken(fliceCommunity.get(), lucas.get()));
		
		perform(get, status().isOk());
	}
	
	@Test
	public void shouldCheckInvalidToken() throws Exception {
	
		MockHttpServletRequestBuilder get = get();
		get.param("token", "INVALID-TOKEN");
		
		perform(get, status().isUnauthorized());
	}
}
