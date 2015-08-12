package com.timeoutzero.flice.core.repository;

import org.springframework.data.repository.CrudRepository;

import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.domain.UserCommunity;

public interface UserCommunityRepository extends CrudRepository<UserCommunity, Long>{

	UserCommunity findByUserAndCommunity(User user, Community community);
	
}
