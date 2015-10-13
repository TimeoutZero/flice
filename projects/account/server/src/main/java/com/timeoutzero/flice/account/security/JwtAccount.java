package com.timeoutzero.flice.account.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.timeoutzero.flice.account.entity.User;
import com.timeoutzero.flice.account.repository.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtAccount {

	private static final String EXCEPTION_EXPIRED_TOKEN 	= "expired.token";
	private static final String EXCEPTION_SUBJECT_NOT_EXIST = "subject.not.exist";
	private static final String EXCEPTION_INVALID_TOKEN 	= "invalid.token"; 

	@Value("${flice.secret.key}")
	private String secretKey; 
	
	@Autowired
	private UserRepository userRepository;

	public String createToken(User user) throws NoSuchAlgorithmException, InvalidKeyException {
		
		String token = 
				 Jwts.builder()
				.setSubject(user.getEmail())
				.setExpiration(getConvertedExpirationDate())
				.signWith(SignatureAlgorithm.HS512, getSecretKey())
				.compact();
		
		return token;
	}

	private Key getSecretKey() throws NoSuchAlgorithmException, InvalidKeyException {
		return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName());
	}

	private Date getConvertedExpirationDate() {
		
		LocalDateTime expiration = LocalDateTime.now().plusDays(1);
		return Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public void validateToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, InvalidKeyException, IllegalArgumentException, NoSuchAlgorithmException {
		 
		String subject = getSubject(token);
	
		if(!userRepository.existByEmail(subject)) {
			throw new BadCredentialsException(EXCEPTION_SUBJECT_NOT_EXIST);
		}
	}

	public String getSubject(String token) {
		String subject = null;

		try {
			
			subject = Jwts.parser()
					.setSigningKey(getSecretKey())
					.parseClaimsJws(token)
					.getBody().getSubject();
			
		} catch (ExpiredJwtException e) {
			throw new BadCredentialsException(EXCEPTION_EXPIRED_TOKEN);
		} catch (Exception e) {
			throw new BadCredentialsException(EXCEPTION_INVALID_TOKEN);
		}
		
		return subject;
	}
}
