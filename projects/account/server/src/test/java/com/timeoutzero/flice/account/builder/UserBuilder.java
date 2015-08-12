package com.timeoutzero.flice.account.builder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.timeoutzero.flice.account.entity.User;

import aleph.AbstractBuilder;

public class UserBuilder extends AbstractBuilder<User> {

	public static UserBuilder user(String email) {
		return new UserBuilder().email(email).password("12345");
	}

	public UserBuilder email(String email) {
		return set("email", email);
	}

	public UserBuilder password(String password) {
		return set("password", new BCryptPasswordEncoder(5).encode("12345"));
	}
}
