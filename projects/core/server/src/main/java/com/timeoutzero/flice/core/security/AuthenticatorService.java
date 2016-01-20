package com.timeoutzero.flice.core.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.service.CoreService;
import com.timeoutzero.flice.rest.AccountException;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.operations.AccountOperations;

@Service
public class AuthenticatorService {

	private static final String EXCEPTION_BLANK_TOKEN = "blank.token"; 

	@Autowired
	private CoreService service;

	@Autowired
	private AccountOperations accountOperations;

	@Transactional(readOnly = true)
	public UsernamePasswordAuthenticationToken createAuthentication(String token) throws BadCredentialsException {

		AccountUserDTO result = getAccountByToken(token);

		User user = service.getUserRepository().findByAccountId(result.getId());
		user.setProfile(result.getProfile());
		
		Collection<SimpleGrantedAuthority> authorities = user.getRoles().stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

 		return new UsernamePasswordAuthenticationToken(user, token, authorities);
	}

	private AccountUserDTO getAccountByToken(String token) {

		if (StringUtils.isBlank(token)) {
			throw new AccountException(HttpStatus.UNAUTHORIZED, EXCEPTION_BLANK_TOKEN);
		}
		
		accountOperations.getTokenOperations().authorize(token);
		return accountOperations.getUserOperations().get(token);
	}
}
