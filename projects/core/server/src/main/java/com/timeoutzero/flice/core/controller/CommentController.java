package com.timeoutzero.flice.core.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.core.domain.Comment;
import com.timeoutzero.flice.core.dto.CommentDTO;
import com.timeoutzero.flice.core.form.CommentForm;
import com.timeoutzero.flice.core.service.CoreService;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;

@Transactional
@RestController
@RequestMapping("/topic/{topicId}/comment")
public class CommentController {

	@Autowired
	private CoreService coreService;

	@RequestMapping(value = "/{commentId}", method = GET)
	public CommentDTO findById(@PathVariable("commentId") Long id){
		Comment comment = coreService.getCommentRepository().findByIdAndActiveTrue(id);
		return new CommentDTO(comment);
	}

	@RequestMapping(method = GET)
	public List<CommentDTO> list(@PathVariable("topicId") Long topicId){

		List<Comment> list = coreService.getCommentRepository().findByTopicIdAndActiveTrue(topicId);
		List<AccountUserDTO> accounts = getAccountsByComments(list);

		return list.stream()
				.map(CommentDTO::new)
				.map(joinAccountProfileIntoComment(accounts))
				.collect(Collectors.toList());
	}
	
	private List<AccountUserDTO> getAccountsByComments(List<Comment> list) {
		List<Long> ids = Arrays.asList(list.stream()
				.map(c -> c.getOwner().getAccountId())
				.toArray(Long[]::new)); 
		 
		return coreService.getAccountOperations().getUserOperations().list(ids);
	}
	

	private Function<CommentDTO, CommentDTO> joinAccountProfileIntoComment(List<AccountUserDTO> accounts) {
		
		Function<CommentDTO, CommentDTO> mapper = dto -> {
		
			Optional<AccountUserDTO> optional = accounts.stream()
					.filter(account -> account.getId().equals(dto.getAuthor().getId()))
					.findFirst();
			
			if (optional.isPresent()) {
				dto.getAuthor().setProfile(optional.get().getProfile());
			}
			return dto;
		};
		
		return mapper;
	}

	@RequestMapping(method=POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public CommentDTO create(@Valid @RequestBody CommentForm form){

		Comment comment = form.toEntity();
		comment.setActive(true);
		comment.setCreated(DateTime.now());
		//		comment.setOwner(user);
		comment.setTopic(coreService.getTopicRepository().findOne(form.getTopicId()));

		comment = coreService.getCommentRepository().save(comment);

		CommentDTO dto = new CommentDTO(comment);

		return dto;
	}

	@RequestMapping(value="/{id}", method=PUT)
	public CommentDTO update(@PathVariable("id") Long id, @Valid @RequestBody CommentForm form){

		Comment comment = coreService.getCommentRepository().findOne(id);
		comment.setContent(form.getContent());
		//		comment.setOwner(user);
		comment.setTopic(coreService.getTopicRepository().findOne(form.getTopicId()));

		comment = coreService.getCommentRepository().save(comment);

		return new CommentDTO(comment);
	}

	@RequestMapping(value="/{id}", method=DELETE)
	public CommentDTO delete(@PathVariable("id") Long id){

		Comment comment = coreService.getCommentRepository().findOne(id);
		comment.setActive(false);
		comment = coreService.getCommentRepository().save(comment);

		return new CommentDTO(comment);
	}
}
