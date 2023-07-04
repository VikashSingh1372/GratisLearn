package com.learn.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.Dto.userDto;
import com.learn.Models.User;
import com.learn.Models.UserQuiz;
import com.learn.Services.QuizService;
import com.learn.Services.userService;
import com.learn.payloads.Messages;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private userService userservice;
	
	@Autowired
	private QuizService quizService;
	
	


	@PutMapping("/update/{userId}")
	public ResponseEntity<userDto> updateUser( @RequestBody @Valid userDto user, @PathVariable int userId) {
		userDto updateUser = this.userservice.updateUser(user, userId);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Messages> deleteUser(@PathVariable int userId) {
		this.userservice.deleteUser(userId);
		return new ResponseEntity(new Messages("User Deleted Successfully", true), HttpStatus.OK);

	}

	@GetMapping("/allUser")
	public ResponseEntity<List<userDto>> getAllUsers() {

		return ResponseEntity.ok(this.userservice.getAllUser());

	}

	@GetMapping("/userById/{userId}")
	public ResponseEntity<userDto> getSingleUsers( @PathVariable int userId){
		
		return ResponseEntity.ok(this.userservice.getUserById(userId));         
			
	
	}
	@PostMapping("/quiz/{userId}/{subId}/{result}")
	public ResponseEntity<UserQuiz> createUserQuiz( @RequestBody @PathVariable int userId,@PathVariable int subId,UserQuiz userQuiz,@PathVariable int result){
		
		 UserQuiz createdUserQuiz = this.userservice.createUserQuiz(userId, subId, userQuiz,result);
			return new ResponseEntity<>(createdUserQuiz, HttpStatus.CREATED);
	}

    @GetMapping("/subject/{id}")
	public List<UserQuiz>  getQuizUserBySubject(@PathVariable int id){
    	List<UserQuiz> userQuiz = this.userservice.getQuizUserBySubjectId(id);
		return userQuiz;
	}
	
	

}
