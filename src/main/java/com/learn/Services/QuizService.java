package com.learn.Services;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.learn.Dto.QuizDto;
import com.learn.Dto.quizSubjectDto;
import com.learn.Dto.topicDto;
import com.learn.Models.Quiz;
import com.learn.Models.Quizsubject;
import com.learn.Models.UserQuiz;

public interface QuizService {
	
	ResponseEntity<Object> allQuiz();
	ResponseEntity<Object> getActiveQuizzes();
	ResponseEntity<Object> getQuizResultById(String quizId);
	ResponseEntity<Object> createQuizSubject(quizSubjectDto quizDto);
	ResponseEntity<Object> createQuiz(QuizDto quizDto, int id);
	public List<Quiz> getAllBySubject(int id);




}
