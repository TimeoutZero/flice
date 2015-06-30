package com.timeoutzero.flice.core.service;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timeoutzero.flice.core.repository.CommentRepository;
import com.timeoutzero.flice.core.repository.CommunityRepository;
import com.timeoutzero.flice.core.repository.TopicRepository;
import com.timeoutzero.flice.core.repository.UserRepository;

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
	
}
