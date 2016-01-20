package com.timeoutzero.flice.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Community.Privacy;

public interface CommunityRepository extends CrudRepository<Community, Long>{

	Community findById(Long id);
	List<Community> findByPrivacy(Privacy privacy);
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Community c JOIN c.members cm WHERE c.id = :communityId AND cm.id = :userId")
	boolean isMember(@Param("communityId") Long communityId, @Param("userId") Long userId);
	
}
