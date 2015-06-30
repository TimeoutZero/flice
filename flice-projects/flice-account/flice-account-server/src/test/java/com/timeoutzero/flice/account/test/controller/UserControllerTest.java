package com.timeoutzero.flice.account.test.controller;

import static com.timeoutzero.flice.account.builder.UserBuilder.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.timeoutzero.flice.account.enums.SocialMedia;
import com.timeoutzero.flice.account.test.BasicControllerTest;
import com.timeoutzero.flice.account.test.ControllerBase;

@ControllerBase("/user")
public class UserControllerTest extends BasicControllerTest {

	@Test
	public void shouldCreateUser() throws Exception {
		
		MockHttpServletRequestBuilder post = post();
		post.param("email"	 , "lucas.gmmartins@gmail.com");
		post.param("password", "12345");
		
		MvcResult result = perform(post, status().isCreated());
		
		jsonAssert(result)
			.assertEquals("$.email", "lucas.gmmartins@gmail.com")
			.assertNull("$.username");
	}

	@Test
	public void shouldntCreateUserWithSameUsername() throws Exception {

		user().username("lucasflice");

		saveAll();

		MockHttpServletRequestBuilder post = post();
		post.param("username", "lucasflice");
		post.param("password", "12345");

		MvcResult result = perform(post, status().isPreconditionFailed());
		
		jsonError(result)
			.contains("username.already.exist");
	}

	@Test
	public void shouldntCreateUserWithSameEmail() throws Exception {

		user().email("lucas.gmmartins@gmail.com");

		saveAll();

		MockHttpServletRequestBuilder post = post();
		post.param("email", "lucas.gmmartins@gmail.com");
		post.param("password", "12345");

		MvcResult result = perform(post, status().isPreconditionFailed());

		jsonError(result)
			.contains("email.already.exist");
	}

	@Test
	public void shouldCreateUserWithAccessToken() throws Exception {
		
		MockHttpServletRequestBuilder post = post("/token");
		post.param("accessToken", "ACCESSTOKEN");
		post.param("media", SocialMedia.FACEBOOK.toString());

		MvcResult result = perform(post, status().isCreated());

		jsonAssert(result)
			.assertNotNull("$.email")
			.assertNotNull("$.profile.name")
			.assertNotNull("$.profile.photo")
			.assertNull("$.username");
	}
}
