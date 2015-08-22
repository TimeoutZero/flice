package com.timeoutzero.flice.rest.operations;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.timeoutzero.flice.rest.Credentials;
import com.timeoutzero.flice.rest.CustomErrorHandler;
import com.timeoutzero.flice.rest.operations.imp.TokenOperationsImp;
import com.timeoutzero.flice.rest.operations.imp.UserOperationsImp;
import com.timeoutzero.flice.rest.operations.mock.TokenOperationsMock;
import com.timeoutzero.flice.rest.operations.mock.UserOperationsMock;

import lombok.Getter;

@Getter
public class AccountOperations {

	private boolean test;
	private TokenOperations tokenOperations;
	private UserOperations userOperations;
	private RestTemplate template;
	
	public AccountOperations(String url, String token, boolean test) {
		
		Credentials credentials = new Credentials(url, token);
		
		setupRestTemplate(); 
		
		if(test) {
			this.tokenOperations = new TokenOperationsMock();
			this.userOperations  = new UserOperationsMock();
		} else {
			this.tokenOperations = new TokenOperationsImp(credentials, template);
			this.userOperations	 = new UserOperationsImp(credentials, template);
		}
	}

	private void setupRestTemplate() {
		
		template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		template.setErrorHandler(new CustomErrorHandler());
	}
}
