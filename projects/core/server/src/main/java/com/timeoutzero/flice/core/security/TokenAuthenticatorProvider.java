package com.timeoutzero.flice.core.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticatorProvider implements AuthenticationProvider {

	private static final String EXCEPTION_INVALID_TOKEN = "invalid.token";

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		if (authentication.isAuthenticated()) {
			return authentication;
		}

		if (authentication.getPrincipal() == null) {
			throw new BadCredentialsException(EXCEPTION_INVALID_TOKEN);
		}

		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
