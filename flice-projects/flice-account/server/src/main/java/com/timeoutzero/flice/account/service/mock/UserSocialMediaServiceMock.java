package com.timeoutzero.flice.account.service.mock;

import org.springframework.stereotype.Component;

import com.timeoutzero.flice.account.entity.Profile;
import com.timeoutzero.flice.account.entity.User;
import com.timeoutzero.flice.account.enums.SocialMedia;
import com.timeoutzero.flice.account.service.UserSocialMediaService;

@Component
@org.springframework.context.annotation.Profile("TEST")
public class UserSocialMediaServiceMock implements UserSocialMediaService {

	@Override
	public User create(String accessToken, SocialMedia media) {

		Profile profile = new Profile();
		profile.setName("user");
		profile.setPhoto("http://imgur./");

		User user = new User();
		user.setEmail("user@gmail.com");
		user.setProfile(profile);

		return user;
	}
}
