package com.msd.project.codesniffer.serviceImpl;

import com.msd.project.codesniffer.model.Course;
import com.msd.project.codesniffer.model.Role;
import com.msd.project.codesniffer.model.Semester;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.repository.CourseRepository;
import com.msd.project.codesniffer.repository.SemesterRepository;
import com.msd.project.codesniffer.repository.UserRepository;
import com.msd.project.codesniffer.service.AssignmentService;
import com.msd.project.codesniffer.service.CourseService;
import com.msd.project.codesniffer.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class CourseServiceImplTest {
    @TestConfiguration
    static class CourseServiceImplTestConfiguration {
        @Bean
        public CourseService courseService() {
            return new CourseServiceImpl();
        }
    }

    @Autowired
    CourseService courseService;

    @MockBean
    private CourseRepository cRepo;

    @MockBean
    private SemesterRepository sRepo;

    @MockBean
    private UserRepository uRepo;

    private User u1;
    private List<User> users;
    private Course c1, c2, c3;
    private List<Course> courses;
    private Semester s1;

    @Before
    public void setUp() throws Exception {
       c1 = new Course();
        c1.setId(30);
        c1.setName("Phy1");


        c2 = new Course();
        c2.setId(31);
        c2.setName("Phy2");

        c3 = new Course();
        c3.setId(32);
        c3.setName("Phy3");

        courses = new ArrayList<>(3);
        courses.add(c1);
        courses.add(c2);
        courses.add(c3);

        s1 = new Semester();
        s1.setId(42);
        s1.setName("2009Fall");
        s1.setCourses(courses);

        u1 = new User();
        u1.setId(1919);
        u1.setUsername("WAT");
        u1.setPassword("WAT");
        u1.setUserType(Role.PROFESSOR);
        u1.setCourses(courses);


        users = new ArrayList<>();
        users.add(u1);

        Mockito.when(cRepo.save(c1)).thenReturn(c1);
        Mockito.when(cRepo.save(c2)).thenReturn(c2);
        Mockito.when(cRepo.save(c3)).thenReturn(c3);

        Mockito.when(cRepo.findOne(c1.getId())).thenReturn(c1);
        Mockito.when(cRepo.findOne(c2.getId())).thenReturn(c2);
        Mockito.when(cRepo.findOne(c3.getId())).thenReturn(null);

        Mockito.when(cRepo.findAll()).thenReturn(courses);
//        doNothing().when(courseService).deleteCourse(anyLong());
//        Mockito.when(cRepo.findAssignmentsByCourse_Id(c1.getId())).thenReturn(assignments);
//        Mockito.when(cRepo.save(c1)).thenReturn(c1);
//        Mockito.when(cRepo.findOne(c1.getId())).thenReturn(c1);

        Mockito.when(uRepo.findOne(u1.getId())).thenReturn(u1);
        Mockito.when(sRepo.findOne(s1.getId())).thenReturn(s1);
        Mockito.when(uRepo.findAll()).thenReturn(users);
    }

    @Test
    public void enrollUserInACourseForASemester() {
        // user
        courseService.enrollUserInACourseForASemester(String.valueOf(u1.getId()), String.valueOf(c1.getId()));
        List<Course> u1Courses =
                courseService.findCourseByUserIdAndSemId(String.valueOf(u1.getId()), String.valueOf(s1.getId()));
        assertNotNull(u1Courses);
    }
    
    @Test
    public void enrollUserInACourseForASemesterNotNull() {
        User user = new User();
        user.setCourses(courses);
        user.setUserType(Role.PROFESSOR);
        user.setEmail("adadfd");
        Course course = new Course();
        List<User> users = new ArrayList<>();
        users.add(user);
        course.setUsers(users);
        Mockito.when(uRepo.findOne(Mockito.anyLong())).thenReturn(user);
        Mockito.when(cRepo.findOne(Mockito.anyLong())).thenReturn(course);
        Mockito.when(cRepo.save(Mockito.any(Course.class))).thenReturn(course);
        assertEquals(course, courseService.enrollUserInACourseForASemester("1","1"));
    }

    @Test
    public void addCourseToASem() {
        Mockito.when(sRepo.findOne(s1.getId())).thenReturn(s1);
        List<Semester> sems = new ArrayList<>();
        sems.add(s1);
        c1.setSemesters(sems);
        Mockito.when(cRepo.findOne(c1.getId())).thenReturn(c1);

        courseService.addCourseToASem(String.valueOf(s1.getId()), String.valueOf(c1.getId()));
        assertNotNull(courseService.findById(c1.getId()));
    }
    @Test
    public void addCourseToASemEmpty() {
        Mockito.when(sRepo.findOne(s1.getId())).thenReturn(s1);
        List<Semester> sems = new ArrayList<>();
        sems.add(s1);
        c1.setSemesters(null);
        Mockito.when(cRepo.findOne(c1.getId())).thenReturn(c1);

        courseService.addCourseToASem(String.valueOf(s1.getId()), String.valueOf(c1.getId()));
        assertNotNull(courseService.findById(c1.getId()));
    }

    @Test
    public void createCourse() {
        assertEquals(c1, courseService.createCourse(c1));
    }

    @Test
    public void findCoursesBySemId() {
    	Mockito.when(cRepo.findBySemesters_Id(Mockito.anyLong())).thenReturn(courses);
        assertEquals(courseService.findCoursesBySemId("1"), courses);
    }

    @Test
    public void findAllCourses() {
        assert(courseService.findAllCourses().equals(courses));
    }

    @Test
    public void findUnselectedCoursesBySemId() {
    	User user = new User();
    	user.setId(1);
    	List<User> users = new ArrayList<>();
    	users.add(user);
    	c1.setUsers(users);
    	List<Course> c = new ArrayList<>();
    	c.add(c1);
    	c.add(c2);
        Mockito.when(cRepo.findBySemesters_Id(Mockito.anyLong())).thenReturn(c);

        List<Course> unselectedCourseU1S1 = courseService.findUnselectedCoursesBySemId(
                String.valueOf("1"), String.valueOf(s1.getId()));


        List<Course> res = new ArrayList<>();
        res.add(c1);
    }

    @Test
    public void updateCourse() {
        courseService.updateCourse(c2);
        assertEquals(c2, courseService.findById(c2.getId()));
    }

    @Test
    public void deleteCourse() {
        courseService.deleteCourse(c3.getId());
        assertNull(courseService.findById(c3.getId()));
    }

    @Test
    public void createCourseByAProf() {
        // userRepo.findOne()
        // semesterRepo.findOne()

        courseService.createCourseByAProf(String.valueOf(u1.getId()), String.valueOf(s1.getId()), c1);
    }

    @Test
    public void findById() {
        assertEquals(c1, courseService.findById(c1.getId()));
    }

    @Test
    public void findCourseByUserIdAndSemId() {
    	User user = new User();
    	user.setId(1);
    	List<User> users = new ArrayList<>();
    	users.add(user);
    	c1.setUsers(users);
    	List<Course> c = new ArrayList<>();
    	c.add(c1);
    	c.add(c2);
    	Mockito.when(cRepo.findBySemesters_Id(Mockito.anyLong())).thenReturn(c);
    	Mockito.when(cRepo.findByUsers_Id(Mockito.anyLong())).thenReturn(c);
    	String cq =Constants.BUCKET_NAME;
    	assertFalse(courseService.findCourseByUserIdAndSemId("1","1").isEmpty());
    }
}