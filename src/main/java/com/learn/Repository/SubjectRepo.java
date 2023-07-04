package com.learn.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.Models.Subject;

public interface SubjectRepo  extends JpaRepository<Subject, Integer>{

}
