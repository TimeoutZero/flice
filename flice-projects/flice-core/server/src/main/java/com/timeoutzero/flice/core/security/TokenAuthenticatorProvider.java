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
import com.timeoutzero.flice.rest.dto.UserDTO;
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
		
		UserDTO result = accountOperations.getUserOperations().getUser(token);
		User user = service.getUserRepository().findByAccountId(result.getId());
		
		return new PreAuthenticatedAuthenticationToken(user, token);
	}

	private String getValidToken(Authentication authentication) {
		String token = authentication.getCredentials().toString();
		
		if(StringUtils.isBlank(token)) {
			throw new BadCredentialsException(EXCEPTION_INVALID_TOKEN);
		}
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TokenAuthentication.class.isAssignableFrom(authentication);
	}
}
