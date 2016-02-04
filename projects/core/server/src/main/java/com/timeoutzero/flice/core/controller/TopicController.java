package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.security.CoreSecurityContext.isOwner;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.core.domain.Comment;
import com.timeoutzero.flice.core.domain.Topic;
import com.timeoutzero.flice.core.domain.User;
import com.timeoutzero.flice.core.dto.TopicDTO;
import com.timeoutzero.flice.core.enums.Role;
import com.timeoutzero.flice.core.form.TopicForm;
import com.timeoutzero.flice.core.form.UpdateTopicForm;
import com.timeoutzero.flice.core.service.CoreService;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;

@RestController
@RequestMapping("/community/{communityId}/topic")
public class TopicController {

	@Autowired
	private CoreService coreService;

	@Transactional(readOnly = true)
	@Secured({ Role.ANONYMOUS , Role.USER })
	@RequestMapping(value = "/{topicId}", method = GET)
	public TopicDTO findById(@PathVariable("topicId") Long id){

		Topic topic = coreService.getTopicRepository().findById(id);
		return new TopicDTO(topic);
	}

	@Transactional(readOnly = true)
	@Secured({ Role.ANONYMOUS , Role.USER })
	@RequestMapping(method = GET)
	public List<TopicDTO> list(
			@PathVariable("communityId") Long communityId, 
			@PageableDefault(direction = Direction.DESC, sort = "created") Pageable page){

		List<Topic> topics = coreService.getTopicRepository().findByCommunityId(communityId, page);
		List<AccountUserDTO> accounts = getAccountsByTopics(topics);

		return topics.stream()
				.map(TopicDTO::new)
				.map(joinAccountProfileIntoTopic(accounts))
				.collect(Collectors.toList());
	}

	private Function<TopicDTO, TopicDTO> joinAccountProfileIntoTopic(List<AccountUserDTO> accounts) {
		
		Function<TopicDTO, TopicDTO> mapper = dto -> {
		
			Optional<AccountUserDTO> optional = accounts.stream()
					.filter(account -> account.getId().equals(dto.getAuthor().getAccountId()))
					.findFirst();
			
			if (optional.isPresent()) {
			
				AccountUserDTO accountUserDTO = optional.get();
				
				dto.getAuthor().setProfile(accountUserDTO.getProfile());
				dto.setEditable(isOwner(accountUserDTO.getId())); 
			}
			return dto;
		};
		
		return mapper;
	}

	private List<AccountUserDTO> getAccountsByTopics(List<Topic> list) {
		
		List<Long> ids = Arrays.asList(list.stream()
				.map(t -> t.getOwner().getAccountId())
				.toArray(Long[]::new)); 
		 
		return ids.isEmpty() ? new ArrayList<>() : coreService.getAccountOperations().getUserOperations().list(ids);
	}

	@Transactional
	@RequestMapping(method = POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	@Secured({ Role.USER , Role.ADMIN })
	public TopicDTO create(@PathVariable("communityId") Long communityId, @Valid @RequestBody TopicForm form){

		User loggedUser = coreService.getLoggedUser();

		Topic topic = form.toEntity();
		topic.setCommunity(coreService.getCommunityRepository().findOne(communityId));
		topic.setOwner(loggedUser);
		
		coreService.getTopicRepository().save(topic);

		Comment comment = new Comment();
		comment.setOwner(loggedUser);
		comment.setTopic(topic);
		comment.setContent(form.getContent());
		
		coreService.getCommentRepository().save(comment);

		return new TopicDTO(topic);
	}

	@Transactional
	@Secured({ Role.USER , Role.ADMIN })
	@RequestMapping(value = "/{topicId}", method = PUT)
	public TopicDTO update(
			@PathVariable("communityId") Long communityId, 
			@PathVariable("topicId") Long id, @Valid @RequestBody UpdateTopicForm form){

		Topic topic = coreService.getTopicRepository().findOne(id);
		topic.setName(form.getName());

		topic = coreService.getTopicRepository().save(topic);

		return new TopicDTO(topic);
	}

	@Transactional
	@Secured({ Role.USER , Role.ADMIN })
	@RequestMapping(value = "/{topicId}", method = DELETE)
	public TopicDTO delete(@PathVariable("topicId") Long id){

		Topic topic = coreService.getTopicRepository().findOne(id);
		coreService.getTopicRepository().delete(id);

		return new TopicDTO(topic);
	}

}
