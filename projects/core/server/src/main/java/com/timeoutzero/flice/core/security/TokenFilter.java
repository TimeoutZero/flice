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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class TokenFilter extends GenericFilterBean {

	private static Logger log = LoggerFactory.getLogger(TokenFilter.class);
	public static final String CUSTOM_COOKIE_X_FLICE_TOKEN = "c-f-token";

	@Autowired
	private AuthenticatorService authenticatorService;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request 	 = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String token = "";

		if (request.getCookies() != null) {
			
			Optional<Cookie> cookie = getCookieFromRequest(request); 
			
			if (cookie.isPresent()) {
				token = cookie.get().getValue();
			}
		}
		
		try {

			UsernamePasswordAuthenticationToken authentication = authenticatorService.createAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
 		} catch (Exception e) {
			log.error("Error from authentication service: {}", e.getLocalizedMessage());
			removeInvalidCookie(request, response);
		}
	
		chain.doFilter(request, response);
	}

	private void removeInvalidCookie(HttpServletRequest request, HttpServletResponse response) {
		Optional<Cookie> optional = getCookieFromRequest(request); 
		if (optional.isPresent()) {
		
			Cookie cookie = optional.get();
			cookie.setValue(null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	}

	private Optional<Cookie> getCookieFromRequest(HttpServletRequest request) {
		Optional<Cookie> cookie = Arrays.asList(request.getCookies()).stream()
				.filter(c -> c.getName().equals(CUSTOM_COOKIE_X_FLICE_TOKEN))
				.findFirst();
		return cookie;
	}
}
