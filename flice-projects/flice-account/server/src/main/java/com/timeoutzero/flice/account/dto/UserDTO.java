package com.timeoutzero.flice.account.dto;

import com.timeoutzero.flice.account.entity.Profile;
import com.timeoutzero.flice.account.entity.User;

import lombok.Getter;

@Getter
public class UserDTO {
	
	private Long id;
	private String email;
	private ProfileDTO profile;
	
	public UserDTO(User user) {
		this.id 	  = user.getId();
		this.email 	  = user.getEmail();

		Profile profile = user.getProfile();

		if (profile != null) {
			this.profile = new ProfileDTO(profile);
		}
	}
}
