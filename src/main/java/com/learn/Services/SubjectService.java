package com.learn.Services;

import java.util.List;

import com.learn.Dto.subjectDto;
import com.learn.Models.Subject;

public interface SubjectService {

	// create
	public subjectDto createSubject(subjectDto subjectdto);

	// update
	public subjectDto updateSubject(subjectDto subjectdto, int id);

	// delete
	public void deleteSubject(int id);

	// getall
	public List<subjectDto> allSubject();

	// getbyId
	public subjectDto getSubjectById(int id);

	public subjectDto SubjectToDto(Subject subject);

	public Subject DtoToSubject(subjectDto subjectdto);

}
