package com.timeoutzero.flice.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.timeoutzero.flice.core.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>{

	Comment findByIdAndActiveTrue(Long id);
	List<Comment> findByActiveTrue();
	List<Comment> findByTopicIdAndActiveTrue(Long topicId);
	
}
