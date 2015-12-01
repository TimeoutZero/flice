package com.timeoutzero.flice.core.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.timeoutzero.flice.core.domain.Topic;

public interface TopicRepository extends CrudRepository<Topic, Long>, PagingAndSortingRepository<Topic, Long>{

	Topic findById(Long id);
	
	List<Topic> findByCommunityId(Long communityId, Pageable page);
	
}
