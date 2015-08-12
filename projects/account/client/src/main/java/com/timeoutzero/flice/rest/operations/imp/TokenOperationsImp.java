package com.timeoutzero.flice.rest.operations.imp;

import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
		
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
		request.add("username", username);
		request.add("password", password);
		request.add("clientId", clientId);
		request.add("grantType", grantType.toString()); 
		
		ResponseEntity<String> response = template.postForEntity(this.credentials.getUrl("/auth/token"), request, String.class);
		
		return response.getBody();
	}

}
