package com.timeoutzero.flice.core.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.enums.Role;
import com.timeoutzero.flice.core.service.CoreService;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.operations.AccountOperations;

@Service
public class AuthenticatorService {

	@Autowired
	private AccountOperations accountOperations;

	@Autowired
	private CoreService service;

	@Transactional
	public UsernamePasswordAuthenticationToken createAuthentication(String token) {

		AccountUserDTO result = getAccountByToken(token);

		User user = null;
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		if (result != null) {

			user = service.getUserRepository().findByAccountId(result.getId());
			authorities = user.getRoles().stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());

		} else {
			authorities.add(new SimpleGrantedAuthority(Role.ANONYMOUS));
		}

		return new UsernamePasswordAuthenticationToken(user, token, authorities);
	}

	private AccountUserDTO getAccountByToken(String token) {

		if (StringUtils.isNotBlank(token)) {

			accountOperations.getTokenOperations().authorize(token);
			return accountOperations.getUserOperations().get(token);
		}

		return null;
	}
}
