package com.timeoutzero.flice.core.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class TokenAuthentication implements Authentication {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object obj;

	public TokenAuthentication(Object obj) {
		this.obj = obj;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>(0);
	}

	@Override
	public Object getCredentials() {
		return obj;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return false;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
	}

	@Override
	public String getName() {
		return null;
	}
	
	public void setObj(Object obj) {
		this.obj = obj;
	}
}