package com.timeoutzero.flice.rest.operations;

import java.util.List;

import com.timeoutzero.flice.rest.dto.AccountUserDTO;

public interface UserOperations {

	AccountUserDTO get(String token);

	AccountUserDTO create(String email, String password);
	
	AccountUserDTO update(Long accountId, String name, String username, String description, String photo);
	
	AccountUserDTO get(Long id);
	
	List<AccountUserDTO> list(List<Long> ids);
	
}
