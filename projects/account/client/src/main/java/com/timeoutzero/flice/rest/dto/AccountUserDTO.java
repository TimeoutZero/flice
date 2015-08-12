package com.timeoutzero.flice.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountUserDTO {
	
	private Long id;
	private String email;
	private String username;
	private ProfileDTO profile;
	
}
