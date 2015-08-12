package com.timeoutzero.flice.core.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.joda.time.LocalDateTime;
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

@Transactional
@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CoreService coreService;

	@RequestMapping(value="/{id}", method=GET)
	public CommentDTO findById(@PathVariable("id") Long id){
		Comment comment = coreService.getCommentRepository().findByIdAndActiveTrue(id);
		return new CommentDTO(comment);
	}

	@RequestMapping(method=GET)
	public List<CommentDTO> list(){

		List<Comment> list = coreService.getCommentRepository().findByActiveTrue();

		return list.stream()
				.map(CommentDTO::new)
				.collect(Collectors.toList());
	}

	@RequestMapping(method=POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public CommentDTO create(@Valid @RequestBody CommentForm form){

		Comment comment = form.toEntity();
		comment.setActive(true);
		comment.setCreated(LocalDateTime.now());
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
