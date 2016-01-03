package com.timeoutzero.flice.core.dto;

import com.timeoutzero.flice.core.domain.Tag;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TagDTO {

	private Long id;
	private String name;
	
	public TagDTO(Tag tag) {
		this.id   = tag.getId();
		this.name = tag.getName();
	}
}
