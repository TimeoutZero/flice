package com.timeoutzero.flice.rest.operations.imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeoutzero.flice.rest.Credentials;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.operations.UserOperations;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserOperationsImp implements UserOperations {
	
	private static final String ENDPOINT = "/user";
	
	private Credentials credentials;
	private RestTemplate template;

	@Override
	public AccountUserDTO get(String token) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		
		return template.getForEntity(this.credentials.getUrl(ENDPOINT), AccountUserDTO.class, map).getBody();
	}

	@Override
	public AccountUserDTO create(String email, String password) {
		
		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		  
		HttpEntity<String> httpEntity = factoryApplicationJSONHttpEntity(map);
		
		return template.exchange(this.credentials.getUrl(ENDPOINT), HttpMethod.POST, httpEntity, AccountUserDTO.class).getBody();
	}

	private HttpEntity<String> factoryApplicationJSONHttpEntity(Map<String, String> map) {
		String content = null;
		try {
			content = new ObjectMapper().writeValueAsString(map);
		} catch (JsonProcessingException e) {
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);
		return httpEntity;
	}
}
