package com.timeoutzero.flice.account.form;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

@Getter @Setter
public class UserForm {

	public static final String USER_AND_EMAIL_BLANK = "user.and.email.blank";

	private String email;
	private String username;

	@NotBlank
	private String password;
}
