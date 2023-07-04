package com.learn.Services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.Models.Course;
import com.learn.Models.Subject;
import com.learn.Repository.CourseRepo;
import com.learn.Services.CourseService;
import com.learn.exceptions.ResourceNotFoundException;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepo courseRepo;

	@Override
	public Course createCourse(Course course) {
		int courseDiscount = course.getDiscount();
		int mrp = course.getMrp();
		int price = course.getPrice();
		int discountPrice = ((mrp * courseDiscount) / 100);
		int realprice = (mrp - discountPrice);
		course.setPrice(realprice);
		Course save = this.courseRepo.save(course);
		return save;
	}

	@Override
	public Course updateCourse(Course course, int id) {
		// TODO Auto-generated method stub
		Course c = this.courseRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course", "Id", id));
		c.setContent(course.getContent());
		c.setDiscount(course.getDiscount());

		c.setMrp(course.getMrp());
		int mrp = course.getMrp();
		int discount = course.getDiscount();
		int discountPrice = ((mrp * discount) / 100);
		int realprice = (mrp - discountPrice);
		c.setPrice(realprice);
		c.setImage(course.getImage());
		Course save = this.courseRepo.save(c);

		return save;
	}

	@Override
	public void deleteCourse(int id) {
		this.courseRepo.deleteById(id);
		// TODO Auto-generated method stub

	}

	@Override
	public List<Course> allCourse() {
		List<Course> findAll = this.courseRepo.findAll();
		// TODO Auto-generated method stub
		return findAll;
	}

	@Override
	public Course getCourseById(int id) {
		Course course = this.courseRepo.findById(id).get();
		return course;
	}

}
