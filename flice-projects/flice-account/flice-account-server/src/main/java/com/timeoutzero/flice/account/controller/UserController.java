package com.timeoutzero.flice.account.controller;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.account.dto.UserDTO;
import com.timeoutzero.flice.account.entity.User;
import com.timeoutzero.flice.account.enums.SocialMedia;
import com.timeoutzero.flice.account.exception.AccountException;
import com.timeoutzero.flice.account.form.UserForm;
import com.timeoutzero.flice.account.repository.AccountRepository;
import com.timeoutzero.flice.account.service.UserSocialMediaService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final String EXCEPTION_USERNAME_ALREADY_EXIST = "username.already.exist";
	private static final String EXCEPTION_EMAIL_ALREADY_EXIST = "email.already.exist";

	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(5);

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private UserSocialMediaService userSocialMediaService;

	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO create(@Valid UserForm form) {
		
		isValidForm(form);

		User user = new User();
		user.setEmail(form.getEmail());
		user.setUsername(form.getUsername());
		user.setPassword(bcrypt.encode(form.getPassword()));
		
		user = repository.getUserRepository().save(user);
		
		return new UserDTO(user);
	}

	private void isValidForm(UserForm form) {

		if (isBlank(form.getEmail()) && isBlank(form.getUsername())) {
			throw new AccountException(HttpStatus.PRECONDITION_FAILED, UserForm.USER_AND_EMAIL_BLANK);
		}

		if (isNotBlank(form.getUsername()) && existUsername(form)) {
			throw new AccountException(HttpStatus.PRECONDITION_FAILED, EXCEPTION_USERNAME_ALREADY_EXIST);
		}

		if (isNotBlank(form.getEmail()) && existEmail(form)) {
			throw new AccountException(HttpStatus.PRECONDITION_FAILED, EXCEPTION_EMAIL_ALREADY_EXIST);
		}
	}

	private boolean existEmail(UserForm form) {
		return repository.getUserRepository().findByEmail(form.getEmail()) != null;
	}

	private boolean existUsername(UserForm form) {
		return repository.getUserRepository().findByUsername(form.getUsername()) != null;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/token", method = POST)
	public UserDTO create(@RequestParam String accessToken, @RequestParam SocialMedia media) {

		User user = userSocialMediaService.create(accessToken, media);
		user = repository.getUserRepository().save(user);

		return new UserDTO(user);
	}
}
