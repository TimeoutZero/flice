package com.timeoutzero.flice.rest.operations.imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.timeoutzero.flice.rest.Credentials;
import com.timeoutzero.flice.rest.enums.GrantType;
import com.timeoutzero.flice.rest.operations.TokenOperations;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TokenOperationsImp implements TokenOperations {
	 
	private Credentials credentials;
	private RestTemplate template;
	
	@Override
	public void authorize(String token) {
		
		template.getForEntity(this.credentials.getUrl("/auth"), null);
	}
	
	@Override
	public String create(String username, String password, String clientId, GrantType grantType) {
		
		Map<String, Object> request = new HashMap<>();
		request.put("username", username);
		request.put("password", password);
		request.put("clientId", clientId);
		request.put("grantType", grantType.toString()); 
		
		ResponseEntity<String> response = template.postForEntity(this.credentials.getUrl("/token"), request, String.class);
		
		return response.getBody();
	}

}
