package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.security.CoreSecurityContext.getLoggedUser;
import static com.timeoutzero.flice.core.security.CoreSecurityContext.getLoggedUserAuthoritys;
import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.core.domain.Community;
import com.timeoutzero.flice.core.domain.Tag;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.dto.CommunityDTO;
import com.timeoutzero.flice.core.dto.TagDTO;
import com.timeoutzero.flice.core.enums.Role;
import com.timeoutzero.flice.core.form.CommunityForm;
import com.timeoutzero.flice.core.service.CoreService;
import com.timeoutzero.flice.core.service.ImageService;
import com.timeoutzero.flice.core.service.ImageService.TYPE;

@RestController
@RequestMapping("/community")
public class CommunityController {

	@Autowired
	private CoreService coreService;
	
	@Autowired
	private ImageService imageService;
	
	//https://api.myjson.com/bins/4gdhe
	@Transactional(readOnly = true)
	@Secured({ Role.ANONYMOUS , Role.USER})
	@RequestMapping(value = "/{id}", method = GET)
	public CommunityDTO get(@PathVariable("id") Long id) {
		
		Community community = coreService.getCommunityRepository().findById(id);
		
		return new CommunityDTO(community);
	}
	
	@Transactional(readOnly = true)
	@Secured({ Role.ANONYMOUS, Role.USER})
	@RequestMapping(value = "/{id}/member/info", method = GET)
	public Map<String, Boolean> info(@PathVariable("id") Long id) {

		Community community = coreService.getCommunityRepository().findById(id);

		Map<String, Boolean> dto = new HashMap<>();
		dto.put("owner", community.getOwner().getId().equals(getLoggedUser().getId()));
		dto.put("member", coreService.getCommunityRepository().isMember(community.getId(), getLoggedUser().getId()));
		
		return dto;
	}

	@Transactional(readOnly = true)
	@Secured({ Role.ANONYMOUS , Role.USER})
	@RequestMapping(method = GET)
	public List<CommunityDTO> list() {
		
		List<Community> communitys = new ArrayList<>();
		
		if (getLoggedUserAuthoritys().contains(Role.ANONYMOUS)) { 
			communitys = coreService.getCommunityRepository()
					.findByPrivacityTrue();
		} else {
			communitys = coreService.getUserRepository()
					.findAllCommunityByUser(getLoggedUser().getId());
		}

		return communitys.stream()
				.map(CommunityDTO::new)
				.collect(toList());
	}
	
	@Transactional
	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(value = "/{id}/join", method = PUT)
	public CommunityDTO join(@PathVariable("id") Long id) {
		
		Community community = coreService.getCommunityRepository().findOne(id);
		community.getMembers().add(coreService.getLoggedUser());
		
		coreService.getCommunityRepository().save(community);
		
		return new CommunityDTO(community);
	}

	@Transactional
	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(method = POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public CommunityDTO create(@Valid @RequestBody CommunityForm form) {

		User loggedUser = coreService.getLoggedUser();
		
		Community community = form.toEntity();
		community.setOwner(loggedUser);
		community.getMembers().add(loggedUser);
		
		community = coreService.getCommunityRepository().save(community);
		
		community.setTags(getTags(form));
		community.setImage(imageService.write(community, form.getImage(), TYPE.THUMB));
		community.setCover(imageService.write(community, form.getCover(), TYPE.COVER));

		loggedUser.getCommunitys().add(community);
		coreService.getUserRepository().save(loggedUser);
		
		return new CommunityDTO(community);
	}

	private Set<Tag> getTags(CommunityForm form) {
		return  form.getTags().stream().map(tagForm -> { 
			 
			Tag tag = null;
			if (tagForm.getId() == null) {
				
				tag = coreService.getTagRepository().findByName(tagForm.getName());
				if (tag == null) {
					tag = new Tag(tagForm.getName());
					coreService.getTagRepository().save(tag);
				}
			} else {
				
				tag = coreService.getTagRepository().findOne(tagForm.getId());
			}
			
			return tag;
		}).collect(Collectors.toSet());
	}

	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(value = "/{id}", method = PUT)
	public CommunityDTO update(@PathVariable("id") Long id, @Valid @RequestBody CommunityForm form) {
		
		Community community = coreService.getCommunityRepository().findOne(id);
		community.setName(form.getName());
		community.setDescription(form.getDescription());
		community.setPrivacity(form.getPrivacity());
		
		if (Base64.isBase64(form.getImage())) {
			community.setImage(imageService.write(community, form.getImage(), TYPE.THUMB));
		}
		
		if (Base64.isBase64(form.getCover())) {
			community.setCover(imageService.write(community, form.getCover(), TYPE.COVER));
		}
		
		community.setTags(getTags(form));
		
		coreService.getCommunityRepository().save(community);

		return new CommunityDTO(community);
	}

	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(value = "/{id}", method = DELETE)
	public CommunityDTO delete(@PathVariable("id") Long id) {
		
		boolean isMember = coreService.getCommunityRepository().isMember(id, getLoggedUser().getId());
		
		if (isMember) {
			//throw new
		}

		Community community = coreService.getCommunityRepository().findOne(id);
		coreService.getCommunityRepository().delete(community);

		return new CommunityDTO(community);
	}

	
	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(value = "/tag/autocomplete")
	public List<TagDTO> getAutocompleteTags(@RequestParam("attr") String attr) {
		
		List<Tag> tags = coreService.getTagRepository().findByNameContaining(attr);
		
		return tags
				.stream().map(TagDTO::new)
				.collect(Collectors.toList());
	}
}
