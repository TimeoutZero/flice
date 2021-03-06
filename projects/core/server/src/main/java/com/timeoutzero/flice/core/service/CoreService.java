package com.timeoutzero.flice.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.repository.CommentRepository;
import com.timeoutzero.flice.core.repository.CommunityRepository;
import com.timeoutzero.flice.core.repository.TopicRepository;
import com.timeoutzero.flice.core.repository.UserRepository;
import com.timeoutzero.flice.core.security.CoreSecurityContext;

import lombok.Getter;

@Getter
@Service
public class CoreService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private CommunityRepository communityRepository;
	
	@Autowired
	private TopicRepository topicRepository;
	
	public User getLoggedUser() {
		return getUserRepository().findOne(CoreSecurityContext.getLoggedUser().getId());
	}
}
