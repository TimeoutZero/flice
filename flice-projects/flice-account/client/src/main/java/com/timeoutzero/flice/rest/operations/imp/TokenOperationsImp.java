package com.timeoutzero.flice.rest.operations.imp;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.timeoutzero.flice.rest.Credentials;
import com.timeoutzero.flice.rest.enums.GrantType;
import com.timeoutzero.flice.rest.operations.TokenOperations;

@AllArgsConstructor
public class TokenOperationsImp implements TokenOperations {
	
	private Credentials credentials;
	
	@Override
	public void authorize(String token) {
		
		ResponseEntity<Object> response = new RestTemplate().getForEntity(this.credentials.getUrl(), null);
		
		if(!response.getStatusCode().equals(HttpStatus.OK)) {
			throw new RuntimeException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
		}
	}
	
	@Override
	public String create(String username, String password, Long clientId, GrantType grantType) {
		
		Map<String, Object> request = new HashMap<>();
		request.put("username", username);
		request.put("password", password);
		request.put("clientId", clientId);
		request.put("grantType", grantType.toString()); 
		
		ResponseEntity<String> response = null;
		
		try {

			response = new RestTemplate().postForEntity(this.credentials.getUrl(), request, String.class);
			
			if(!response.getStatusCode().equals(HttpStatus.CREATED)) {
				throw new RuntimeException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
			}
			
		} catch (Exception e) {
			throw new RuntimeException(HttpStatus.UNAUTHORIZED.getReasonPhrase());

		}
		
		return response.getBody();
	}
}
