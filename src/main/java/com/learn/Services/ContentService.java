package com.learn.Services;

import java.util.List;

import com.learn.Dto.ContentDto;
import com.learn.Dto.topicDto;
import com.learn.Models.Content;
import com.learn.Models.Topic;

public interface ContentService {
	public void deleteContent(int id);

	public ContentDto getContentByid(int id);

	public List<ContentDto> getAllBySubjectId(int id);

	public List<ContentDto> allContent();

	public ContentDto contentToDto(Content content);

	public Content dtoToContent(ContentDto contentdto);


	ContentDto updateContent(ContentDto contentdto, int id);


	ContentDto createContent(ContentDto contentdto, int subjectId);


}
