package com.timeoutzero.flice.core.form;

import org.hibernate.validator.constraints.NotBlank;

import com.timeoutzero.flice.core.domain.Topic;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class TopicForm {

	@NotBlank
	private String name;

	@NotBlank
	private String content;
	
	public Topic toEntity(){
	
		Topic topic = new Topic();
		topic.setName(this.name);
		
		return topic;
	}
	
}
