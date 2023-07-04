package com.learn.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.learn.Models.User;
import com.learn.Models.UserQuiz;

public interface UserQuizRepo extends JpaRepository<UserQuiz,Integer> {
	
	@Query( value = "SELECT * FROM User_Quiz u WHERE u.user_id = user_id", nativeQuery = true)
	public List<UserQuiz> getquizByUserId(User user);



}
