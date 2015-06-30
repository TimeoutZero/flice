package com.timeoutzero.flice.rest.operations;

import com.timeoutzero.flice.rest.enums.GrantType;

public interface TokenOperations {

	public abstract void authorize(String token); 
	public abstract String create(String username, String password, Long clientId, GrantType grantType);

}