package com.timeoutzero.flice.account.repository;

import org.springframework.data.repository.CrudRepository;

import com.timeoutzero.flice.account.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);
	User findByUsername(String username);

}
