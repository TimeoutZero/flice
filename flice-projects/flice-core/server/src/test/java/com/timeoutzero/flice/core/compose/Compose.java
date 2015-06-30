package com.timeoutzero.flice.core.compose;


import org.joda.time.LocalDateTime;

import com.timeoutzero.flice.core.domain.Comment;
import com.timeoutzero.flice.core.domain.Comment.CommentBuilder;
import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Community.CommunityBuilder;
import com.timeoutzero.flice.core.domain.Topic;
import com.timeoutzero.flice.core.domain.Topic.TopicBuilder;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.domain.User.UserBuilder;
import com.timeoutzero.flice.rest.dto.UserDTO;
import com.timeoutzero.flice.rest.operations.mock.AccountMockBuilders;

public class Compose {
	
	
	public static UserBuilder user(String username) {
	
		UserDTO user = AccountMockBuilders.createMockUser(username);
		return User.builder().accountId(user.getId()).email(username);
	}

	public static CommunityBuilder community(String name) {
		return Community.builder().name(name).description(name).active(true).created(LocalDateTime.now());
	}
	
	public static TopicBuilder topic(Community community, String name) {
		return Topic.builder().name(name).active(true).community(community).created(LocalDateTime.now());
	}
	
	public static CommentBuilder comment(Topic topic, String content) {
		return Comment.builder().content(content).active(true).created(LocalDateTime.now()).topic(topic);
	}


}
