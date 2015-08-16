package com.timeoutzero.flice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.timeoutzero.flice.rest.operations.AccountOperations;

@Configuration
public class CoreConfiguration {

	private static final String PROPERTY_ACCOUNT_TEST = "account.test"; 

	@Value("${account.url}")
	private String url;

	@Value("${account.token}")
	private String clientId;

	@Autowired
	private Environment env;
	
	@Bean
	public AccountOperations accountOperations() {
		
		Boolean test = env.getRequiredProperty(PROPERTY_ACCOUNT_TEST, Boolean.class);
		AccountOperations operations = new AccountOperations("http://account:8080/account/api" , "1", test);
		
		return operations;
	}
}
