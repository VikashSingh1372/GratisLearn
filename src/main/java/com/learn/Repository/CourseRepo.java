package com.learn.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.Models.Course;

public interface CourseRepo extends JpaRepository<Course, Integer>{

}
