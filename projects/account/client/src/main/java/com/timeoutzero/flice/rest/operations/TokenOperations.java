package com.timeoutzero.flice.rest.operations;

public interface TokenOperations {

	public void authorize(String token); 
	public String create(String username, String password);

}