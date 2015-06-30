package com.timeoutzero.flice.account.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.timeoutzero.flice.account.entity.User;
import com.timeoutzero.flice.account.enums.SocialMedia;
import com.timeoutzero.flice.account.service.UserSocialMediaService;

@Component
@Profile({ "DEV", "PROD" })
public class UserSocialMediaServiceImp implements UserSocialMediaService {

	@Autowired
	private FacebookUserMediaService facebookUserMediaService;

	@Autowired
	private TwitterUserMediaService twitterUserMediaService;

	@Autowired
	private GoogleUserMediaService googleUserMediaService;

	@Override
	public User create(String accessToken, SocialMedia media) {

		User user = null;

		switch (media) {
		case FACEBOOK:
			user = facebookUserMediaService.create(accessToken);
			break;
		case TWITTER:
			user = twitterUserMediaService.create(accessToken);
			break;
		case GOOGLE:
			user = googleUserMediaService.create(accessToken);
			break;
		}

		return user;
	}

}
