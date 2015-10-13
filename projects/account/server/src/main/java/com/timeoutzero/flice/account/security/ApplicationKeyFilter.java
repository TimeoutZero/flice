package com.timeoutzero.flice.account.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class ApplicationKeyFilter extends GenericFilterBean { 
 
	public static final String HEADER_X_FLICE_TOKEN = "X-FLICE-TOKEN";
	
	private static final String EXCEPTION_INVALID_TOKEN 	= "invalid.token";

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		
		String token = request.getHeader(HEADER_X_FLICE_TOKEN);
		 
		if(StringUtils.isBlank(token)){
			throw new BadCredentialsException(EXCEPTION_INVALID_TOKEN);
		}
		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
		
		chain.doFilter(request, servletResponse);
	}
}
