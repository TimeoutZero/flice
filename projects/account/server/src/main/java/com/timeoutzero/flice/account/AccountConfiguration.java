package com.timeoutzero.flice.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AccountConfiguration {

	private static final String FLICE_ACCOUNT_CLIENT_KEY = "flice.account.client.key";
	
	@Autowired
	private Environment env;
	
	@Bean
	public String clientKey() {
		return env.getRequiredProperty(FLICE_ACCOUNT_CLIENT_KEY);
	}
}
