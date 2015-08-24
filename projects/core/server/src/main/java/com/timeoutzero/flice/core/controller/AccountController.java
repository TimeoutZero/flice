package com.timeoutzero.flice.core.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.dto.UserDTO;
import com.timeoutzero.flice.core.form.UserForm;
import com.timeoutzero.flice.core.service.CoreService;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.enums.GrantType;
import com.timeoutzero.flice.rest.operations.AccountOperations;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	private static final String CUSTOM_HEADER_X_FLICE_TOKEN = "x-f-token";
	private static final int COOKIE_EXPIRE_SECONDS = 3600;
	private static final int COOKIE_EXPIRE_DAYS = 15; 
	
	@Autowired
	private CoreService services;
	
	@Autowired
	private AccountOperations accountOperations;
	
	@RequestMapping(value = "/user", method = POST)
	public UserDTO create(@RequestBody @Valid UserForm form) {
		
		AccountUserDTO userAccountDTO = accountOperations.getUserOperations().create(form.getEmail(), form.getPassword());
		
		User user = new User(); 
		user.setAccountId(userAccountDTO.getId());
		user.setEmail(userAccountDTO.getEmail());
		
		services.getUserRepository().save(user);
		
		return new UserDTO(user);
	}

	@RequestMapping(value = "/token", method = POST)
	public void createToken(@RequestBody @Valid UserForm form, HttpServletResponse response) {
		
		String token = accountOperations.getTokenOperations().create(form.getEmail(), form.getPassword(), GrantType.PASSWORD);
		
		Cookie cookie = new Cookie(CUSTOM_HEADER_X_FLICE_TOKEN, token);
		cookie.setMaxAge(COOKIE_EXPIRE_SECONDS * COOKIE_EXPIRE_DAYS);
		cookie.setHttpOnly(true);
		
		response.addCookie(cookie);
		
	}
}
