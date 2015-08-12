package com.timeoutzero.flice.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.timeoutzero.flice.core.domain.Topic;

public interface TopicRepository extends CrudRepository<Topic, Long>{

	Topic findByIdAndActiveTrue(Long id);
	List<Topic> findByActiveTrue();
	
}
