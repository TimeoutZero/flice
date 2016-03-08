package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.compose.Compose.user;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.fasterxml.jackson.databind.JsonNode;
import com.timeoutzero.flice.core.ApplicationTest;
import com.timeoutzero.flice.core.RequestBuilder;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.service.ImageService;
import com.timeoutzero.flice.core.service.SmtpMailSender;
import com.timeoutzero.flice.core.util.ImageMockUtil;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.dto.ProfileDTO;
import com.timeoutzero.flice.rest.operations.AccountOperations;
import com.timeoutzero.flice.rest.operations.UserOperations;

@SuppressWarnings("unchecked")
public class UserControllerTest extends ApplicationTest {
	
	@Autowired
	@InjectMocks
	private ImageService imageService;
	
	@Mock
	private AmazonS3Client amazons3;
	
	@Mock
	private SmtpMailSender mockMailSender;
	
	@Mock
	private AccountOperations mockAccountOperations;
	
	@Autowired
	private SmtpMailSender mailSender;
	
	@Mock
	private AccountOperations accountOperations;
	
	@Autowired
	private UserController userController; 
	
	@Test
	public void testCreateByEmail() throws Exception {
		
		UserController unwrapProxy = (UserController) unwrapProxy(userController);
		ReflectionTestUtils.setField(unwrapProxy, "mailSender", mockMailSender);
		ReflectionTestUtils.setField(unwrapProxy, "accountOperations", mockAccountOperations);
		
		UserOperations userOperationsMock = mock(UserOperations.class);
	
		when(mockAccountOperations.getUserOperations()).thenReturn(userOperationsMock);
		when(userOperationsMock.create(anyString(), anyString())).thenReturn(new AccountUserDTO());
		
		RequestBuilder post = post("/user/email");
		post.queryParam("email", "thediafrit@yahoo.com");
		
		post.expectedStatus(HttpStatus.CREATED).getJson();
		
		verify(mockMailSender, times(1)).send(anyString(), anyString(), anyString(), anyMap());
		
		ReflectionTestUtils.setField(unwrapProxy, "accountOperations", mockAccountOperations);
		ReflectionTestUtils.setField(unwrapProxy, "mailSender", mailSender);
	}
	
	@Test
	public void testUpdate() throws Exception {
		
		UserController unwrapProxy = (UserController) unwrapProxy(userController);
		ReflectionTestUtils.setField(unwrapProxy, "accountOperations", mockAccountOperations);
		
		UserOperations userOperationsMock = mock(UserOperations.class);
	
		when(mockAccountOperations.getUserOperations()).thenReturn(userOperationsMock);
		
		AccountUserDTO value = new AccountUserDTO();
		value.setProfile(new ProfileDTO("Lucas Martins", "lucas.gmmartins", RandomStringUtils.randomAlphanumeric(6)));
		
		when(userOperationsMock.update(anyLong(), anyString(), anyString(), anyString())).thenReturn(value);
		
		User lucas = user("lucas.martins").build();
		
		saveAll(lucas);
		login(lucas);
		
		RequestBuilder put = put("/user/%d", lucas.getId());
		put.formParam("name", "Lucas Martins");
		put.formParam("username", "lucas.gmmartins");
		put.formParam("photo", ImageMockUtil.getMockBase64Image());
		
		JsonNode json = put.expectedStatus(HttpStatus.OK).getJson();
		
		jsonAsserter(json)
			.assertEquals("$.profile.name", "Lucas Martins")
			.assertEquals("$.profile.username", "lucas.gmmartins")
			.assertNotNull("$.profile.photo");
		
		ReflectionTestUtils.setField(unwrapProxy, "accountOperations", accountOperations);
	}

	@Test
	public void testInviteFriend() throws Exception {
		
		UserController unwrapProxy = (UserController) unwrapProxy(userController);
		ReflectionTestUtils.setField(unwrapProxy, "mailSender", mockMailSender);
		
		doNothing().when(mockMailSender).send(anyString(), anyString(), anyString(), anyMap());
		
		User lucas = user("lucas.martins").build();
		saveAll();
		login(lucas);
		
		RequestBuilder post = post("/user/invite");
		post.formParam("email", "thediafrit@yahoo.com");
		
		JsonNode json = post.getJson();
		
		jsonAsserter(json)
			.assertEquals("invites", 1);
		
		verify(mockMailSender, times(1)).send(anyString(), anyString(), anyString(), anyMap());
		
		ReflectionTestUtils.setField(unwrapProxy, "mailSender", mailSender);
		
	}
	
	@Test
	public void testFailedWhenInvitesExceedLimit() throws Exception {
		
		UserController unwrapProxy = (UserController) unwrapProxy(userController);
		ReflectionTestUtils.setField(unwrapProxy, "mailSender", mockMailSender);
		
		doNothing().when(mockMailSender).send(anyString(), anyString(), anyString(), anyMap());
		
		User lucas = user("lucas.martins").invites(5).build();
		saveAll();
		login(lucas);
		
		RequestBuilder post = post("/user/invite").expectedStatus(HttpStatus.PRECONDITION_FAILED);
		post.formParam("email", "thediafrit@yahoo.com");
		
		post.getJson();
		
		verify(mockMailSender, times(0)).send(anyString(), anyString(), anyString(), anyMap());
		
		ReflectionTestUtils.setField(unwrapProxy, "mailSender", mailSender);
		
	}
}
