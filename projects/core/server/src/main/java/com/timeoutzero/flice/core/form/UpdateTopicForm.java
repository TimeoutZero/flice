package com.timeoutzero.flice.core.form;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class UpdateTopicForm {

	@NotBlank
	private String name;

}
