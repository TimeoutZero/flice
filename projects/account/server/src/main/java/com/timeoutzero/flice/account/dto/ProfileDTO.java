package com.timeoutzero.flice.account.dto;

import com.timeoutzero.flice.account.entity.Profile;

import lombok.Getter;

@Getter
public class ProfileDTO {

	private String name;
	private String username;
	private String description;
	private String photo;

	public ProfileDTO(Profile profile) {
		this.name 		 = profile.getName();
		this.username	 = profile.getUsername();
		this.description = profile.getDescription();
		this.photo 		 = profile.getPhoto();
	}
}
