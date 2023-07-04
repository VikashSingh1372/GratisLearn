package com.learn.Services.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.learn.Dto.topicDto;
import com.learn.Models.Content;
import com.learn.Models.Subject;
import com.learn.Models.Topic;
import com.learn.Repository.ContentRepository;
import com.learn.Repository.SubjectRepo;
import com.learn.Repository.topicRepository;
import com.learn.Services.topicService;
import com.learn.exceptions.ResourceNotFoundException;

@Service
public class topicServicaeImpl implements topicService {

	@Autowired
	private topicRepository topicRepo;

	@Autowired
	private ContentRepository contentRepo;

	@Override
	public topicDto createTopic(topicDto topicdto, int contentId) {
		Content content = this.contentRepo.findById(contentId)
				.orElseThrow(() -> new ResourceNotFoundException("content", "Id", contentId));
		Topic topic = this.dtoToTopic(topicdto);
		int id = content.getId();
		System.out.println("id of contentt is " + id);
		topic.setContent(content);
		topic.setData(topicdto.getData());
		topic.setHeading(topicdto.getHeading());

		Topic savedTopic = this.topicRepo.save(topic);

		return this.topicToDto(savedTopic);
	}

	@Override
	public void deleteTopic(int id) {
		this.topicRepo.deleteById(id);
		// TODO Auto-generated method stub

	}

	@Override
	public topicDto updateTopic(topicDto topicdto, int id) {
		Topic topic = this.topicRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("topic", "topicid", id));

		topic.setHeading(topicdto.getHeading());
		topic.setData(topicdto.getData());
		topic.setImage(topicdto.getImage());
		Topic save = this.topicRepo.save(topic);

		// TODO Auto-generated method stub
		return this.topicToDto(save);
	}

	@Override
	public topicDto getTopicByid(int id) {
		Topic topic = this.topicRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("topic", "topicid", id));

		// TODO Auto-generated method stub
		return this.topicToDto(topic);
	}

	@Override
	public List<topicDto> getAllByContentId(int id) {
		Content content = this.contentRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("content", "cId", id));
		List<Topic> topics = topicRepo.findByContent(content);
		List<topicDto> topicDto = topics.stream().map(topic -> this.topicToDto(topic)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return topicDto;
	}

	@Override
	public List<topicDto> allTopic(int pageNumber, int pageSize) {
		PageRequest p = PageRequest.of(pageNumber, pageSize);
		Page<Topic> pagePost = this.topicRepo.findAll(p);
		List<Topic> alltopic = pagePost.getContent();
		List<topicDto> topicDto = alltopic.stream().map(topics -> this.topicToDto(topics)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return topicDto;
	}

	@Override
	public topicDto topicToDto(Topic topic) {
		topicDto dto = new topicDto();
		dto.setId(topic.getId());
		dto.setHeading(topic.getHeading());
		dto.setData(topic.getData());
		dto.setImage(topic.getImage());
		// TODO Auto-generated method stub
		return dto;
	}

	@Override
	public Topic dtoToTopic(topicDto topicdto) {
		Topic topic = new Topic();
		topic.setId(topicdto.getId());
		topic.setHeading(topicdto.getHeading());
		topic.setData(topicdto.getData());
		topic.setImage(topicdto.getImage());
		// TODO Auto-generated method stub
		return topic;
	}

}
