package com.timeoutzero.flice.rest.operations.imp;

import java.util.HashMap;
import java.util.Map;

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
	 
	private static final String ENDPOINT = "/auth/token";
	
	private Credentials credentials;
	private RestTemplate template;
	
	@Override
	public void authorize(String token) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		
		template.getForEntity(this.credentials.getUrl(ENDPOINT), null, map);
	}
	
	@Override
	public String create(String username, String password, GrantType grantType) {
		
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
		request.add("username", username);
		request.add("password", password);
		request.add("token", credentials.getToken());
		request.add("grantType", grantType.toString()); 
		
		ResponseEntity<String> response = template.postForEntity(this.credentials.getUrl(ENDPOINT), request, String.class);
		
		return response.getBody();
	}

}
