package com.learn.payloads;

import lombok.Setter;

import com.learn.Dto.userDto;
import com.learn.Models.User;

import lombok.Getter;

@Getter
@Setter
public class JwtAuthResponse {
	private String token;
  private User user;
}
