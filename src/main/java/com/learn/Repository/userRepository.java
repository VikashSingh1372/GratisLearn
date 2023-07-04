package com.learn.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learn.Models.User;

public interface userRepository extends JpaRepository<User, Integer> {

	
	Optional<User> findByEmail(String email);
	

}
