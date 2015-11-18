package com.timeoutzero.flice.core.form;

import org.hibernate.validator.constraints.NotBlank;

import com.timeoutzero.flice.core.domain.Comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class CommentForm {

	@NotBlank
	private String content;
	
	public Comment toEntity(){
		Comment comment = new Comment();
		comment.setContent(this.content);
		
		return comment;
	}
	
}
