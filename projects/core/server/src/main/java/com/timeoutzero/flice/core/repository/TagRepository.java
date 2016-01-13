package com.timeoutzero.flice.core.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.timeoutzero.flice.core.domain.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {

	Tag findByName(String name);
	List<Tag> findByNameContaining(String name);
}
