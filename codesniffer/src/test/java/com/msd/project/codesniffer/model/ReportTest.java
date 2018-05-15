package com.msd.project.codesniffer.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReportTest {
    Assignment a1, a2;
    Course course;
    Report report;

    @org.junit.Before
    public void setUp() throws Exception {
        a1 = new Assignment();
        a2 = new Assignment();
        course = new Course();
        report = new Report();
    }

    @Test
    public void getId() {
        report.setId(248);
        assertEquals(248, report.getId());
    }

    @Test
    public void setId() {
        report.setId(248);
        assertEquals(248, report.getId());
    }

    @Test
    public void similarityScoreTest() {
        Double percentage = 67.8;
        report.setSimilarityScore(percentage);
        assertEquals(percentage, report.getSimilarityScore(), 0.1);
    }

    @Test
    public void reportLinkTest() {
        String str = "abc";
        report.setReportLink(str);
        assertEquals(str, report.getReportLink());
    }

    @Test
    public void submissionTest() {
        List<Submission> submissions = new ArrayList<>();
        Submission sub = new Submission();
        Submission sub2 = new Submission();
        submissions.add(sub);
        submissions.add(sub2);
        report.setSubmissions(submissions);
        assertEquals(submissions, report.getSubmissions());
    }
}