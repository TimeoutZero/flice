package com.timeoutzero.flice.core.form;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;

import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Tag;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class CommunityForm {

	@NotBlank
	private String name;
	
	@NotBlank
	private String description;

	private String image;
	
	private List<Tag> tags;
	
	public Community toEntity(){
		
		Community community = new Community();
		community.setName(this.name);
		community.setDescription(this.description);
		community.setImage(this.image);
		community.setCreated(LocalDateTime.now());
		community.setActive(true);
		community.setTags(this.tags);
		
		return community;
	}
	
}
