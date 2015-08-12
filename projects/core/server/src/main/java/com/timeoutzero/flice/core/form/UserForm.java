package com.timeoutzero.flice.core.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class UserForm {

	@Email
	@NotBlank(message = "email.blank.error")
	private String email;
	
	@NotBlank(message = "password.blank.error")
	private String password;
}
