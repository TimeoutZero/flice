package com.timeoutzero.flice.account.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.account.entity.User;
import com.timeoutzero.flice.account.exception.AccountException;
import com.timeoutzero.flice.account.form.TokenForm;
import com.timeoutzero.flice.account.repository.UserRepository;
import com.timeoutzero.flice.account.security.JwtAccount;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@RestController
@Controller("/auth/token")
public class TokenController {
 
	private static final String EXCEPTION_BAD_CREDENTIALS   = "bad.credentials"; 
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private JwtAccount jwtAccount;

	@RequestMapping(method = GET)
	public void checkToken(@RequestParam String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, InvalidKeyException, IllegalArgumentException, NoSuchAlgorithmException {
		jwtAccount.validateToken(token);
	}

	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String postForToken(@Valid @RequestBody TokenForm form) throws AccessException, NoSuchAlgorithmException, InvalidKeyException {
		
		User user = findBy(form.getUsername(), form.getPassword());
		return jwtAccount.createToken(user);
	}

	private User findBy(String username, String password) {

		User user = userRepository.findByEmail(username);

		if(user == null) {
			throw new AccountException(HttpStatus.FORBIDDEN, EXCEPTION_BAD_CREDENTIALS);
		}
		
		if(!BCrypt.checkpw(password, user.getPassword())){
			throw new AccountException(HttpStatus.FORBIDDEN, EXCEPTION_BAD_CREDENTIALS);
		}
		
		return user;
	}
}
