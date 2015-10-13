package com.timeoutzero.flice.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.timeoutzero.flice.account.entity.Product;
import com.timeoutzero.flice.account.exception.AccountException;
import com.timeoutzero.flice.account.repository.ProductRepository;

@Component
public class ProductAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String principal = (String) authentication.getPrincipal();
		Product issuer = productRepository.findByToken(principal);
		
		if(issuer == null) {
			throw new AccountException(HttpStatus.UNAUTHORIZED, "issuer.not.found");
		}
		
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
