package com.timeoutzero.flice.account.service;

import com.timeoutzero.flice.account.entity.User;
import com.timeoutzero.flice.account.enums.SocialMedia;

public interface UserSocialMediaService {

	User create(String accessToken, SocialMedia media);
}
