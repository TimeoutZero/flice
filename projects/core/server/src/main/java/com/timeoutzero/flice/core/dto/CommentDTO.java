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

	private Long id;
	private Long topicId;
	private String content;
	private UserDTO author;
	private DateTime created;
	
	private boolean editable;
	
	public CommentDTO(final Comment comment){
		super();
		this.id 	 = comment.getId();
		this.content = comment.getContent();
		this.topicId = comment.getTopic().getId();
		this.created = comment.getCreated();
		this.author	 = new UserDTO(comment.getOwner());
	}

}
