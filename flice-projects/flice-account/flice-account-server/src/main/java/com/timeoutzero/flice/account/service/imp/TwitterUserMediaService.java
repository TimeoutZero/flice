package com.timeoutzero.flice.account.service.imp;

import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import com.timeoutzero.flice.account.entity.Profile;
import com.timeoutzero.flice.account.entity.User;

@Component
public class TwitterUserMediaService {

	public User create(String accessToken) {

		TwitterTemplate template = new TwitterTemplate(accessToken);
		TwitterProfile twitterProfile = template.userOperations().getUserProfile();

		String name = twitterProfile.getName();
		String photo = twitterProfile.getProfileImageUrl();

		Profile profile = new Profile();
		profile.setName(name);
		profile.setPhoto(photo);

		User user = new User();

		return user;
	}

}
