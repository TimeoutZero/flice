package com.timeoutzero.flice.rest.operations.mock;

import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.operations.UserOperations;

public class UserOperationsMock implements UserOperations {

	@Override
	public AccountUserDTO get(String token) {
		return AccountMockBuilders.users.get(token);
	}

	@Override
	public AccountUserDTO create(String email, String password) {
		return AccountMockBuilders.createMockUser(email);
	}
}
