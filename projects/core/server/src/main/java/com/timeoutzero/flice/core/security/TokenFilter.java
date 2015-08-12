package com.timeoutzero.flice.core.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class TokenFilter extends GenericFilterBean {

	//private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);
	
	public static final String CUSTOM_HEADER_X_AUTH_TOKEN = "X-Auth-Token"; 
	
//	@Autowired
//	private AccountOperations accountOperations;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request 	 = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String token = request.getHeader(CUSTOM_HEADER_X_AUTH_TOKEN);
		
		SecurityContextHolder.getContext().setAuthentication(new TokenAuthentication(token));
		chain.doFilter(request, response);
	}

//	private boolean authorize(String token) throws IOException {
//		
//		if(StringUtils.isBlank(token)) {
//			return false;
//		}
//		
//		log.info("Attemping to authenticate by X-Auth-Token. Token: {}", token);
//		
//		try {
//			
//			accountOperations.getTokenOperations().authorize(token);
//			
//		} catch (Exception e) {
//			return false;
//		}
//		
//		return true;
//	}
}
