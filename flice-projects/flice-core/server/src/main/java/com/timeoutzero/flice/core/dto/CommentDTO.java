package com.timeoutzero.flice.core.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.joda.time.LocalDateTime;

import com.timeoutzero.flice.core.domain.Comment;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class CommentDTO {

	private String content;

	private Long topicId;

	private String owner;

	private LocalDateTime created;

	public CommentDTO(Comment comment){
		super();
		this.content = comment.getContent();
		this.topicId = comment.getTopic().getId();
//		this.owner = comment.getOwner().getName();
		this.created = comment.getCreated();
	}

}
