package com.msd.project.codesniffer.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserTest {
    User alex;

    @org.junit.Before
    public void setUp() throws Exception {
        alex = new User();
        alex.setId(1);
        alex.setfName("Alex");
        alex.setlName("Smith");
        alex.setEmail("alex@gmail.com");
        alex.setUsername("alex.lee.1991");
        alex.setUserType(Role.STUDENT);
        alex.setPassword("abc123");
    }


    @Test
    public void getId() {
        assertEquals(1, (long) alex.getId());
    }

    @Test
    public void setId() {
        alex.setId(0);
        assertEquals(0, (long) alex.getId());
    }

    @Test
    public void getfName() {
        alex.setfName("alexy");
        assertEquals("alexy", alex.getfName());
    }

    @Test
    public void setfName() {
        alex.setfName("Alex");
        assertEquals("Alex", alex.getfName());
    }

    @Test
    public void getlName() {
        alex.setlName("Lee");
        assertEquals("Lee", alex.getlName());
    }

    @Test
    public void setlName() {
        alex.setlName("Burr");
        assertEquals("Burr", alex.getlName());
    }

    @Test
    public void getEmail() {
        alex.setEmail("alex.lee@gmail.com");
        assertEquals("alex.lee@gmail.com", alex.getEmail());
    }

    @Test
    public void setEmail() {
        alex.setEmail("alex.lee@gmail.com");
        assertEquals("alex.lee@gmail.com", alex.getEmail());
    }

    @Test
    public void getUserType() {
        alex.setUserType(Role.PROFESSOR);
        assertEquals(Role.PROFESSOR, alex.getUserType());
    }

    @Test
    public void setUserType() {
        alex.setUserType(Role.TA);
        assertEquals(Role.TA, alex.getUserType());
        alex.setUserType(Role.ADMIN);
        assertEquals(Role.ADMIN, alex.getUserType());
    }

    @Test
    public void getPassword() {
        alex.setPassword("wat");
        assertEquals("wat", alex.getPassword());
    }

    @Test
    public void setPassword() {
        alex.setPassword("!@#$ ");
        assertEquals("!@#$ ", alex.getPassword());
    }

    @Test
    public void getUsername() {
        alex.setUsername("alex");
        assertEquals("alex", alex.getUsername());
    }

    @Test
    public void setUsername() {
        alex.setUsername("alex.lee");
        assertEquals("alex.lee", alex.getUsername());
    }

    @Test
    public void coursesTest() {
        List<Course> courses = new ArrayList<>();
        Course c1 = new Course();
        Course c2 = new Course();

        courses.add(c1);
        courses.add(c2);

        alex.setCourses(courses);
        assertEquals(courses, alex.getCourses());
    }

    @Test
    public void semestersTest() {
        List<Semester> semesters = new ArrayList<>();
        Semester sem1 = new Semester();
        Semester sem2 = new Semester();

        semesters.add(sem1);
        semesters.add(sem2);

        alex.setSemesters(semesters);
        assertEquals(semesters, alex.getSemesters());
    }

    @Test
    public void submissionTest() {
        List<Submission> submissions = new ArrayList<>();
        Submission sub1 = new Submission();
        Submission sub2 = new Submission();

        submissions.add(sub1);
        submissions.add(sub2);

        alex.setSubmissions(submissions);
        assertEquals(submissions, alex.getSubmissions());
    }
}