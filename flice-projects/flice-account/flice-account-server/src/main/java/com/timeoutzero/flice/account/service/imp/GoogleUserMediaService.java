package com.timeoutzero.flice.account.service.imp;

import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Component;

import com.timeoutzero.flice.account.entity.Profile;
import com.timeoutzero.flice.account.entity.User;

@Component
public class GoogleUserMediaService {

	public User create(String accessToken) {

		GoogleTemplate template = new GoogleTemplate(accessToken);
		Person googleProfile = template.plusOperations().getGoogleProfile();

		Profile profile = new Profile();
		profile.setName(googleProfile.getDisplayName());
		profile.setPhoto(googleProfile.getImageUrl());

		User user = new User();
		user.setProfile(profile);
		user.setEmail(googleProfile.getAccountEmail());

		return user;
	}

}
