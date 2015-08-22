package com.timeoutzero.flice.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class CoreApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(CoreApplication.class).run(args);
	}

	@Bean
	public HttpPutFormContentFilter httpPutFormContentFilter() {
		return new HttpPutFormContentFilter();
	}
}
