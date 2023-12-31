package com.learn.Controllers;

import java.text.ParseException;
import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.learn.Dto.QuizDto;
import com.learn.Dto.quizSubjectDto;
import com.learn.Models.Quiz;
import com.learn.Models.UserQuiz;
import com.learn.Services.QuizService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;


@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;
    
    private final Bucket bucket;

    
    public QuizController() {
        Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50,Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @PostMapping("/save/{id}")
    public ResponseEntity<Object> createQuiz(@RequestBody QuizDto quizDto,@PathVariable("id") int id)throws ParseException {
    	
        if (bucket.tryConsume(1)) {
            return quizService.createQuiz(quizDto,id);

        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();

    }
    @PostMapping()
    public ResponseEntity<Object> createQuizSubject(@RequestBody quizSubjectDto quizDto) throws ParseException {
    	
        if (bucket.tryConsume(1)) {
            return quizService.createQuizSubject(quizDto);

        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();

    }

    @GetMapping("/active")
    public ResponseEntity<Object> getActiveQuizzes() {
    	if(bucket.tryConsume(1)) {
            return quizService.getActiveQuizzes();

    	}
    	 return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping("/{id}/result")
    public ResponseEntity<Object> getQuizResultById( @RequestBody @PathVariable(value = "id") String quizId) {
    	
    	if(bucket.tryConsume(1)) {
        return quizService.getQuizResultById(quizId);
        }
    	return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping("/all")
    public ResponseEntity<Object> allQuiz() {
    	if(bucket.tryConsume(1)) {
    		return quizService.allQuiz();
    	}
    	return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();

    }
    @GetMapping("/quizBySub/{id}")
    public List<Quiz> getQuizByQuizSubject(@PathVariable int id) {
    	if(bucket.tryConsume(1)) {
    		
    	}
    	List<Quiz> allBySubject = quizService.getAllBySubject(id);
    	return allBySubject;
    }


}
