package com.learn.Services;

import java.util.List;

import com.learn.Dto.userDto;
import com.learn.Models.Quiz;
import com.learn.Models.User;
import com.learn.Models.UserQuiz;

public interface userService {

	userDto createUser(userDto user);

	void deleteUser(int id);

	userDto getUserById(Integer id);

	List<userDto> getAllUser();
	userDto userToDto(User user);
	User dtoToUser(userDto dto);


	userDto updateUser(userDto user, int id);
	
	UserQuiz createUserQuiz(int userId,int subId,UserQuiz userQuiz, int result);


     List<UserQuiz> getquizByUser(int id);
     public List<UserQuiz> getQuizUserBySubjectId(int id);

	
	
}
