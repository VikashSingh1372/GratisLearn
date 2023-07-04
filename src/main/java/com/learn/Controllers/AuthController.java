package com.learn.Controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learn.Dto.ContentDto;
import com.learn.Dto.subjectDto;
import com.learn.Dto.topicDto;
import com.learn.Dto.userDto;
import com.learn.Models.Course;
import com.learn.Models.User;
import com.learn.Services.ContentService;
import com.learn.Services.CourseService;
import com.learn.Services.FileService;
import com.learn.Services.SubjectService;
import com.learn.Services.topicService;
import com.learn.Services.userService;
import com.learn.payloads.JwtAuthRequest;
import com.learn.payloads.JwtAuthResponse;
import com.learn.security.JwtHelper;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JwtHelper helper;

	@Autowired
	private UserDetailsService userdetailservce;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private userService userservice;
	
	@Autowired
	private SubjectService subService;
	
	@Autowired
	private topicService topService;
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private FileService fileService;
	

	@Value("${project.image}")
	private String path;
	

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {

		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userdetail = this.userdetailservce.loadUserByUsername(request.getUsername());
		String token = this.helper.generateToken(userdetail);
		JwtAuthResponse r = new JwtAuthResponse();
		r.setToken(token);
		User user = (User)userdetail;
		r.setUser(user);
	
		return new ResponseEntity<JwtAuthResponse>(r, HttpStatus.OK);

	}

	@PostMapping("/")
	public ResponseEntity<userDto> createUser(@RequestBody userDto userdto) {
		userDto createUserDto = this.userservice.createUser(userdto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	private void authenticate(String username, String password) {

		UsernamePasswordAuthenticationToken usernamepasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);

		try {
			this.authenticationManager.authenticate(usernamepasswordAuthenticationToken);

		} catch (DisabledException e) {
		}
	}
	
	// topicbyid
	
	@GetMapping("/topic/{id}")
	public ResponseEntity<topicDto> getTopicById(@RequestBody @PathVariable("id") int id) {
		topicDto topic = this.topService.getTopicByid(id);
		return ResponseEntity.ok(topic);

	}
	@GetMapping("/content/{id}")
	public ResponseEntity<ContentDto> getContentById(@RequestBody @PathVariable("id") int id) {
		ContentDto content = this.contentService.getContentByid(id);
		return ResponseEntity.ok(content);

	}

	// getAll subject
	@GetMapping("/all")
	public ResponseEntity<List<subjectDto>> allSubject() {
		List<subjectDto> allSubject = this.subService.allSubject();
		return ResponseEntity.ok(allSubject);

	}


	// getById subject
	@GetMapping("/subjectById/{id}")
	public ResponseEntity<subjectDto> subjectById(@PathVariable int id) {
		subjectDto subjectById = this.subService.getSubjectById(id);
		return ResponseEntity.ok(subjectById);

	}
	    // all topic
		@GetMapping("/allTopic")
		public ResponseEntity<List<topicDto>> allTopic(
				@RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber,
				@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize) {
			List<topicDto> allTopic = this.topService.allTopic(pageNumber, pageSize);
			return ResponseEntity.ok(allTopic);

		}
		@GetMapping("/allContent")
		public ResponseEntity<List<ContentDto>> allContent() {
			List<ContentDto> allContent = this.contentService.allContent();
			return ResponseEntity.ok(allContent);

		}
		// get topics by subjectId

		@GetMapping("/topicByContentId/{id}")
		public ResponseEntity<List<topicDto>> getTopicBySubjectId(@PathVariable("id") int id) {
			List<topicDto> topic = this.topService.getAllByContentId(id);
			return ResponseEntity.ok(topic);

		}
		@GetMapping("/contentBySubId/{id}")
		public ResponseEntity<List<ContentDto>> getContentBySubjectId(@PathVariable("id") int id) {
			List<ContentDto> content = this.contentService.getAllBySubjectId(id);
			return ResponseEntity.ok(content);

		}
		
		@GetMapping("/all/course")
		public ResponseEntity<List<Course>> allCourse() {
			 List<Course> allCourse = this.courseService.allCourse();
			return ResponseEntity.ok(allCourse);

		}

		@GetMapping("/courseById/{id}")
		public ResponseEntity<Course> courseById(@PathVariable int id) {
		 Course courseById = this.courseService.getCourseById(id);
			return ResponseEntity.ok(courseById);

		}
		
	
}
