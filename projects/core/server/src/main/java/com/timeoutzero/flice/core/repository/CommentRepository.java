package com.timeoutzero.flice.core.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.timeoutzero.flice.core.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>, PagingAndSortingRepository<Comment, Long>{

	Comment findById(Long id);
	List<Comment> findByTopicId(Long topicId, Pageable page);
	
}
