package com.timeoutzero.flice.core;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.timeoutzero.flice.rest.operations.AccountOperations;

@Configuration
public class CoreConfiguration {

	@Bean
	public AccountOperations accountOperations(@Value("${account.url}") String accountUrl, @Value("${account.token}") String accountClientId) {
		return new AccountOperations(accountUrl , accountClientId);
	}
	
	@Bean
	public AmazonS3Client amazonS3(@Value("${aws.access.token}") String accessToken, @Value("${aws.secret.key}") String secretKey) {
		return new AmazonS3Client(new BasicAWSCredentials(accessToken, secretKey));
	}
	
	@Bean
	@Profile("DEV")
	@Qualifier("awsEndpoint")
	public String awsEndpointDev(@Value("${mock.s3}") String url) {
		return url;
	}

	@Bean
	@Profile({ "QA", "PROD"})
	@Qualifier("awsEndpoint")
	public String awsEndpoint() {
		return "http://flice.s3.amazonaws.com";
	}
}
