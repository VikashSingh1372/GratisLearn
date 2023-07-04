package com.learn.Controllers;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learn.Dto.ContentDto;
import com.learn.Dto.subjectDto;
import com.learn.Dto.topicDto;
import com.learn.Dto.userDto;
import com.learn.Models.Course;
import com.learn.Models.Subject;
import com.learn.Models.Topic;
import com.learn.Repository.topicRepository;
import com.learn.Services.ContentService;
import com.learn.Services.CourseService;
import com.learn.Services.FileService;
import com.learn.Services.SubjectService;
import com.learn.Services.topicService;
import com.learn.payloads.Messages;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private SubjectService subService;

	@Autowired
	private topicService topService;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private CourseService courseService;

	@Value("${project.image}")
	private String path;

	// create subject
	@PostMapping("/save")
	public ResponseEntity<subjectDto> createSubject(@RequestBody subjectDto dto) {
		subjectDto createdSubject = this.subService.createSubject(dto);
		return new ResponseEntity<>(createdSubject, HttpStatus.CREATED);
	}

	// update subject
	@PutMapping("/update/{id}")
	public ResponseEntity<subjectDto> updateSubject(@RequestBody subjectDto dto, @PathVariable int id) {
		subjectDto updatedSubject = this.subService.updateSubject(dto, id);
		return new ResponseEntity<>(updatedSubject, HttpStatus.OK);
	}



	// delete subject
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Messages> deleteSubject(@PathVariable int id) {
		this.subService.deleteSubject(id);
		return new ResponseEntity(new Messages("Deleted Successfully", true), HttpStatus.OK);
	}
	// Api to handel topic requests

	// create topic

	
	// delete topic

	@DeleteMapping("/topic/delete/{id}")
	public ResponseEntity<?> deleteTopic(@PathVariable("id") int id) {
		this.topService.deleteTopic(id);
		return new ResponseEntity(new Messages("Deleted Successfully", true), HttpStatus.OK);

	}
	// update topic

	@PutMapping("/topic/update/{id}")
	public ResponseEntity<topicDto> updateTopic(@RequestBody topicDto topicdto, @PathVariable("id") int id) {
		topicDto updateTopic = this.topService.updateTopic(topicdto, id);
		return new ResponseEntity<>(updateTopic, HttpStatus.OK);

	}

	@PostMapping("/topic/{contId}/save")
	public ResponseEntity<topicDto> createTopic(@RequestBody topicDto topicdto, @PathVariable("contId") int contId) {
		topicDto createTopic = this.topService.createTopic(topicdto, contId);
		return new ResponseEntity<>(createTopic, HttpStatus.CREATED);

	}
	
	
	// delete content

	@DeleteMapping("/content/delete/{id}")
	public ResponseEntity<?> deleteContent(@PathVariable("id") int id) {
		this.contentService.deleteContent(id);
		return new ResponseEntity(new Messages("Deleted Successfully", true), HttpStatus.OK);

	}
	// update topic

	@PutMapping("/content/update/{id}")
	public ResponseEntity<ContentDto> updateContent(@RequestBody ContentDto contentdto, @PathVariable("id") int id) {
		ContentDto updateContent = this.contentService.updateContent(contentdto, id);
		return new ResponseEntity<>(updateContent, HttpStatus.OK);

	}

	@PostMapping("/content/{subId}/save")
	public ResponseEntity<ContentDto> createContent(@RequestBody ContentDto contentdto, @PathVariable("subId") int subId) {
		System.out.print("the cat id is " + subId);
	 ContentDto createContent = this.contentService.createContent(contentdto, subId);
		return new ResponseEntity<>(createContent, HttpStatus.CREATED);

	}
	

	// post image
	@PostMapping("/course/image/upload/{courseId}")
	public ResponseEntity<Course> uploadImage(@RequestParam("image") MultipartFile image
			,@PathVariable int courseId) throws IOException{
		Course course= this.courseService.getCourseById(courseId);  

		 String fileName = this.fileService.uploadImage(path ,image);
		 course.setImage(fileName);
	 Course updateCourse = this.courseService.updateCourse(course, courseId);
		return new ResponseEntity<Course>(updateCourse, HttpStatus.OK);
	}
	
	//method to serve image
	@GetMapping(value="/course/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}

	@PostMapping("/save/course")
	public ResponseEntity<Course> createCourse(@RequestBody Course course) {
		Course c = this.courseService.createCourse(course);
		return new ResponseEntity<>(c, HttpStatus.CREATED);
	}

	@PutMapping("/update/course/{id}")
	public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable int id) {
		Course Updatedcourse = this.courseService.updateCourse(course, id);
		return new ResponseEntity<>(Updatedcourse, HttpStatus.OK);
	}

	@DeleteMapping("/delete/course/{id}")
	public ResponseEntity<Messages> deleteCourse(@PathVariable int id) {
		this.courseService.deleteCourse(id);
		return new ResponseEntity(new Messages("Deleted Successfully", true), HttpStatus.OK);
	}

}
