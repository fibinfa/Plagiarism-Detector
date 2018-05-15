package com.msd.project.codesniffer.model;

import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

public class AssignmentTest {
    Assignment assignment;
    Report report;
    User user;
    Date submitDate, uploadDate;

    @org.junit.Before
    public void setUp() throws Exception {
        assignment = new Assignment();
        report = new Report();
        user = new User();
        submitDate = new Date(2018, 3, 23);
        uploadDate = new Date(1989, 2, 31);
    }

    @Test
    public void getId() {
        assignment.setId(101);
        assertEquals(101, assignment.getId());
    }

    @Test
    public void setId() {
        assignment.setId(101);
        assertEquals(101, assignment.getId());
    }

    @Test
    public void getName() {
        assignment.setName("Sid");
        assertEquals("Sid", assignment.getName());
    }

    @Test
    public void thresholdTest() {
        assignment.setThreshold(85);
        assertEquals(85, assignment.getThreshold(), 0.1);
    }

    @Test
    public void deadlineTest() {
        Date d = new Date(1000000);
        assignment.setDeadline(d);
        assertEquals(d, assignment.getDeadline());
    }

    @Test
    public void flagTest() {

        assignment.setFlag(true);
        assertEquals(true, assignment.isFlag());
    }

}