package com.timeoutzero.flice.core.compose;


import java.util.Arrays;

import org.joda.time.DateTime;

import com.timeoutzero.flice.core.domain.Comment;
import com.timeoutzero.flice.core.domain.Comment.CommentBuilder;
import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Community.CommunityBuilder;
import com.timeoutzero.flice.core.domain.Topic;
import com.timeoutzero.flice.core.domain.Topic.TopicBuilder;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.domain.User.UserBuilder;
import com.timeoutzero.flice.core.enums.Role;

public class Compose {
	
	public static UserBuilder user(String username) {
		return User.builder().accountId(1l).email(username).roles(Arrays.asList(Role.USER));
	}

	public static CommunityBuilder community(String name) {
		return Community.builder().name(name).description(name).active(true).created(DateTime.now());
	}
	
	public static TopicBuilder topic(Community community, String name) {
		return Topic.builder().name(name).active(true).community(community).created(DateTime.now());
	}
	
	public static CommentBuilder comment(Topic topic, String content) {
		return Comment.builder().content(content).active(true).created(DateTime.now()).topic(topic);
	}


}
