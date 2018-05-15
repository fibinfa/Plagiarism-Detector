package com.msd.project.codesniffer.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CourseTest {
    Course course;
    Assignment assignment;
    Report report;
    ArrayList<Report> reports;
    User user;
    ArrayList<User> users;
    Date submitDate, uploadDate;

    @Before
    public void setUp() throws Exception {
        course = new Course();
        assignment = new Assignment();
        report = new Report();
        reports = new ArrayList<>(1);
        reports.add(report);

        user = new User();
        users = new ArrayList<User>(1);
        users.add(user);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getId() {
        course.setId(10101);
        assertEquals(10101, course.getId());
    }

    @Test
    public void setId() {
        course.setId(10101);
        assertEquals(10101, course.getId());
    }

    @Test
    public void getName() {
        course.setName("hot dog");
        assertEquals("hot dog", course.getName());
    }

    @Test
    public void setName() {
        course.setName("hot dog");
        assertEquals("hot dog", course.getName());
    }

    @Test
    public void assignmentTest() {
        List<Assignment> assignments = new ArrayList<>();
        Assignment ass1 = new Assignment();
        Assignment ass2 = new Assignment();

        assignments.add(ass1);
        assignments.add(ass2);

        course.setAssignments(assignments);
        assertEquals(assignments, course.getAssignments());
    }

    @Test
    public void semesterTest() {
        List<Semester> semesters = new ArrayList<>();
        Semester sem1 = new Semester();
        Semester sem2 = new Semester();

        semesters.add(sem1);
        semesters.add(sem2);

        course.setSemesters(semesters);
        assertEquals(semesters, course.getSemesters());
    }

    @Test
    public void usersTest() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();

        users.add(user1);
        users.add(user2);

        course.setUsers(users);
        assertEquals(users, course.getUsers());
    }
}