package com.msd.project.codesniffer.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SemesterTest {



    Semester semester = new Semester();

    @Test
    public void idTest() {
        semester.setId(10101);
        assertEquals(10101, semester.getId());
    }

    @Test
    public void nameTest() {
        semester.setName("fall16");
        assertEquals("fall16", semester.getName());
    }

    @Test
    public void usersTest() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();

        users.add(user1);
        users.add(user2);

        semester.setUsers(users);
        assertEquals(users, semester.getUsers());
    }

    @Test
    public void coursesTest() {
        List<Course> courses = new ArrayList<>();
        Course c1 = new Course();
        Course c2 = new Course();

        courses.add(c1);
        courses.add(c2);

        semester.setCourses(courses);
        assertEquals(courses, semester.getCourses());
    }
}