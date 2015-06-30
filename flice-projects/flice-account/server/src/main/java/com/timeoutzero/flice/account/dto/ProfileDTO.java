package com.timeoutzero.flice.account.dto;

import lombok.Getter;

import com.timeoutzero.flice.account.entity.Profile;

@Getter
public class ProfileDTO {

	private String name;
	private String photo;

	public ProfileDTO(Profile profile) {
		this.name = profile.getName();
		this.photo = profile.getPhoto();
	}
}
