package com.timeoutzero.flice.core.dto;

import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

import com.timeoutzero.flice.core.domain.Community;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class CommunityDTO {

	private Long id;
	private String name;
	private String image;
	private String cover;
	private int members;
	private String description;
	private String owner;
	private DateTime created;
	private boolean privacity;
	private Set<TagDTO> tags;
	
	public CommunityDTO(Community community) {
		this.id	 		 = community.getId();
		this.name 		 = community.getName();
		this.image 		 = community.getImage();
		this.cover 		 = community.getCover();
		this.members     = community.getMembers().size();
		this.description = community.getDescription();
		//this.owner  	 = community.getOwner().getProfile().getName();
		this.created 	 = community.getCreated();
		this.privacity	 = community.getPrivacity();
		this.tags 		 = community.getTags().stream()
				.map(TagDTO::new).collect(Collectors.toSet());
	}
	
}
