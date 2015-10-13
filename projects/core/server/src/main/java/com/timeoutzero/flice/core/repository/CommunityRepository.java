package com.timeoutzero.flice.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.timeoutzero.flice.core.domain.Community;

public interface CommunityRepository extends CrudRepository<Community, Long>{

	Community findByIdAndActiveTrue(Long id);
	List<Community> findByActiveTrueAndVisibilityTrue();
	
}
