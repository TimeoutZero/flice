package com.timeoutzero.flice.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.timeoutzero.flice.core.domain.User;

public class CoreSecurityContext {

	public static User getLoggedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User) authentication.getPrincipal();
	}
}
