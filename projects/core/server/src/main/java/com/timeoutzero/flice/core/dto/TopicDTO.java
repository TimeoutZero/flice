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

	private String name;
	private Long communityId;
	private String owner;
	private DateTime created;

	public TopicDTO(Topic topic){
		super();
		this.name = topic.getName();
		this.communityId = topic.getCommunity().getId();
//		this.owner = topic.getOwner().getName();
		this.created = topic.getCreated();
	}

}
