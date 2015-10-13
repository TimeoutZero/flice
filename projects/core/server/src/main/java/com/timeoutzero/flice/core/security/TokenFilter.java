package com.timeoutzero.flice.core.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class TokenFilter extends GenericFilterBean {

	public static final String CUSTOM_COOKIE_X_FLICE_TOKEN = "c-f-token";

	@Autowired
	private AuthenticatorService authenticatorService;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request 	 = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String token = "";

		if (request.getCookies() != null) {
			
			Optional<Cookie> cookie = Arrays.asList(request.getCookies()).stream()
					.filter(c -> c.getName().equals(CUSTOM_COOKIE_X_FLICE_TOKEN))
					.findFirst();
			
			if (cookie.isPresent()) {
				token = cookie.get().getValue();
			}
		}
		
 		UsernamePasswordAuthenticationToken authentication = authenticatorService.createAuthentication(token);
	
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
}
