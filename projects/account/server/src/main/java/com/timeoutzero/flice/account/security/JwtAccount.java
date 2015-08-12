package com.timeoutzero.flice.account.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.timeoutzero.flice.account.entity.Product;
import com.timeoutzero.flice.account.entity.User;
import com.timeoutzero.flice.account.exception.AccountException;
import com.timeoutzero.flice.account.repository.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtAccount {
	
	@Autowired
	private String clientKey;
	
	@Autowired
	private UserRepository userRepository;

	public String createToken(Product issuer, User user) throws NoSuchAlgorithmException, InvalidKeyException {
		
		String token = 
				 Jwts.builder()
				.setIssuer(issuer.getName())
				.setSubject(user.getEmail())
				.setExpiration(getConvertedExpirationDate())
				.signWith(SignatureAlgorithm.HS512, getSecretKey())
				.compact();
		
		return token;
	}

	private Key getSecretKey() throws NoSuchAlgorithmException, InvalidKeyException {
		return new SecretKeySpec(clientKey.getBytes(), SignatureAlgorithm.HS512.getJcaName());
	}

	private Date getConvertedExpirationDate() {
		
		LocalDateTime expiration = LocalDateTime.now().plusDays(1);
		return Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public void validateToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, InvalidKeyException, IllegalArgumentException, NoSuchAlgorithmException {
		 
		String subject = getSubject(token);
	
		if(!userRepository.existByEmail(subject)) {
			throw new AccountException(HttpStatus.UNAUTHORIZED, "unautorized");
		}
	}

	private String getSubject(String token) throws NoSuchAlgorithmException, InvalidKeyException {
		String subject = null;

		try {
			
			subject = Jwts.parser()
					.setSigningKey(getSecretKey())
					.parseClaimsJws(token)
					.getBody().getSubject();
			
		} catch (Exception e) {
			throw new AccountException(HttpStatus.UNAUTHORIZED, "invalid.token");
		}
		
		return subject;
	}
}
