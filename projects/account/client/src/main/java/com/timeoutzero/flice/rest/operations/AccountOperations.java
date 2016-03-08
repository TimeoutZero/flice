package com.timeoutzero.flice.rest.operations;

import org.springframework.web.client.RestTemplate;

import com.timeoutzero.flice.rest.operations.imp.TokenOperationsImp;
import com.timeoutzero.flice.rest.operations.imp.UserOperationsImp;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountOperations {

	private RestTemplate template;

	private TokenOperations tokenOperations;
	private UserOperations 	userOperations;
	 
	public AccountOperations(String url, String applicationSecretKey) {
		 
		FliceTemplate template = new FliceTemplate(url, applicationSecretKey);
	
		this.tokenOperations = new TokenOperationsImp(template);
		this.userOperations	 = new UserOperationsImp(template);
	}

}
