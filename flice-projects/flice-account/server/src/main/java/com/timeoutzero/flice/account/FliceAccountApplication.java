package com.timeoutzero.flice.account;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FliceAccountApplication {

	public static void main(String[] args) { 
		new SpringApplicationBuilder(FliceAccountApplication.class).run(args);
	}
}
