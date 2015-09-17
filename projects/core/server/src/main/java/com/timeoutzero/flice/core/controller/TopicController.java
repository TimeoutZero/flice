package com.timeoutzero.flice.core.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.timeoutzero.flice.core.domain.Topic;
import com.timeoutzero.flice.core.dto.TopicDTO;
import com.timeoutzero.flice.core.form.TopicForm;
import com.timeoutzero.flice.core.service.CoreService;

@RestController
@RequestMapping("/topic")
public class TopicController {

	@Autowired
	private CoreService coreService;

	@RequestMapping(value="/{id}", method=GET)
	public TopicDTO findById(@PathVariable("id") Long id){

		Topic topic = coreService.getTopicRepository().findByIdAndActiveTrue(id);
		return new TopicDTO(topic);
	}

	@RequestMapping(method=GET)
	public List<TopicDTO> list(){

		List<Topic> list = coreService.getTopicRepository().findByActiveTrue();

		List<TopicDTO> dtos = new ArrayList<TopicDTO>();
		for(Topic topic : list){
			dtos.add(new TopicDTO(topic));
		}

		return dtos;
	}

	@RequestMapping(method=POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public TopicDTO create(@Valid @RequestBody TopicForm form){

		Topic topic = form.toEntity();

		topic.setCreated(DateTime.now());
		topic.setActive(true);
		topic.setCommunity(coreService.getCommunityRepository().findOne(form.getCommunityId()));
		//		topic.setOwner(user);

		topic = coreService.getTopicRepository().save(topic);

		TopicDTO topicDTO = new TopicDTO(topic);
		return topicDTO;
	}

	@RequestMapping(value="/{id}", method=PUT)
	public TopicDTO update(@PathVariable("id") Long id, @Valid @RequestBody TopicForm form){

		Topic topic = coreService.getTopicRepository().findOne(id);
		topic.setCommunity(coreService.getCommunityRepository().findOne(form.getCommunityId()));
		topic.setName(form.getName());
		//		topic.setOwner(user);

		topic = coreService.getTopicRepository().save(topic);

		return new TopicDTO(topic);
	}

	@RequestMapping(value="/{id}", method = DELETE)
	public TopicDTO delete(@PathVariable("id") Long id){

		Topic topic = coreService.getTopicRepository().findOne(id);
		topic.setActive(false);
		topic = coreService.getTopicRepository().save(topic);

		return new TopicDTO(topic);
	}

}
