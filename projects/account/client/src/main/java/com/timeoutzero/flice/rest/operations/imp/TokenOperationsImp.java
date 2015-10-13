package com.timeoutzero.flice.rest.operations.imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.util.UriComponentsBuilder;

import com.timeoutzero.flice.rest.operations.FliceTemplate;
import com.timeoutzero.flice.rest.operations.TokenOperations;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TokenOperationsImp implements TokenOperations {

	private static final String ENDPOINT = "/auth/token";

	private static final String PARAMETER_TOKEN    = "token";
	private static final String PARAMETER_USERNAME = "username";
	private static final String PARAMETER_PASSWORD = "password"; 

	private FliceTemplate template;
	
	@Override
	public void authorize(String token) {
		
		String uri = UriComponentsBuilder.fromUriString(ENDPOINT)
			.queryParam(PARAMETER_TOKEN, token)
			.build().toUriString();
		
		this.template.get(uri, String.class);
	}
	
	@Override
	public String create(String username, String password) {
		
		Map<String, String> parameters = new HashMap<>();
		parameters.put(PARAMETER_USERNAME, username);
		parameters.put(PARAMETER_PASSWORD, password);
		 
		return this.template.post(ENDPOINT, parameters, String.class);
	}
}
