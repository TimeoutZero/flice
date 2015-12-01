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
		return Community.builder().name(name).description(name).visibility(true);
	}
	
	public static TopicBuilder topic(String name, Community community, User user) {
		return Topic.builder().name(name).owner(user).community(community);
	}
	
	public static CommentBuilder comment(String content, Topic topic, User user) {
		return Comment.builder().content(content).created(DateTime.now()).topic(topic).owner(user);
	}
}
