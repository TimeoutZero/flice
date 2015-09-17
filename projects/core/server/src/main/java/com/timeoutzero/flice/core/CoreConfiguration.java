package com.timeoutzero.flice.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.timeoutzero.flice.rest.operations.AccountOperations;

@Configuration
public class CoreConfiguration {

	@Bean
	public AccountOperations accountOperations(@Value("${account.url}") String accountUrl, @Value("${account.token}") String accountClientId) {
		return new AccountOperations(accountUrl , accountClientId);
	}
}
