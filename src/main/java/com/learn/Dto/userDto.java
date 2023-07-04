package com.learn.Dto;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class userDto {

	@NotNull
	private String userName;

	@Email
	private String userEmail;

	private String userPassword;

	@NotNull
	private String userAbout;
	
	
	
	@JsonIgnore
	public String getUserPassword() {
		return this.userPassword;
	}
	
	@JsonProperty
	 public void setUserPassword(String password) {
		 this.userPassword = password;
	 }

}
