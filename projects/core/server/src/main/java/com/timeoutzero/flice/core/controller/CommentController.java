package com.timeoutzero.flice.core.controller;

import static com.timeoutzero.flice.core.security.CoreSecurityContext.getLoggedUser;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import com.timeoutzero.flice.core.dto.CommentDTO;
import com.timeoutzero.flice.core.enums.Role;
import com.timeoutzero.flice.core.form.CommentForm;
import com.timeoutzero.flice.core.service.CoreService;
import com.timeoutzero.flice.rest.dto.AccountUserDTO;

@Transactional
@RestController
@RequestMapping("/topic/{topicId}/comment")
public class CommentController {

	@Autowired
	private CoreService coreService;

	@Secured({ Role.ANONYMOUS , Role.USER})
	@RequestMapping(value = "/{commentId}", method = GET)
	public CommentDTO findById(@PathVariable("commentId") Long id){
		Comment comment = coreService.getCommentRepository().findById(id);
		return new CommentDTO(comment);
	}

	@Secured({ Role.ANONYMOUS , Role.USER})
	@RequestMapping(method = GET)
	public List<CommentDTO> list(@PathVariable("topicId") Long topicId, @PageableDefault Pageable pageable){

		List<Comment> list = coreService.getCommentRepository().findByTopicId(topicId, pageable);
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

	@Secured({ Role.USER, Role.ADMIN })
	@ResponseStatus(value = HttpStatus.CREATED)
	@RequestMapping(method = POST)
	public CommentDTO create(@PathVariable("topicId") Long topicId, @Valid @RequestBody CommentForm form){

		Comment comment = form.toEntity();
		comment.setOwner(getLoggedUser());
		comment.setTopic(coreService.getTopicRepository().findOne(topicId));

		comment = coreService.getCommentRepository().save(comment);

		return new CommentDTO(comment);
	}

	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(value = "/{commentId}", method=PUT)
	public CommentDTO update(@PathVariable("topicId") Long topicId, @PathVariable("commentId") Long commendId, @Valid @RequestBody CommentForm form){

		Comment comment = coreService.getCommentRepository().findOne(commendId);
		comment.setContent(form.getContent());
		comment.setTopic(coreService.getTopicRepository().findOne(topicId));

		comment = coreService.getCommentRepository().save(comment);

		return new CommentDTO(comment);
	}

	@Secured({ Role.USER, Role.ADMIN })
	@RequestMapping(value = "/{id}", method = DELETE)
	public CommentDTO delete(@PathVariable("id") Long id){

		Comment comment = coreService.getCommentRepository().findOne(id);
		coreService.getCommentRepository().delete(comment);

		return new CommentDTO(comment);
	}
}
