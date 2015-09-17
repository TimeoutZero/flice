package com.timeoutzero.flice.core.dto;

import java.util.List;

import org.joda.time.DateTime;

import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Tag;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class CommunityDTO {

	private Long id;
	private String name;
	private String description;
	private String owner;
	private String image;
	private DateTime created;
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
