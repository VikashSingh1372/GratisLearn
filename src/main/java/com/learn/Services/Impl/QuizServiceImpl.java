package com.learn.Services.Impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.learn.exceptions.ResourceNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import static com.learn.Models.QuizStatus.ACTIVE;

import com.learn.Dto.QuizDto;
import com.learn.Dto.quizSubjectDto;
import com.learn.Dto.topicDto;
import com.learn.Models.Quiz;
import com.learn.Models.QuizStatus;
import com.learn.Models.Quizsubject;
import com.learn.Models.UserQuiz;
import com.learn.Repository.QuizRepository;
import com.learn.Repository.QuizSubjectRepo;
import com.learn.Repository.UserQuizRepo;
import com.learn.Services.QuizService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import static java.util.Objects.nonNull;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepo;

	@Autowired
	private QuizSubjectRepo quizSubRepo;
	
	@Autowired
	private UserQuizRepo userQuizRepo;

	@Override
	public ResponseEntity<Object> createQuiz(QuizDto quizDto, int id) {

		  
		Quiz quiz;
		if (nonNull(quizDto)) {
			quiz = new Quiz();
			// id is auto generated
			Quizsubject quizSub = this.quizSubRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", id));
                quiz.setQuizsubject(quizSub);
                
                       
			// set questions
			if (StringUtils.isNotBlank(quizDto.getQuestion()))
				quiz.setQuestion(quizDto.getQuestion());
			else
				return new ResponseEntity<>("Question should not be empty/null/blank", HttpStatus.valueOf(400));
			if (StringUtils.isNotBlank(quizDto.getName()))
				quiz.setName(quizDto.getName());
			else
				return new ResponseEntity<>("Name should not be empty/null/blank", HttpStatus.valueOf(400));

			// set options
			quiz.setOptions(quizDto.getOptions());
	    
			// set rightAnswerIndex
			if (nonNull(quizDto.getAnswer()))
				quiz.setAnswer(quizDto.getAnswer());
			else
				return new ResponseEntity<>(
						"Answer should not be empty/null " + "or should be pointing to the options element",
						HttpStatus.valueOf(400));
            quiz.setResult(quizDto.getResult());

			// persist in db
			Quiz saveQuiz = quizRepo.save(quiz);
			return new ResponseEntity<>(saveQuiz, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
		}

	}
	// active Quizzes

	@Override
	public ResponseEntity<Object> getActiveQuizzes() {
		// this need to check
		List<Quizsubject> activeQuizzes = quizSubRepo.findAllByQuizStatus(ACTIVE);

		if (!CollectionUtils.isEmpty(activeQuizzes))
			return new ResponseEntity<>(activeQuizzes, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// get quiz result by id
	@Override
	public ResponseEntity<Object> getQuizResultById(String quizId) {

		if (StringUtils.isNotBlank(quizId)) {
			Quiz quiz = quizRepo.findById(Integer.valueOf(quizId))
					.orElseThrow(() -> new ResourceNotFoundException("quiz", "Id", quizId));
			;

			if (nonNull(quiz))
				return new ResponseEntity<>(quiz.getResult(), HttpStatus.OK);

			else
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} else {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}

	}

	// all quiz
	@Override
	public ResponseEntity<Object> allQuiz() {
		List<Quizsubject> allQuiz = this.quizSubRepo.findAll();

		if (!CollectionUtils.isEmpty(allQuiz))
			return new ResponseEntity<>(allQuiz, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	@Override
	public ResponseEntity<Object> createQuizSubject(quizSubjectDto quizsubject) {

		Quizsubject quiz = new Quizsubject();
		if (nonNull(quizsubject)) {
			

			// id is auto generated
			// set startDate
			if (StringUtils.isNotBlank(quizsubject.getName()))
				quiz.setName(quizsubject.getName());
			else
				return new ResponseEntity<>("Name should not be empty/null/blank", HttpStatus.valueOf(400));

			if (Objects.nonNull(quizsubject.getStartDate()))
				quiz.setStartDate(quizsubject.getStartDate());
			else
				return new ResponseEntity<>("StartDate should not be empty/null", HttpStatus.valueOf(400));

			// set endDate
			if (Objects.nonNull(quizsubject.getEndDate()))
				quiz.setEndDate(quizsubject.getEndDate());
			else
				return new ResponseEntity<>("EndDate should not be empty/null ", HttpStatus.valueOf(400));

			// Set quizStatus based on startDate and endDate and currDate
			Date currDate = new Date();

			if ((Objects.nonNull(quizsubject.getStartDate())) && (Objects.nonNull(quizsubject.getEndDate()))
					&& (Objects.nonNull(currDate))) {

				if (currDate.after(quiz.getStartDate()) && currDate.before(quiz.getEndDate())) {
					quiz.setQuizStatus(QuizStatus.ACTIVE);
				} else if (currDate.before(quiz.getStartDate())) {
					quiz.setQuizStatus(QuizStatus.INACTIVE);
				} else if (currDate.after(quiz.getEndDate())) {
					quiz.setQuizStatus(QuizStatus.FINISHED);
				}
			} else {
				return new ResponseEntity<>("Date should not be empty/null ", HttpStatus.valueOf(400));
			}
			Quizsubject qz = this.quizSubRepo.save(quiz);
			
		}
		return new ResponseEntity<>(quiz, HttpStatus.OK);
	}

	@Override
	public List<Quiz> getAllBySubject(int id) {
	
   Quizsubject quiz= this.quizSubRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("quiz", "id", id));

		List<Quiz> Quiz = this.quizRepo.findByQuizsubject(quiz);

	
		return Quiz;
	}
	
	

	
}
