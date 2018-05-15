package com.msd.project.codesniffer.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User implements Serializable{
   
    private long id;
    private String username;
    private String fName;
    private String lName;
    private String email;
    private Role userType;
    private String password;
    private List<Semester> semesters;
    private List<Submission> submissions;
    private List<Course> courses;
    
    @Id
    @GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Role getUserType() {
		return userType;
	}
	public void setUserType(Role userType) {
		this.userType = userType;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "user_semester",
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName= "id")},
			inverseJoinColumns = {@JoinColumn(name = "semester_id", referencedColumnName= "id")}
			)
	public List<Semester> getSemesters() {
		return semesters;
	}
	public void setSemesters(List<Semester> semesters) {
		this.semesters = semesters;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	public List<Submission> getSubmissions() {
		return submissions;
	}
	public void setSubmissions(List<Submission> submissions) {
		this.submissions = submissions;
	}
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "user_course",
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName= "id")},
			inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName= "id")}
			)
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
    
}