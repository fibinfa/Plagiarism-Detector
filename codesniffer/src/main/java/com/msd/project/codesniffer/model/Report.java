package com.msd.project.codesniffer.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author fibin
 * @version 1.0
 * @since 23-Mar-2018
 */

@Entity
@Table
public class Report {
    
    private long id;
    private List<Submission> submissions;
    private double similarityScore;
    private String reportLink;
    
    @Id
    @GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public double getSimilarityScore() {
		return similarityScore;
	}
	public void setSimilarityScore(double similarityScore) {
		this.similarityScore = similarityScore;
	}
	public String getReportLink() {
		return reportLink;
	}
	public void setReportLink(String reportLink) {
		this.reportLink = reportLink;
	}
	@ManyToMany(mappedBy = "reports", fetch = FetchType.EAGER)
	public List<Submission> getSubmissions() {
		return submissions;
	}
	public void setSubmissions(List<Submission> submissions) {
		this.submissions = submissions;
	}
    
    
}
