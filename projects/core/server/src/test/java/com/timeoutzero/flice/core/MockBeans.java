package com.timeoutzero.flice.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.enums.GrantType;
import com.timeoutzero.flice.rest.operations.AccountOperations;
import com.timeoutzero.flice.rest.operations.TokenOperations;
import com.timeoutzero.flice.rest.operations.UserOperations;

@Configuration
public class MockBeans {
	
	@Autowired
	private AccountOperations accountOperations;

	@PostConstruct
	public void mock() {
		
		accountOperations.setTokenOperations(new TokenOperations() {
			
			@Override
			public String create(String username, String password, GrantType grantType) {
				return null;
			}
			
			@Override
			public void authorize(String token) {
				
			}
		});
		
		accountOperations.setUserOperations(new UserOperations() {
			
			@Override
			public AccountUserDTO get(String token) {
				AccountUserDTO dto = new AccountUserDTO();
				dto.setId(1l);
				return dto;
			}
			
			@Override
			public AccountUserDTO create(String email, String password) {
				return null;
			}
		});
	}
}
