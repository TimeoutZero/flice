package com.timeoutzero.flice.account.form;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserForm {

	public static final String USER_AND_EMAIL_BLANK = "user.and.email.blank";

	@NotBlank
	private String email;

	@NotBlank
	private String password;
}
