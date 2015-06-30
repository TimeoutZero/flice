package com.timeoutzero.flice.account.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import aleph.ChainPersistenceProvider;
import aleph.ContextUtil;
import aleph.PersistenceProvider;

@Configuration
public class AppTestProvider {

	@Bean
	public ContextUtil contextUtil() {
		return new ContextUtil();
	}

	@Bean
	public ChainPersistenceProvider chainPersistenceProvider() {
		return new ChainPersistenceProvider();
	}
	@Bean
	public PersistenceProvider persistenceProvider() {
		return new JpaPersistenceProvider();
	}
}
