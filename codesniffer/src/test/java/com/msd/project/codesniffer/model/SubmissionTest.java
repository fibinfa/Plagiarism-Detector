package com.msd.project.codesniffer.model;

import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SubmissionTest {

    Submission submission = new Submission();

    @Test
    public void idTest() {
        submission.setId(10101);
        assertEquals(10101, submission.getId());
    }

    @Test
    public void S3linkTest() {
        String link = "https://s3.amazonaws.com/codesniffer-reports/bd/match0.html";
        submission.setS3Link(link);
        assertEquals(link, submission.getS3Link());
    }

    @Test
    public void gitLinkTest() {
        String git = "https://github.ccs.neu.edu/cs5500/team-202";

        submission.setGitLink(git);
        assertEquals(git, submission.getGitLink());
    }

    @Test
    public void usesTest() {
        User user2 = new User();

        submission.setUser(user2);
        assertEquals(user2, submission.getUser());
    }

    @Test
    public void assignmentTest() {
        Assignment ass2 = new Assignment();
        submission.setAssignment(ass2);
        assertEquals(ass2, submission.getAssignment());
    }

    @Test
    public void uploadDateTest() {
        Date uploadDate = new Date(1800000);
        submission.setUploadDate(uploadDate);
        assertEquals(uploadDate, submission.getUploadDate());
    }

    @Test
    public void reportsTest() {
        List<Report> reports = new ArrayList<>();
        Report report1 = new Report();
        Report report2 = new Report();

        reports.add(report1);
        reports.add(report2);
        submission.setReports(reports);
        assertEquals(reports, submission.getReports());
    }
}