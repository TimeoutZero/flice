package com.timeoutzero.flice.account.test.controller;

import static com.timeoutzero.flice.account.test.Compose.product;
import static com.timeoutzero.flice.account.test.Compose.user;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.timeoutzero.flice.account.entity.Product;
import com.timeoutzero.flice.account.entity.User;
import com.timeoutzero.flice.account.enums.SocialMedia;
import com.timeoutzero.flice.account.form.UserForm;
import com.timeoutzero.flice.account.security.ApplicationKeyFilter;
import com.timeoutzero.flice.account.test.ApplicationTest;

public class UserControllerTest extends ApplicationTest {
	
	@Test
	public void testShouldCreateUser() throws Exception {
		
		Product core = product("core");
		saveAll(core);
		
		JsonNode json = post("/user")
				.json(new UserForm("lucas.gmmartins@gmail.com", "12345"))
				.header(ApplicationKeyFilter.HEADER_X_FLICE_TOKEN, core.getToken())
				.expectedStatus(HttpStatus.CREATED)
				.getJson();
		
		jsonAsserter(json) 
			.assertEquals("$.email", "lucas.gmmartins@gmail.com");
	}
 
	@Test
	public void testShouldntCreateUserWithSameEmail() throws Exception {

		Product core = product("core");
		User lucas = user("lucas.gmmartins@gmail.com").build();
		
		saveAll(core, lucas);

		JsonNode json = post("/user")
				.json(new UserForm("lucas.gmmartins@gmail.com", "12345"))
				.header(ApplicationKeyFilter.HEADER_X_FLICE_TOKEN, core.getToken())
				.expectedStatus(HttpStatus.PRECONDITION_FAILED)
				.getJson();
		
		jsonAsserter(json)
			.assertEquals("$.message", "email.already.exist");

	}
	
	@Test
	public void testShouldChangePassword() throws Exception {
		
		Product core = product("core");
		User lucas = user("lucas.gmmartins@gmail.com", "54321").build();
		
		saveAll(core, lucas);
		
		put("/user/%s/password", lucas.getId())
			.queryParam("newPassword", "12345")
			.queryParam("newPasswordConfirmation", "12345")
			.queryParam("actualPassword", "54321")
			.header(ApplicationKeyFilter.HEADER_X_FLICE_TOKEN, core.getToken())
			.expectedStatus(HttpStatus.OK)
			.getResponse();
	}
	
	@Test
	public void testShouldntChangePasswordActualPasswordInvalid() throws Exception {
		
		Product core = product("core");
		User lucas = user("lucas.gmmartins@gmail.com", "54321").build();
		
		saveAll(core, lucas);
		
		JsonNode json = put("/user/%s/password", lucas.getId())
			.queryParam("newPassword", "12345")
			.queryParam("newPasswordConfirmation", "12345")
			.queryParam("actualPassword", "-1")
			.header(ApplicationKeyFilter.HEADER_X_FLICE_TOKEN, core.getToken())
			.expectedStatus(HttpStatus.FORBIDDEN)
			.getJson();
		
		jsonAsserter(json)
			.assertEquals("$.message", "invalid.actual.password");
	}
	
	@Test
	public void testShouldntChangePasswordNewPasswordNotMatch() throws Exception {
		
		Product core = product("core");
		User lucas = user("lucas.gmmartins@gmail.com", "54321").build();
		
		saveAll(core, lucas);
		
		JsonNode json = put("/user/%s/password", lucas.getId())
				.queryParam("newPassword", "11111")
				.queryParam("newPasswordConfirmation", "22222")
				.queryParam("actualPassword", "54321")
				.header(ApplicationKeyFilter.HEADER_X_FLICE_TOKEN, core.getToken())
				.expectedStatus(HttpStatus.PRECONDITION_REQUIRED)
				.getJson();
		
		jsonAsserter(json)
			.assertEquals("$.message", "password.not.match");
	}

	@Test 
	@Ignore
	public void testShouldCreateUserWithAccessToken() throws Exception {
		
		Product core = product("core");
		
		saveAll(core); 
		
		JsonNode json = post("/user/token")
			.queryParam("accessToken", "ACCESSTOKEN")
			.queryParam("media", SocialMedia.FACEBOOK.toString())
			.header(ApplicationKeyFilter.HEADER_X_FLICE_TOKEN, core.getToken())
			.expectedStatus(HttpStatus.CREATED)
			.getJson();

		jsonAsserter(json)
			.assertNotNull("$.email")
			.assertNotNull("$.profile.name")
			.assertNotNull("$.profile.photo");
	}
}
