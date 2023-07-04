package com.learn.Dto;

import javax.persistence.Entity;

import com.learn.Models.Subject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class topicDto {

	private int id;
	
	private String heading;

	private String data;
	private String Image;
	
	
	

}
