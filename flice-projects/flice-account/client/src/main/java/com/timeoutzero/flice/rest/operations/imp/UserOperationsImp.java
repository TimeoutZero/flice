package com.timeoutzero.flice.rest.operations.imp;

import lombok.AllArgsConstructor;

import com.timeoutzero.flice.rest.Credentials;
import com.timeoutzero.flice.rest.dto.UserDTO;
import com.timeoutzero.flice.rest.operations.UserOperations;

@AllArgsConstructor
public class UserOperationsImp implements UserOperations {
	
	@SuppressWarnings("unused")
	private Credentials credentials;

	@Override
	public UserDTO getUser(String token) {
		return null;
	}
}
