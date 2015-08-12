package com.timeoutzero.flice.core.controller;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.timeoutzero.flice.core.ApplicationTest;
import com.timeoutzero.flice.core.form.UserForm;

public class UserControllerTest extends ApplicationTest {

	@Test
	public void testNonSecuredAPI() throws Exception {
		
		UserForm value = new UserForm();
		value.setEmail("email@gmail.com");
		value.setPassword("password");
		
		post("/account/user").json(value).expectedStatus(HttpStatus.OK).getResponse();
		get("/community").expectedStatus(HttpStatus.UNAUTHORIZED).getResponse();
	}
}
