package com.timeoutzero.flice.rest.operations;

import java.util.List;

import com.timeoutzero.flice.rest.dto.AccountUserDTO;

public interface UserOperations {

	AccountUserDTO get(String token);

	AccountUserDTO create(String email, String password);
	
	List<AccountUserDTO> list(List<Long> ids);
	
}
