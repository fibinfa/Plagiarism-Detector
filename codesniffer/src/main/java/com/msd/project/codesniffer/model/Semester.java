package com.msd.project.codesniffer.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
   @author fibin
   @version 1.0
   @since 26-Mar-2018
*/
@Entity
public class Semester {
	private long id;
	private String name;
	private List<User> users;
	private List<Course> courses;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	@ManyToMany(mappedBy="semesters", fetch=FetchType.LAZY)
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name= "sem_course",
			joinColumns= {@JoinColumn(name = "semester_id", referencedColumnName="id")},
			inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName= "id")}
			)
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
}
