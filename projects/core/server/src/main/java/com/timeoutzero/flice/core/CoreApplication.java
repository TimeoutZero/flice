package com.timeoutzero.flice.core;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.timeoutzero.flice.core.exception.WebException;

@SpringBootApplication
public class CoreApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(CoreApplication.class).run(args);
	}

	@Bean
	public HttpPutFormContentFilter httpPutFormContentFilter() {
		return new HttpPutFormContentFilter();
	}
	
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new HandlerExceptionResolver() {

			@Override
			public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			    Exception ex) {
				if (ex.getClass().isAssignableFrom(BindException.class)) {
					BindException bindex = (BindException) ex;
					final StringBuilder message = new StringBuilder();
					bindex.getAllErrors().forEach(a -> message.append(a.getDefaultMessage()).append("\n"));
					try {
						response.sendError(HttpStatus.BAD_REQUEST.value(), message.toString());
					} catch (IOException e) {
						return null;
					}
					return new ModelAndView();
				}
				if (ex instanceof WebException) {
					WebException webex = (WebException) ex;
					try {
						response.sendError(webex.getStatus().value(), ex.getMessage());
					} catch (IOException e) {
						return null;
					}
					return new ModelAndView();
				}
				return null;
			}
		});
		
		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}

}