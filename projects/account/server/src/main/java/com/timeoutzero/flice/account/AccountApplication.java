package com.timeoutzero.flice.account;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AccountApplication {

	public static void main(String[] args) { 
		new SpringApplicationBuilder(AccountApplication.class).run(args);
	}
}
