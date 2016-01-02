package com.timeoutzero.flice.core.security;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;

@Component
public class CoreSecurityContext {
	
	public static User getLoggedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User) authentication.getPrincipal();
	}
	
	public static List<String> getLoggedUserAuthoritys() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}
	
	public static boolean isOwner(Optional<AccountUserDTO> optional) {
		return getLoggedUser().getAccountId().equals(optional.get().getId());
	}
	
	public static boolean isOwner(Long id) {
		return getLoggedUser().getAccountId().equals(id);
	}

}
