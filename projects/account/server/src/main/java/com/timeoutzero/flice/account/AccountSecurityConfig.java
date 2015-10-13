package com.timeoutzero.flice.account;

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

import com.timeoutzero.flice.account.security.ApplicationKeyFilter;
import com.timeoutzero.flice.account.service.ProductAuthenticationProvider;

@Configuration
@EnableWebMvcSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AccountSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ApplicationKeyFilter applicationKeyFilter;
	
	@Autowired
	private ProductAuthenticationProvider productAuthenticatorProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.addFilterBefore(applicationKeyFilter, BasicAuthenticationFilter.class)
			.csrf().disable()
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(productAuthenticatorProvider);
	}
}
