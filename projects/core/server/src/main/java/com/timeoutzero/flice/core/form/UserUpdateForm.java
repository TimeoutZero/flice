package com.timeoutzero.flice.core.form;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdateForm {

	@NotBlank
	private String name;

	@NotBlank
	private String username;
	
	@NotBlank
	private String description;
	
	private String photo;
	
}
