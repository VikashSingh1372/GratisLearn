package com.learn.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.Models.QuizStatus;
import com.learn.Models.Quizsubject;

public interface QuizSubjectRepo extends JpaRepository<Quizsubject, Integer> {
	  List<Quizsubject> findAllByQuizStatus(QuizStatus quizStatus);

}
