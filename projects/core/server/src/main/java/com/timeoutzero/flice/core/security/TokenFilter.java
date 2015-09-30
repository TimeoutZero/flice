package com.timeoutzero.flice.core.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class TokenFilter extends GenericFilterBean {
	public static final String CUSTOM_HEADER_X_AUTH_TOKEN = "X-AUTH-TOKEN";

	@Autowired
	private AuthenticatorService authenticatorService;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request 	 = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		if (request.getCookies() != null) {
			Arrays.asList(request.getCookies()).stream().filter(c -> c.getName().equals("x-f-token")).forEach(c -> {
				System.out.println(c.toString());
			});
		}

		
		String token = request.getHeader(CUSTOM_HEADER_X_AUTH_TOKEN);
		UsernamePasswordAuthenticationToken authentication = authenticatorService.createAuthentication(token);
	
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
}
