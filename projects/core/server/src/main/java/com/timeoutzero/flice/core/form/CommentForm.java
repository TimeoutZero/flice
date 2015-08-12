package com.timeoutzero.flice.core.form;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotBlank;

import com.timeoutzero.flice.core.domain.Comment;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class CommentForm {

	@NotBlank
	private String content;
	
	@NotNull
	private Long topicId;
	
	public Comment toEntity(){
		Comment comment = new Comment();
		comment.setContent(this.content);
		
		return comment;
	}
	
}
