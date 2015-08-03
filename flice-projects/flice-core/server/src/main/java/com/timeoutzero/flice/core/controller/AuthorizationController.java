package com.timeoutzero.flice.core.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.rest.operations.AccountOperations;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
	
	@Autowired
	private AccountOperations accountOperations;
	
	@RequestMapping(value =  "/login", method = POST)
	public Cookie name() {
		
		accountOperations.getTokenOperations().create(username, password, clientId, grantType)
		return null;
	}
}
