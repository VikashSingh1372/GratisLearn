package com.learn.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.Models.Quiz;
import com.learn.Models.Quizsubject;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
	
	public List<Quiz> findByQuizsubject(Quizsubject quiz);

}
