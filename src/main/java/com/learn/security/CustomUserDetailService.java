package com.learn.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learn.Models.User;
import com.learn.Repository.userRepository;
import com.learn.exceptions.ResourceNotFoundException;


@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private userRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user", "id", username));
		return user;
	}

}
