package com.timeoutzero.flice.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.accountId = :accountId")
	User findByAccountId(@Param("accountId") Long accountId);
	
	@Query("SELECT uc FROM User u JOIN u.communitys uc WHERE u.id = :id")
	List<Community> findAllCommunityByUser(@Param("id") Long id);
	
}
