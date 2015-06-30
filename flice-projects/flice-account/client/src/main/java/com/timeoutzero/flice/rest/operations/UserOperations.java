package com.timeoutzero.flice.rest.operations;

import com.timeoutzero.flice.rest.dto.UserDTO;

public interface UserOperations {

	UserDTO getUser(String token);
}
