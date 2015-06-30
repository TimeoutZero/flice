package com.timeoutzero.flice.rest.operations.mock;

import com.timeoutzero.flice.rest.dto.UserDTO;
import com.timeoutzero.flice.rest.operations.UserOperations;

public class UserOperationsMock implements UserOperations {

	@Override
	public UserDTO getUser(String token) {
		return AccountMockBuilders.users.get(token);
	}
}
