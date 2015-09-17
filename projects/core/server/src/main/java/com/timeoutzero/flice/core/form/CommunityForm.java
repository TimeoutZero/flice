package com.timeoutzero.flice.core.form;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Tag;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
		community.setCreated(DateTime.now());
		community.setActive(true);
		community.setTags(this.tags);
		
		return community;
	}
	
}
