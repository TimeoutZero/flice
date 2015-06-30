package com.timeoutzero.flice.account.dto;

import lombok.Getter;

import com.timeoutzero.flice.account.entity.Profile;
import com.timeoutzero.flice.account.entity.User;

@Getter
public class UserDTO {
	
	private Long id;
	private String email;
	private String username;
	private ProfileDTO profile;
	
	public UserDTO(User user) {
		this.id 	  = user.getId();
		this.email 	  = user.getEmail();
		this.username = user.getUsername();

		Profile profile = user.getProfile();

		if (profile != null) {
			this.profile = new ProfileDTO(profile);
		}
	}
}
