package com.timeoutzero.flice.core.dto;

import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.rest.dto.ProfileDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class UserDTO {

	private Long id;
	private Long accountId;
	private ProfileDTO profile;
	
	public UserDTO(User user) {
		this.id 	  	= user.getId();
		this.accountId	= user.getAccountId();
		this.profile  	= user.getProfile();
	}
}
