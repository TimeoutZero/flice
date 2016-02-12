package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.security.CoreSecurityContext.getLoggedUser;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.timeoutzero.flice.core.exception.WebException;
import com.timeoutzero.flice.core.form.UserForm;
import com.timeoutzero.flice.core.repository.CommunityRepository;
import com.timeoutzero.flice.core.repository.TopicRepository;
import com.timeoutzero.flice.core.repository.UserCommunityRepository;
import com.timeoutzero.flice.core.repository.UserRepository;
import com.timeoutzero.flice.core.repository.UserTopicRepository;
import com.timeoutzero.flice.core.service.SmtpMailSender;
import com.timeoutzero.flice.core.util.AuthenticatorCookieHandler;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.operations.AccountOperations;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	private static final int INVITES_LIMIT = 5;

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
	
	@Autowired
	private SmtpMailSender mailSender;
	
	@Transactional(readOnly = true)
	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(value = "/me", method = GET)
	public UserDTO me() {
		return new UserDTO(getLoggedUser());
	}
	
	@Transactional
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
	
	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(value = "/invite", method = POST)
	public Map<String, Integer> invite(@RequestParam("email") String email) {
		
		String subject = "Welcome to exclusive club";
		
		User user = getLoggedUser();
		
		if (user.getInvites() >= INVITES_LIMIT) {
			throw new WebException(HttpStatus.PRECONDITION_FAILED, "The invites exceed limit!");
		}

		try {
			
			byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream("/email/invites.html"));
			String body = new String(bytes);
			mailSender.send(email, subject, body);
		
		} catch (MessagingException e) {
			LOG.error("Problem to invite e-mail: [ {} ] , details : {}", email,  e.getLocalizedMessage());
		} catch (IOException e) {
			LOG.error("Problem to find e-mail template details : {}", e.getLocalizedMessage());
		}
		
		user.setInvites(user.getInvites() + 1);
		userRepository.save(user);
		
		Map<String, Integer> map = new HashMap<>();
		map.put("invites", getLoggedUser().getInvites());
		
		return map;
	}

	@RequestMapping(value = "/token", method = POST)
	public void createToken(@RequestBody @Valid UserForm form, HttpServletRequest request, HttpServletResponse response) {
		
		String token = accountOperations.getTokenOperations().create(form.getEmail(), form.getPassword());
		response.addCookie(authenticatorCookieHandler.retrieveCookie(request, token));
	}
	
	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(value="/topic", method=PUT)
	public void followTopic(@RequestParam("topicId") Long topicId, @RequestParam("favorite") Boolean favorite){
		
		Topic topic = topicRepository.findById(topicId);
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
		
		Community community = communityRepository.findById(communityId);
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
