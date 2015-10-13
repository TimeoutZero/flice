package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.security.CoreSecurityContext.getLoggedUser;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Topic;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.domain.UserCommunity;
import com.timeoutzero.flice.core.domain.UserTopic;
import com.timeoutzero.flice.core.dto.UserDTO;
import com.timeoutzero.flice.core.enums.Role;
import com.timeoutzero.flice.core.form.UserForm;
import com.timeoutzero.flice.core.repository.CommunityRepository;
import com.timeoutzero.flice.core.repository.TopicRepository;
import com.timeoutzero.flice.core.repository.UserCommunityRepository;
import com.timeoutzero.flice.core.repository.UserRepository;
import com.timeoutzero.flice.core.repository.UserTopicRepository;
import com.timeoutzero.flice.core.util.AuthenticatorCookieHandler;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.operations.AccountOperations;

@RestController
@Transactional
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserTopicRepository userTopicRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserCommunityRepository userCommunityRepository;
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private CommunityRepository communityRepository;
	
	@Autowired
	private AccountOperations accountOperations;
	
	@Autowired
	private AuthenticatorCookieHandler authenticatorCookieHandler;
	
	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(value = "/me", method = GET)
	public UserDTO me() {
		return new UserDTO(getLoggedUser());
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = POST)
	public UserDTO create(@RequestBody @Valid UserForm form) {
		
		AccountUserDTO userAccountDTO = accountOperations.getUserOperations().create(form.getEmail(), form.getPassword());
		
		User user = new User(); 
		user.setAccountId(userAccountDTO.getId());
		user.setEmail(userAccountDTO.getEmail());
		user.getRoles().add(Role.USER);
		
		userRepository.save(user);
		 
		return new UserDTO(user);
	}

	@RequestMapping(value = "/token", method = POST)
	public void createToken(@RequestBody @Valid UserForm form, HttpServletRequest request, HttpServletResponse response) {
		
		String token = accountOperations.getTokenOperations().create(form.getEmail(), form.getPassword());
		response.addCookie(authenticatorCookieHandler.retrieveCookie(request, token));
	}
	
	@Secured({ Role.USER, Role.ADMIN })
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

	@Secured({ Role.USER, Role.ADMIN})
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
