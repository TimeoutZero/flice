package com.timeoutzero.flice.account.repository;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AccountRepository {

	@Autowired
	private UserRepository userRepository;
}
