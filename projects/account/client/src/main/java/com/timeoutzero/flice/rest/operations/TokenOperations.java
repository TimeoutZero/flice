package com.timeoutzero.flice.rest.operations;

import com.timeoutzero.flice.rest.enums.GrantType;

public interface TokenOperations {

	public void authorize(String token); 
	public String create(String username, String password, GrantType grantType);

}