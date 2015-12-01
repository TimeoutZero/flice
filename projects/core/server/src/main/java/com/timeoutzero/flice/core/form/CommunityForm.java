package com.timeoutzero.flice.core.form;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Tag;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class CommunityForm {

	@NotBlank(message = "name.blank")
	private String name;
	
	@NotBlank(message = "description.blank")
	private String description;

	@NotNull(message = "visibility.null")
	private Boolean visibility;

	private String image;
	private List<Tag> tags;
	
	public Community toEntity(){
		
		Community community = new Community();
		community.setName(this.name);
		community.setDescription(this.description);
		community.setImage(this.image);
		community.setTags(this.tags);
		community.setVisibility(this.visibility);
		
		return community;
	}
	
}
