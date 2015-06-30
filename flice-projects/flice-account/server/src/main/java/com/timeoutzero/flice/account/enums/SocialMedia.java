package com.timeoutzero.flice.account.enums;

import lombok.Getter;

public enum SocialMedia {

	FACEBOOK("facebook"), TWITTER("twitter"), GOOGLE("google");

	@Getter
	private String name;

	SocialMedia(String name) {
		this.name = name;
	}
}
