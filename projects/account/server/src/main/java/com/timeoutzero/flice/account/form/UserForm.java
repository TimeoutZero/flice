package com.timeoutzero.flice.account.form;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

	public static final String USER_AND_EMAIL_BLANK = "user.and.email.blank";

	@NotBlank
	private String email;

	@NotBlank
	private String password;
}
