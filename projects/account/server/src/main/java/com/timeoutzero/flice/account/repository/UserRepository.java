package com.timeoutzero.flice.account.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.timeoutzero.flice.account.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT CASE WHEN count(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
	boolean existByEmail(@Param("email") String email);
	
	@Query("SELECT CASE WHEN count(u) > 0 THEN true ELSE false END FROM User u WHERE u.profile.username = :username")
	boolean existByUsername(@Param("username") String username);
	
	
	User findByEmail(String email);

}
