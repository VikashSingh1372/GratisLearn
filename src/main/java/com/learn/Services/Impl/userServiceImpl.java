package com.learn.Services.Impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learn.Dto.userDto;
import com.learn.Models.Quizsubject;
import com.learn.Models.Role;
import com.learn.Models.User;
import com.learn.Models.UserQuiz;
import com.learn.Repository.QuizSubjectRepo;
import com.learn.Repository.RoleRepo;
import com.learn.Repository.UserQuizRepo;
import com.learn.Repository.userRepository;
import com.learn.exceptions.ResourceNotFoundException;
import com.learn.Services.userService;

@Service
public class userServiceImpl implements userService {

	@Autowired
	private userRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private QuizSubjectRepo quizSubRepo;
	
	@Autowired
	private UserQuizRepo userQuizRepo;

	static int roleid = 2;

	@Override
	public userDto createUser(userDto userdto) {
		User user = this.dtoToUser(userdto);
		user.setPassword(passwordEncoder.encode(userdto.getUserPassword()));

		Role role = this.roleRepo.findById(roleid).get();
		user.getRoles().add(role);

		User savedUser = this.userRepo.save(user);
		// TODO Auto-generated method stud
		return this.userToDto(savedUser);
	}

	@Override
	public userDto updateUser(userDto user, int id) {
		User user1 = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
	
		user1.setName(user.getUserName());
		user1.setAbout(user.getUserAbout());
		user1.setEmail(user.getUserEmail());
		User updatedUser = this.userRepo.save(user1);

		return this.userToDto(updatedUser);
	}

	@Override
	public void deleteUser(int id) {
		this.userRepo.deleteById(id);
		System.out.print("User deleted Successfully");

	}

	@Override
	public userDto getUserById(Integer id) {
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

		return this.userToDto(user);
	}

	@Override
	public List<userDto> getAllUser() {
		List<User> allUser = this.userRepo.findAll();
		List<userDto> userDtos = allUser.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	public User dtoToUser(userDto userdto) {
		User user = new User();
		user.setName(userdto.getUserName());
		user.setEmail(userdto.getUserEmail());
		user.setPassword((userdto.getUserPassword()));
		user.setAbout(userdto.getUserAbout());

		return user;

	}

	public userDto userToDto(User user) {
		userDto userdto = new userDto();
		userdto.setUserName(user.getName());
		userdto.setUserEmail(user.getEmail());
		userdto.setUserPassword((user.getPassword()));
		userdto.setUserAbout(user.getAbout());

		return userdto;

	}

	@Override
	public UserQuiz createUserQuiz(int userId, int subId, UserQuiz userQuiz,int result) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "id", subId));
		Quizsubject quiz = this.quizSubRepo.findById(subId).orElseThrow(()->new ResourceNotFoundException("user", "id", subId));
        userQuiz.setUser(user);
        userQuiz.setQuiz(quiz);
        userQuiz.setResult(result);
        
        UserQuiz userquiz = this.userQuizRepo.save(userQuiz);
		
		return userquiz;
	}

	@Override
	public List<UserQuiz> getquizByUser(int id) {
		User user = this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user", "id", id));
		 
		List<UserQuiz> userQuiz = this.userQuizRepo.getquizByUserId(user);
		System.out.println("user name is here" + user.getName());
		return userQuiz;
	}
	@Override
	public List<UserQuiz> getQuizUserBySubjectId(int id) {
		
		User user = this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user", "id", id));
		List<UserQuiz> userQuiz = this.userQuizRepo.getquizByUserId(user);
		System.out.println("user name is here" + user.getName());
		System.out.println("uq"+user.getUserQuiz());
		return userQuiz;
	}

}
