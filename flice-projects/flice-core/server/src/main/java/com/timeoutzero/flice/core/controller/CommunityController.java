package com.timeoutzero.flice.core.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.dto.CommunityDTO;
import com.timeoutzero.flice.core.form.CommunityForm;
import com.timeoutzero.flice.core.service.CoreService;

@RestController
@RequestMapping("/community")
public class CommunityController {

	@Autowired
	private CoreService coreService;

	@RequestMapping(value = "/{id}", method = GET)
	public CommunityDTO findById(@PathVariable("id") Long id) {
		
		Community community = coreService.getCommunityRepository().findByIdAndActiveTrue(id);
		return new CommunityDTO(community);
	}

	@RequestMapping(method = GET)
	public List<CommunityDTO> list() {

		List<Community> list = coreService.getCommunityRepository().findByActiveTrue();

		return list.stream().map(CommunityDTO::new).collect(Collectors.toList());
	}

	@RequestMapping(method = POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public CommunityDTO create(@Valid @RequestBody CommunityForm form) {

		Community community = form.toEntity();
		// community.setOwner(user);
		community = coreService.getCommunityRepository().save(community);

		return new CommunityDTO(community);
	}

	@RequestMapping(value = "/{id}", method = PUT)
	public CommunityDTO update(@PathVariable("id") Long id, @Valid @RequestBody CommunityForm form) {

		Community community = coreService.getCommunityRepository().findOne(id);
		community.setDescription(form.getDescription());
		community.setImage(form.getImage());
		community.setName(form.getName());
		community.setTags(form.getTags());
		// community.setOwner(user);

		coreService.getCommunityRepository().save(community);

		return new CommunityDTO(community);
	}

	@RequestMapping(value = "/{id}", method = DELETE)
	public CommunityDTO delete(@PathVariable("id") Long id) {

		Community community = coreService.getCommunityRepository().findOne(id);
		community.setActive(false);

		coreService.getCommunityRepository().save(community);

		return new CommunityDTO(community);
	}

}
