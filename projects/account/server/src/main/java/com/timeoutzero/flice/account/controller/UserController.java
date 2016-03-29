package com.timeoutzero.flice.account.controller;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.joda.time.DateTime;
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
import com.timeoutzero.flice.account.form.UserPasswordForm;
import com.timeoutzero.flice.account.form.UserUpdateForm;
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
	
	@RequestMapping(value = "/check/username", method = GET)
	public Map<String, String> checkUsername (@RequestParam("username") String username) {
		
		Boolean existByUsername = repository.getUserRepository().existByUsername(username);

		Map<String, String> map = new HashMap<>();
		map.put("exist", existByUsername.toString());
		
		return map;
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
	public UserDTO create(@Valid @RequestBody UserForm form) {
		
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
	public UserDTO update(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateForm form) {
		
		User user = repository.getUserRepository().findOne(id);
		
		Profile profile = user.getProfile();
		profile.setName(form.getName());
		profile.setUsername(form.getUsername());
		profile.setDescription(form.getDescription());
		profile.setPhoto(form.getPhoto());
		profile.setLastUpdate(DateTime.now());
		
		user.setProfile(profile);
		repository.getUserRepository().save(user);
		
		return new UserDTO(user);
	}
	
	@Transactional
	@RequestMapping(value = "/{id}/password", method = PUT)
	public void updatePassword(
			@PathVariable("id") Long id, @Valid @RequestBody UserPasswordForm form ) {
		
		User user = repository.getUserRepository().findOne(id);
		
		if (!bcrypt.matches(form.getActualPassword(), user.getPassword())) {
			throw new AccountException(HttpStatus.FORBIDDEN, "invalid.actual.password");
		}
		
		if (!form.getNewPassword().equals(form.getNewPasswordConfirmation())) {
			throw new AccountException(HttpStatus.PRECONDITION_REQUIRED, "password.not.match");
		}
		
		user.setPassword(bcrypt.encode(form.getNewPassword()));
		user.setLastUpdate(DateTime.now());
		
		repository.getUserRepository().save(user);
		
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
