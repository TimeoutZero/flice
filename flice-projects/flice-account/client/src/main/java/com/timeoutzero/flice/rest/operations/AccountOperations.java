package com.timeoutzero.flice.rest.operations;

import lombok.Getter;

import com.timeoutzero.flice.rest.Credentials;
import com.timeoutzero.flice.rest.operations.imp.TokenOperationsImp;
import com.timeoutzero.flice.rest.operations.imp.UserOperationsImp;
import com.timeoutzero.flice.rest.operations.mock.TokenOperationsMock;
import com.timeoutzero.flice.rest.operations.mock.UserOperationsMock;

@Getter
public class AccountOperations {

	private boolean test;
	private TokenOperations tokenOperations;
	private UserOperations userOperations;
	
	public AccountOperations(String url, String clientId, String secretKey, boolean test) {
		
		Credentials credentials = new Credentials(url, clientId, secretKey);
		
		if(test) {
			this.tokenOperations = new TokenOperationsMock();
			this.userOperations  = new UserOperationsMock();
		} else {
			this.tokenOperations = new TokenOperationsImp(credentials);
			this.userOperations	 = new UserOperationsImp(credentials);
		}
	}
}
