package com.learn.Services;

import java.util.List;

import com.learn.Models.Course;


public interface CourseService {
	
	public Course createCourse(Course course);


	
	public void deleteCourse(int id);

	public List<Course> allCourse();

	public Course getCourseById(int id);

	Course updateCourse(Course course, int id);

}
