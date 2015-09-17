package com.timeoutzero.flice.rest.operations;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.timeoutzero.flice.rest.Credentials;
import com.timeoutzero.flice.rest.CustomErrorHandler;
import com.timeoutzero.flice.rest.operations.imp.TokenOperationsImp;
import com.timeoutzero.flice.rest.operations.imp.UserOperationsImp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountOperations {

	private boolean test;
	private TokenOperations tokenOperations;
	private UserOperations userOperations;
	private RestTemplate template;
	
	public AccountOperations(String url, String token) {
		
		Credentials credentials = new Credentials(url, token);
		
		setupRestTemplate(); 
	
		this.tokenOperations = new TokenOperationsImp(credentials, template);
		this.userOperations	 = new UserOperationsImp(credentials, template);
	}

	private void setupRestTemplate() {
		
		template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		template.setErrorHandler(new CustomErrorHandler());
	}
}
