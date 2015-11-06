package com.timeoutzero.flice.rest.operations;

import java.util.List;

import com.timeoutzero.flice.rest.dto.AccountUserDTO;

public interface UserOperations {

	AccountUserDTO get(String token);
	List<AccountUserDTO> list(List<Long> ids);
	AccountUserDTO create(String email, String password);
}
