package com.learn.Services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.learn.Dto.subjectDto;
import com.learn.Models.Subject;
import com.learn.Repository.SubjectRepo;
import com.learn.Services.SubjectService;
import com.learn.exceptions.ResourceNotFoundException;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepo subRepo;	

	@Override
	public subjectDto createSubject(subjectDto subjectdto) {
		Subject subject = this.DtoToSubject(subjectdto);
		Subject savedSub = this.subRepo.save(subject);
				return this.SubjectToDto(savedSub);
	}

	@Override
	public subjectDto updateSubject(subjectDto subjectdto, int id) {
		Subject t = this.subRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject", "Id", id));
		t.setName(subjectdto.getName());
		Subject updatedSubject = this.subRepo.save(t);
		return this.SubjectToDto(updatedSubject);
	}

	@Override
	public void deleteSubject(int id) {
		// TODO Auto-generated method stub
		this.subRepo.deleteById(id);
		
	}

	@Override
	public List<subjectDto> allSubject() {
		List<Subject> allSubject = this.subRepo.findAll();
		List<subjectDto> SubjectDto = allSubject.stream().map(subject -> this.SubjectToDto(subject)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		return SubjectDto;
	}

	@Override
	public subjectDto getSubjectById(int id) {
		 Subject sub = this.subRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject", "id", id));
		 subjectDto subjectToDto = this.SubjectToDto(sub);
			return subjectToDto;
		// TODO Auto-generated method stub
		
	}

	@Override
	public subjectDto SubjectToDto(Subject subject) {
		// TODO Auto-generated method stub
		 subjectDto dto = new subjectDto();
		 dto.setId(subject.getId());
		 dto.setName(subject.getName());
		return dto;
		
	}

	@Override
	public Subject DtoToSubject(subjectDto subjectdto) {
		// TODO Auto-generated method stub
		Subject subject = new Subject();
		subject.setId(subjectdto.getId());
		subject.setName(subjectdto.getName());
		return subject;
	}


}
