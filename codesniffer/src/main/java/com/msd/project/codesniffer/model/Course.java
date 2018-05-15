package com.msd.project.codesniffer.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author fibin
 * @version 1.0
 * @since 23-Mar-2018
 */

@Entity
public class Course {
    
    private long id;
    private String name;
    private String recipientEmail;
    private List<Semester> semesters;
    private List<Assignment> assignments;
    private List<User> users;
    
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
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	public List<Assignment> getAssignments() {
		return assignments;
	}
	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
	@JsonIgnore
	@ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
	public List<Semester> getSemesters() {
		return semesters;
	}
	public void setSemesters(List<Semester> semesters) {
		this.semesters = semesters;
	}
    
	@JsonIgnore
	@ManyToMany(mappedBy="courses", fetch=FetchType.LAZY)
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public String getRecipientEmail() {
		return recipientEmail;
	}
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	
	

}
