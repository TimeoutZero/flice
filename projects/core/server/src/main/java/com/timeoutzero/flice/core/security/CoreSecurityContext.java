package com.timeoutzero.flice.core.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.timeoutzero.flice.core.domain.User;

public class CoreSecurityContext {

	public static User getLoggedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User) authentication.getPrincipal();
	}
	
	public static List<String> getLoggedUserAuthoritys() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

	}
}
