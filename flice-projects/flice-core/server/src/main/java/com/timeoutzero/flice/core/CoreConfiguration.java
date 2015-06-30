package com.timeoutzero.flice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.timeoutzero.flice.rest.operations.AccountOperations;

@Configuration
public class CoreConfiguration {

	private static final String PROPERTY_ACCOUNT_TEST = "account.test"; 
//
//	@Value("${account.url}")
//	private String url;
//	
//	@Value("${account.client.id}")
//	private String clientId;
//	
//	@Value("${account.secret.key}")
//	private String secretKey;
	
	@Autowired
	private Environment env;
	
	@Bean
	public AccountOperations accountOperations() {
		
		Boolean test = env.getRequiredProperty(PROPERTY_ACCOUNT_TEST, Boolean.class);
		AccountOperations operations = new AccountOperations("http://localhost:9090/account/api" , "1", "1", test);
		
		return operations;
	}
}
