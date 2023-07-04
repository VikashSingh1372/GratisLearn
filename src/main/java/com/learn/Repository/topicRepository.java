package com.learn.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.Models.Content;
import com.learn.Models.Subject;
import com.learn.Models.Topic;

public interface topicRepository  extends JpaRepository<Topic, Integer>{
	
	List<Topic> findByContent(Content content);

}
