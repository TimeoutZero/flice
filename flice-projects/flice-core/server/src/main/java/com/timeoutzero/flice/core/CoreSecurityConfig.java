package com.timeoutzero.flice.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.timeoutzero.flice.core.security.CustomAuthenticationEntryPoint;
import com.timeoutzero.flice.core.security.CustomAuthenticationFailureHandler;
import com.timeoutzero.flice.core.security.CustomAuthenticationSuccessHandler;
import com.timeoutzero.flice.core.security.TokenAuthenticatorProvider;
import com.timeoutzero.flice.core.security.TokenFilter;

@Configuration
@EnableWebMvcSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class CoreSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticateFailureHandler;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired 
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Autowired
	private TokenAuthenticatorProvider tokenAuthenticatorProvider;
	
	@Autowired
	private TokenFilter tokenFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http
			.csrf().disable()
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.successHandler(customAuthenticationSuccessHandler)
				.failureHandler(customAuthenticateFailureHandler)
				.permitAll()
				.and()
			.exceptionHandling()
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.addFilterBefore(tokenFilter, BasicAuthenticationFilter.class);
		
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(tokenAuthenticatorProvider);
	}
}
