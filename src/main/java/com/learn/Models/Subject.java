package com.learn.Models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subject {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private int id;
	  private String name;
	  //cascade means --- if we delete  or save parant then child will be deleted automatically
	  @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	  private List<Content> content = new ArrayList<>();
	 

}
