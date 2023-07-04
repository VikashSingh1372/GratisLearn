package com.learn;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.learn.Models.Subject;
import com.learn.Models.Topic;
import com.learn.Repository.topicRepository;

@SpringBootApplication
public class GratisLearnApplication {
	


	
	public static void main(String[] args) {
		SpringApplication.run(GratisLearnApplication.class, args);
	
	
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	

}
