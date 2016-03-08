package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.security.CoreSecurityContext.getLoggedUser;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.dto.UserDTO;
import com.timeoutzero.flice.core.enums.Role;
import com.timeoutzero.flice.core.exception.WebException;
import com.timeoutzero.flice.core.form.UserForm;
import com.timeoutzero.flice.core.repository.UserRepository;
import com.timeoutzero.flice.core.service.ImageService;
import com.timeoutzero.flice.core.service.SmtpMailSender;
import com.timeoutzero.flice.core.util.AuthenticatorCookieHandler;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;
import com.timeoutzero.flice.rest.operations.AccountOperations;

@RestController
@RequestMapping("/user")
public class UserController {
	
//	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	private static final int INVITES_LIMIT = 5;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountOperations accountOperations;
	
	@Autowired
	private AuthenticatorCookieHandler authenticatorCookieHandler;
	
	@Autowired
	private SmtpMailSender mailSender;
	
	@Autowired
	private ImageService imageService;
	
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
		
		User user = createUser(form.getEmail(), form.getPassword());
		 
		return new UserDTO(user);
	}
	
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/email" , method = POST)
	public UserDTO create(@RequestParam("email") String email) {
		
		User user = createUser(email,  "password");
		
		Map<String, String> params = new HashMap<>();
		params.put("${password}", RandomStringUtils.randomAlphanumeric(6));
		
		mailSender.send(email, "Welcome to exclusive club", "/email/welcome.html", params);

		return new UserDTO(user);
	}
	
	@Transactional
	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(value = "/{id}", method = PUT)
	public UserDTO update(
			@PathVariable("id") Long id,
			@RequestParam("name") String name,
			@RequestParam("username") String username,
			@RequestParam("photo") String photo) {
		
		User user = userRepository.findOne(id);
		
		AccountUserDTO accountUpdated = accountOperations
				.getUserOperations()
				.update(user.getAccountId(), name, username, imageService.write(user, photo));
		user.setProfile(accountUpdated.getProfile());
		
		return new UserDTO(user);
	}

	private User createUser(String email, String password) {
		
		AccountUserDTO userAccountDTO = accountOperations.getUserOperations().create(email, password);
		
		User user = new User(); 
		user.setAccountId(userAccountDTO.getId());
		user.setEmail(userAccountDTO.getEmail());
		user.getRoles().add(Role.USER);
		
		userRepository.save(user);
		
		return user;
	}
	
	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(value = "/invite", method = POST)
	public Map<String, Integer> invite(@RequestParam("email") String email, HttpServletRequest req) {
		
		User user = getLoggedUser();
		
		if (user.getInvites() >= INVITES_LIMIT) {
			throw new WebException(HttpStatus.PRECONDITION_FAILED, "The invites exceed limit!");
		}
		
		Map<String, String> params = new HashMap<>();
		params.put("${url}", req.getRequestURL() + "/#/community/user?email=" + Base64.encodeBase64String(email.getBytes()));
		
		mailSender.send(email, "Did you hear about Flice?", "/email/invites.html", params);
		
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

}
