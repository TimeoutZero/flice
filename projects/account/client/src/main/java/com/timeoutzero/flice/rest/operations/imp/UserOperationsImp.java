package com.timeoutzero.flice.rest.operations.imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.util.UriComponentsBuilder;

import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.operations.FliceTemplate;
import com.timeoutzero.flice.rest.operations.UserOperations;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserOperationsImp implements UserOperations {
	
	private static final String ENDPOINT = "/user";
	
	private static final String PARAMETER_TOKEN 	= "token";
	private static final String PARAMETER_EMAIL 	= "email";
	private static final String PARAMETER_PASSWORD  = "password";
	
	private FliceTemplate template;

	@Override
	public AccountUserDTO get(String token) {
		
		String uri = UriComponentsBuilder.fromUriString(ENDPOINT)
				.queryParam(PARAMETER_TOKEN, token)
				.build().toUriString();
		
		return template.get(uri, AccountUserDTO.class);
	}

	@Override
	public AccountUserDTO create(String email, String password) {
		
		Map<String, String> parameters = new HashMap<>();
		parameters.put(PARAMETER_EMAIL, email);
		parameters.put(PARAMETER_PASSWORD, password);
		  
		return template.post(ENDPOINT, parameters, AccountUserDTO.class);
	}
}
