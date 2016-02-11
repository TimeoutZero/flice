package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.compose.Compose.user;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.timeoutzero.flice.core.ApplicationTest;
import com.timeoutzero.flice.core.RequestBuilder;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.service.SmtpMailSender;

public class UserControllerTest extends ApplicationTest {
	
	@Mock
	private SmtpMailSender mockMailSender;
	
	@Autowired
	private SmtpMailSender mailSender;
	
	@Autowired
	private UserController userController; 

	@Test
	public void testInviteFriend() throws Exception {
		
		UserController unwrapProxy = (UserController) unwrapProxy(userController);
		ReflectionTestUtils.setField(unwrapProxy, "mailSender", mockMailSender);
		
		doNothing().when(mockMailSender).send(anyString(), anyString(), anyString());
		
		User lucas = user("lucas.martins").build();
		saveAll();
		login(lucas);
		
		RequestBuilder post = post("/user/invite");
		post.formParam("email", "thediafrit@yahoo.com");
		
		JsonNode json = post.getJson();
		
		jsonAsserter(json)
			.assertEquals("invites", 1);
		
		verify(mockMailSender, times(1)).send(anyString(), anyString(), anyString());
		
		ReflectionTestUtils.setField(unwrapProxy, "mailSender", mailSender);
		
	}
	
	@Test
	public void testFailedWhenInvitesExceedLimit() throws Exception {
		
		UserController unwrapProxy = (UserController) unwrapProxy(userController);
		ReflectionTestUtils.setField(unwrapProxy, "mailSender", mockMailSender);
		
		doNothing().when(mockMailSender).send(anyString(), anyString(), anyString());
		
		User lucas = user("lucas.martins").invites(5).build();
		saveAll();
		login(lucas);
		
		RequestBuilder post = post("/user/invite").expectedStatus(HttpStatus.PRECONDITION_FAILED);
		post.formParam("email", "thediafrit@yahoo.com");
		
		post.getJson();
		
		verify(mockMailSender, times(0)).send(anyString(), anyString(), anyString());
		
		ReflectionTestUtils.setField(unwrapProxy, "mailSender", mailSender);
		
	}
}
