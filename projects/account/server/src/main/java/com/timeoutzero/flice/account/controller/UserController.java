package com.timeoutzero.flice.account.controller;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.account.dto.UserDTO;
import com.timeoutzero.flice.account.entity.Profile;
import com.timeoutzero.flice.account.entity.User;
import com.timeoutzero.flice.account.enums.SocialMedia;
import com.timeoutzero.flice.account.exception.AccountException;
import com.timeoutzero.flice.account.form.UserForm;
import com.timeoutzero.flice.account.repository.AccountRepository;
import com.timeoutzero.flice.account.security.JwtAccount;
import com.timeoutzero.flice.account.service.UserSocialMediaService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final String EXCEPTION_USER_NOT_FOUND	  = "user.not.found";
	private static final String EXCEPTION_EMAIL_ALREADY_EXIST = "email.already.exist";

	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(5);

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private JwtAccount jwtAccount;
	
	@Autowired
	private UserSocialMediaService userSocialMediaService;
	
	@RequestMapping(value = "/token", method = GET)
	public UserDTO get(@RequestParam String token){
		
		String email = jwtAccount.getSubject(token);

		User user = repository.getUserRepository().findByEmail(email); 
		
		if (user == null) {
			throw new AccountException(HttpStatus.NOT_FOUND, EXCEPTION_USER_NOT_FOUND);
		}
		
		return new UserDTO(user);
	}
	
	@RequestMapping(value = "/{id}", method = GET)
	public UserDTO get(@PathVariable("id") Long id){
		
		User user = repository.getUserRepository().findOne(id);
		
		return new UserDTO(user);
	}
	
	@RequestMapping(method = GET)
	public List<UserDTO> list(@RequestParam List<Long> ids){
		
		List<User> users = (List<User>) repository.getUserRepository().findAll(ids);		
		
		return users.stream()
				.map(UserDTO::new)
				.collect(Collectors.toList());
	}

	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO create(@RequestBody @Valid UserForm form) {
		
		isValidForm(form);

		User user = new User();
		user.setEmail(form.getEmail());
		user.setPassword(bcrypt.encode(form.getPassword()));
		
		user = repository.getUserRepository().save(user);
		
		return new UserDTO(user);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/token", method = POST)
	public UserDTO create(@RequestParam String accessToken, @RequestParam SocialMedia media) {
		
		User user = userSocialMediaService.create(accessToken, media);
		user = repository.getUserRepository().save(user);
		
		return new UserDTO(user);
	}
	
	@Transactional
	@RequestMapping(value = "/{id}", method = PUT)
	public UserDTO update(
			@PathVariable("id") Long id,
			@RequestParam("name") String name,
			@RequestParam("username") String username,
			@RequestParam("photo") String photo
			) {
		
		User user = repository.getUserRepository().findOne(id);
		
		Profile profile = user.getProfile();
		profile.setName(name);
		profile.setUsername(username);
		profile.setPhoto(photo);
		
		user.setProfile(profile);
		repository.getUserRepository().save(user);
		
		return new UserDTO(user);
	}

	private void isValidForm(UserForm form) {

		if (isBlank(form.getEmail())) {
			throw new AccountException(HttpStatus.PRECONDITION_FAILED, UserForm.USER_AND_EMAIL_BLANK);
		}

		if (isNotBlank(form.getEmail()) && repository.getUserRepository().existByEmail(form.getEmail())) {
			throw new AccountException(HttpStatus.PRECONDITION_FAILED, EXCEPTION_EMAIL_ALREADY_EXIST);
		}
	}
}
