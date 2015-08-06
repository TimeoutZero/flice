package com.timeoutzero.flice.rest.operations.mock;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import com.timeoutzero.flice.rest.dto.AccountUserDTO;

public class AccountMockBuilders {

	public static Map<String, AccountUserDTO> users = new HashMap<>();

	public static AccountUserDTO createMockUser(String username) {
		
		AccountUserDTO user = new AccountUserDTO();
		user.setId(Long.valueOf(RandomStringUtils.randomNumeric(3)));
		user.setUsername(username);
		
		users.put("default", user);
		
		return user;
	}
}
