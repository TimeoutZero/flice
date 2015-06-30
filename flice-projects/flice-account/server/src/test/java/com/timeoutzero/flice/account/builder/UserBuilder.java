package com.timeoutzero.flice.account.builder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import aleph.AbstractBuilder;

import com.timeoutzero.flice.account.entity.User;

public class UserBuilder extends AbstractBuilder<User> {

	public static UserBuilder user() {
		return new UserBuilder().password("12345");
	}

	public UserBuilder email(String email) {
		return set("email", email);
	}

	public UserBuilder username(String username) {
		return set("username", username);
	}

	public UserBuilder password(String password) {
		return set("password", new BCryptPasswordEncoder(5).encode("12345"));
	}
}
