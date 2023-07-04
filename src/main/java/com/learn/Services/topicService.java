package com.learn.Services;

import java.util.List;

import com.learn.Dto.topicDto;
import com.learn.Models.Topic;

public interface topicService {

	public void deleteTopic(int id);

	public topicDto getTopicByid(int id);

	public List<topicDto> getAllByContentId(int id);

	public List<topicDto> allTopic(int pageNumber, int pageSize);

	public topicDto topicToDto(Topic topic);

	public Topic dtoToTopic(topicDto topicdto);


	topicDto updateTopic(topicDto topicdto, int id);


	topicDto createTopic(topicDto topicdto, int contentId);

}
