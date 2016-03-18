package com.timeoutzero.flice.account.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.timeoutzero.flice.account.entity.Product;
import com.timeoutzero.flice.account.entity.User;

public class Compose {
	
	public static Product product(String name) {
		return Product.builder().name(name).token(RandomStringUtils.randomAlphanumeric(5)).build();
	}

	public static User.UserBuilder user(String email) {
		return User.builder().email(email).password(new BCryptPasswordEncoder().encode("12345"));
	}
}
