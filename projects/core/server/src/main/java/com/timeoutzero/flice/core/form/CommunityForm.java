package com.timeoutzero.flice.core.form;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.timeoutzero.flice.core.domain.Community;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class CommunityForm {

	@NotBlank(message = "invalid.name.blank")
	private String name;
	
	@NotBlank(message = "invalid.description.blank")
	private String description;

	@NotNull(message = "invalid.visibility.null")
	private Boolean privacity;

	private String image;
	private String cover;
	
	private Set<TagForm> tags = new HashSet<>();
	
	public Community toEntity(){
		
		Community community = new Community();
		community.setName(this.name);
		community.setDescription(this.description);
		community.setPrivacity(this.privacity);
		
		return community;
	}
	
}
