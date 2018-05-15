package com.msd.project.codesniffer.model;


import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author fibin
 * @version 1.0
 * @since 23-Mar-2018
 */

@Entity
public class Assignment {
   
    private long id;
    private String name;
    private Date deadline;
    private double threshold;
    private boolean flag;
    private String language;
    private int numOfPrevSemsToCompare;
    private Course course;
    private List<Submission> submissions;
    
    
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
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	@JsonIgnore
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "course_id")
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY)
	public List<Submission> getSubmissions() {
		return submissions;
	}
	public void setSubmissions(List<Submission> submissions) {
		this.submissions = submissions;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getNumOfPrevSemsToCompare() {
		return numOfPrevSemsToCompare;
	}
	public void setNumOfPrevSemsToCompare(int numOfPrevSemsToCompare) {
		this.numOfPrevSemsToCompare = numOfPrevSemsToCompare;
	}
    
    
}
