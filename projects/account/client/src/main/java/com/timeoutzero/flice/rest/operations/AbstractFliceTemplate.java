package com.timeoutzero.flice.rest.operations;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractFliceTemplate {

	private String url;
	private String applicationSecretKey;

	private static final String X_FLICE_TOKEN = "X-FLICE-TOKEN";
	
	public String getUrl(String endpoint) {
		
		StringBuilder builderURL = new StringBuilder(url);
		builderURL.append(endpoint);
		
		return builderURL.toString();
	}
	
	public HttpEntity<?> getHttpEntity(Object request) { 

		MultiValueMap<String, String> headers = getDefaultHeaders();
		
		String content = null;
		try {
			content = new ObjectMapper().writeValueAsString(request);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return new HttpEntity<>(content, headers);
	}
	
	public HttpEntity<?> getHttpEntity() { 
		
		HttpHeaders headers = getDefaultHeaders();
		return new HttpEntity<>(null, headers);
	}

	private HttpHeaders getDefaultHeaders() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(X_FLICE_TOKEN, this.applicationSecretKey);
		
		return headers;
	}
}
