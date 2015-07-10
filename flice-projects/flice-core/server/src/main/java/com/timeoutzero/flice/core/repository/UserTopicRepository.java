package com.timeoutzero.flice.core.repository;

import org.springframework.data.repository.CrudRepository;

import com.timeoutzero.flice.core.domain.Topic;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.domain.UserTopic;

public interface UserTopicRepository extends CrudRepository<UserTopic, Long>{

	UserTopic findByUserAndTopic(User user, Topic topic);
	
}
