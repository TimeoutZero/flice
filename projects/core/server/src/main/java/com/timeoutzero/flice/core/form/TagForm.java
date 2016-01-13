package com.timeoutzero.flice.core.form;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TagForm {

	private Long id;
	
	@NotBlank(message = "invalid.tag.blank")
	private String name;

	public TagForm(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}
