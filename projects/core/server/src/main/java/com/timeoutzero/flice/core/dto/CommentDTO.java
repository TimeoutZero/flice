package com.timeoutzero.flice.core.dto;


import org.joda.time.DateTime;

import com.timeoutzero.flice.core.domain.Comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class CommentDTO {

	private Long topicId;
	private String content;
	private String owner;
	private DateTime created;

	public CommentDTO(Comment comment){
		super();
		this.content = comment.getContent();
		this.topicId = comment.getTopic().getId();
//		this.owner = comment.getOwner().getName();
		this.created = comment.getCreated();
	}

}
