package com.timeoutzero.flice.core.dto;


import org.joda.time.DateTime;

import com.timeoutzero.flice.core.domain.Topic;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class TopicDTO {

	private Long id;
	private Long communityId;
	private String name;
	private UserDTO author;
	private DateTime created;
	private DateTime lastUpdated;
	private int answers;
	
	private boolean editable;
	private boolean deletable;
	
	public TopicDTO(Topic topic){
		super();
		this.id		 	 = topic.getId();
		this.communityId = topic.getCommunity().getId();
		this.name 	 	 = topic.getName();
		this.created 	 = topic.getCreated();
		this.answers 	 = topic.getComments().size();
		this.author	 	 = new UserDTO(topic.getOwner());
	}

}
