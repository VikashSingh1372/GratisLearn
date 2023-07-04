package com.learn.Dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learn.Models.QuizStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class quizSubjectDto {
	 private int id;
	  private String name;
	  
		@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
		private Date startDate;
		
		@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
		private Date endDate;

		private QuizStatus quizStatus;

}
