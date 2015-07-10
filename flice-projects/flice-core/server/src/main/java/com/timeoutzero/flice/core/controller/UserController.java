package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.security.CoreSecurityContext.getLoggedUser;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Topic;
import com.timeoutzero.flice.core.domain.UserCommunity;
import com.timeoutzero.flice.core.domain.UserTopic;
import com.timeoutzero.flice.core.repository.CommunityRepository;
import com.timeoutzero.flice.core.repository.TopicRepository;
import com.timeoutzero.flice.core.repository.UserCommunityRepository;
import com.timeoutzero.flice.core.repository.UserTopicRepository;

@RestController
@Transactional
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserTopicRepository userTopicRepository;
	
	@Autowired
	private UserCommunityRepository userCommunityRepository;
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private CommunityRepository communityRepository;
	
	@RequestMapping(value="/topic", method=PUT)
	public void followTopic(@RequestParam("topicId") Long topicId, @RequestParam("favorite") Boolean favorite){
		
		Topic topic = topicRepository.findByIdAndActiveTrue(topicId);
		UserTopic userTopic = userTopicRepository.findByUserAndTopic(getLoggedUser(), topic);
		
		if(userTopic == null){
			userTopic = new UserTopic();
		}
		
		userTopic.setFavorite(favorite);;
		userTopic.setUser(getLoggedUser());
		userTopic.setTopic(topic);
		
		userTopicRepository.save(userTopic);
		
	}

	@RequestMapping(value="/community", method=PUT)
	public void followCommunity(@RequestParam("communityId") Long communityId, @RequestParam("favorite") Boolean favorite){
		
		Community community = communityRepository.findByIdAndActiveTrue(communityId);
		UserCommunity userCommunity = userCommunityRepository.findByUserAndCommunity(getLoggedUser(), community);
		
		if(userCommunity == null){
			userCommunity = new UserCommunity();
		}
		
		userCommunity.setFavorite(favorite);;
		userCommunity.setUser(getLoggedUser());
		userCommunity.setCommunity(community);
		
		userCommunityRepository.save(userCommunity);
	}
	
}
