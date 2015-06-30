package com.timeoutzero.flice.account.service.imp;

import static org.springframework.social.facebook.api.GraphApi.GRAPH_API_URL;

import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import com.timeoutzero.flice.account.entity.Profile;
import com.timeoutzero.flice.account.entity.User;

@Component
@org.springframework.context.annotation.Profile({ "DEV", "QA", "PROD" })
public class FacebookUserMediaService {

	public User create(String accessToken) {

		FacebookTemplate template = new FacebookTemplate(accessToken);
		FacebookProfile facebookProfile = template.userOperations().getUserProfile();

		Profile profile = getProfileFromFacebook(facebookProfile);

		User user = new User();
		user.setProfile(profile);
		user.setEmail(facebookProfile.getEmail());

		return user;
	}

	private Profile getProfileFromFacebook(FacebookProfile facebookProfile) {

		String name = facebookProfile.getName();
		String photo = getPhotoFromFacebook(facebookProfile);

		return createProfile(name, photo);
	}

	private String getPhotoFromFacebook(FacebookProfile profile) {

		StringBuilder photoBuilder = new StringBuilder(GRAPH_API_URL);
		photoBuilder.append(String.format("%s/picture", profile.getId()));
		photoBuilder.append(String.format("?type=%s", "square"));
		return photoBuilder.toString();
	}

	private Profile createProfile(String name, String photo) {

		Profile profile = new Profile();
		profile.setName(name);
		profile.setPhoto(photo);

		return profile;
	}

}
