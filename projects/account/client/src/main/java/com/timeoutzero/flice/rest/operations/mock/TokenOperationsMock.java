package com.timeoutzero.flice.rest.operations.mock;

import java.util.Map.Entry;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.enums.GrantType;
import com.timeoutzero.flice.rest.operations.TokenOperations;

public class TokenOperationsMock implements TokenOperations {

	@Override
	public void authorize(String token) {
		if (!AccountMockBuilders.users.containsKey(token)) {
			throw new RuntimeException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
		}
	}

	@Override
	public String create(String username, String password, String clientId, GrantType grantType) {
		
		String mockToken = RandomStringUtils.randomAlphanumeric(10);
		
		 for (Entry<String, AccountUserDTO> entry : AccountMockBuilders.users.entrySet()) {
			
			AccountUserDTO value = entry.getValue();
			
			boolean isSameEmail    = StringUtils.isNotBlank(value.getEmail()) && value.getEmail().equals(username);
			boolean isSameUsername = StringUtils.isNotBlank(value.getUsername()) && value.getUsername().equals(username);
			
			if( isSameEmail || isSameUsername) {
				AccountMockBuilders.users.put(mockToken, value);
				AccountMockBuilders.users.remove("default");
				break;
			}
		 }
		
		return mockToken;
	}
}
