package com.timeoutzero.flice.core.repository;

import org.springframework.data.repository.CrudRepository;

import com.timeoutzero.flice.core.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByAccountId(Long id);
}
