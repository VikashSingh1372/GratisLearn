package com.learn.Services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.learn.Dto.ContentDto;
import com.learn.Dto.topicDto;
import com.learn.Models.Content;
import com.learn.Models.Subject;
import com.learn.Models.Topic;
import com.learn.Repository.ContentRepository;
import com.learn.Repository.SubjectRepo;
import com.learn.Repository.topicRepository;
import com.learn.Services.ContentService;
import com.learn.Services.topicService;
import com.learn.exceptions.ResourceNotFoundException;


@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private SubjectRepo subRepo;
	
	@Autowired
	private ContentRepository contentRepo;

	@Override
	public void deleteContent(int id) {
		// TODO Auto-generated method stub
		this.contentRepo.deleteById(id);

	}

	@Override
	public ContentDto getContentByid(int id) {
		Content content = this.contentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("content", "cid", id));

		// TODO Auto-generated method stub
		return this.contentToDto(content);
	}

	@Override
	public List<ContentDto> getAllBySubjectId(int id) {
		Subject subject = this.subRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId", id));
		List<Content> c = this.contentRepo.findBySubject(subject);	
			 List<ContentDto> contentDto = c.stream().map(content -> this.contentToDto(content)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return contentDto;
	}

	@Override
	public List<ContentDto> allContent() {
		  List<Content> allcontent = this.contentRepo.findAll();
		List<ContentDto> contentDto = allcontent.stream().map(content-> this.contentToDto(content)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return contentDto;
	}

	@Override
	public ContentDto contentToDto(Content content) {
		// TODO Auto-generated method stub
		ContentDto dto = new ContentDto();
		dto.setId(content.getId());
		dto.setName(content.getName());
		dto.setDescription(content.getDescription());
		// TODO Auto-generated method stub
		return dto;
	}

	@Override
	public Content dtoToContent(ContentDto contentdto) {
		Content content = new Content();
		content.setId(contentdto.getId());
		content.setName(contentdto.getName());
		content.setDescription(contentdto.getDescription());
		// TODO Auto-generated method stub
		return content;
	}

	@Override
	public ContentDto updateContent(ContentDto contentdto, int id) {
		// TODO Auto-generated method stub
Content content = this.contentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("content", "cid", id));
		

		content.setName(contentdto.getName());
		content.setDescription(contentdto.getDescription());
        Content save = this.contentRepo.save(content);
		
		
		// TODO Auto-generated method stub
		return this.contentToDto(save);
	}

	@Override
	public ContentDto createContent(ContentDto contentdto, int subjectId) {
		// TODO Auto-generated method stub
		Subject subject =this.subRepo.findById(subjectId).orElseThrow(()-> new ResourceNotFoundException("Subject", "Id", subjectId));
		Content content = this.dtoToContent(contentdto);
		int id = subject.getId();
		System.out.print("id is here " +id +"0ok");
		content.setSubject(subject);
		content.setName(contentdto.getName());
		content.setDescription(contentdto.getDescription());
		content.setSubject(subject);
	
		
		
		Content savedContent = this.contentRepo.save(content);
		// TODO Auto-generated method stub
	
		
		return  this.contentToDto(savedContent);
	}

	
}
