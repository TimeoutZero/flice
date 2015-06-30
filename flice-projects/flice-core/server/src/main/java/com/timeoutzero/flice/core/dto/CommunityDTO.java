package com.timeoutzero.flice.core.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Tag;
import com.timeoutzero.flice.core.util.LocalDateTimeDeserializer;
import com.timeoutzero.flice.core.util.LocalDateTimeJsonSerializer;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class CommunityDTO {

	private Long id;

	private String name;
	
	private String description;
	
	private String owner;
	
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeJsonSerializer.class)
	private LocalDateTime created;

	private String image;
	
	private List<Tag> tags;
	
	public CommunityDTO(Community community){
		super();
		
		this.id	 		 = community.getId();
		this.name 		 = community.getName();
		this.description = community.getDescription();
//		this.owner  	 = community.getOwner().getName();
		this.created 	 = community.getCreated();
		this.image 		 = community.getImage();
		this.tags 		 = community.getTags();
	}
	
}
