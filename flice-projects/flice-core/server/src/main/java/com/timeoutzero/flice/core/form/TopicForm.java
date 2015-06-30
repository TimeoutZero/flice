package com.timeoutzero.flice.core.form;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotBlank;

import com.timeoutzero.flice.core.domain.Topic;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class TopicForm {

	@NotBlank
	private String name;
	
	@NotNull
	private Long communityId;
	
	public Topic toEntity(){
		Topic topic = new Topic();
		topic.setName(this.name);
		
		return topic;
	}
	
}
