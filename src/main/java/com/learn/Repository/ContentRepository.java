package com.learn.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.Models.Content;
import com.learn.Models.Subject;
import com.learn.Models.Topic;

public interface ContentRepository extends JpaRepository<Content, Integer> {
	
	List<Content> findBySubject(Subject subject);
}
