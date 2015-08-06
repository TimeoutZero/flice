package com.timeoutzero.flice.core.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.service.CoreService;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.operations.AccountOperations;

@Component
public class TokenAuthenticatorProvider implements AuthenticationProvider {
	
	private static final String EXCEPTION_INVALID_TOKEN = "invalid.token"; 
	
	@Autowired
	private AccountOperations accountOperations;
	
	@Autowired
	private CoreService service;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		if(authentication.isAuthenticated()) {
			return authentication;
		}
		
		String token = getValidToken(authentication); 
		
		AccountUserDTO result = accountOperations.getUserOperations().get(token);
		User user = service.getUserRepository().findByAccountId(result.getId());
		
		return new PreAuthenticatedAuthenticationToken(user, token);
	}

	private String getValidToken(Authentication authentication) {
		
		Object credentials = authentication.getCredentials();
		
		if(credentials == null || StringUtils.isBlank(credentials.toString())) {
			throw new BadCredentialsException(EXCEPTION_INVALID_TOKEN);
		}
		
		return credentials.toString();
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TokenAuthentication.class.isAssignableFrom(authentication);
	}
}
