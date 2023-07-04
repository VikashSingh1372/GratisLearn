package com.learn.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Quizsubject {
	 @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private int id;
	  private String name;
	  
		@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
		private Date startDate;
		
		@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
		private Date endDate;

		private QuizStatus quizStatus;
		
		  @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
		  @JsonIgnore
		  private List<UserQuiz> userSubject = new ArrayList<>();

	  //cascade means --- if we delete  or save parant then child will be deleted automatically
	  @OneToMany(mappedBy = "quizsubject", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	  @JsonIgnore
	  private List<Quiz> quiz = new ArrayList<>();
	 
}
