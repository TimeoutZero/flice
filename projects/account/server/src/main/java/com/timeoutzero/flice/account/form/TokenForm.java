package com.timeoutzero.flice.account.form;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenForm {

	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
