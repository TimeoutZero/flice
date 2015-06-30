package com.timeoutzero.flice.core.form;

import lombok.Setter;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.timeoutzero.flice.core.domain.User;

@Setter
public class UserForm {

	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;

	public User toEntity(){
		
//		String gensalt  = BCrypt.gensalt(5);
//		String password = BCrypt.hashpw(this.password, gensalt);
		
		User user = new User();
		user.setEmail(this.email);
		
		return user;
	}
	
}
