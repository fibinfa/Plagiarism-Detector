package com.msd.project.codesniffer.controller;
/**
   @author fibin
   @version 1.0
   @since 14-Apr-2018
*/

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.msd.project.codesniffer.CodesnifferApplication;
import com.msd.project.codesniffer.model.Course;
import com.msd.project.codesniffer.service.CourseService;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseController.class)
@ContextConfiguration(classes=CodesnifferApplication.class)
public class CourseControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService mockCourseService;

	Course mockCourse;
	String mockCourseJson;

	@Before
	public void setup() {
		mockCourse = new Course();
		mockCourse.setName("MSD");
		mockCourse.setRecipientEmail("test@test.com");
		Gson gson = new Gson();
		mockCourseJson = gson.toJson(mockCourse);
	}


	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#findCourseByUserIdAndSemId(java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindCourseByUserIdAndSemId() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(mockCourse);
		Mockito.when(mockCourseService.findCourseByUserIdAndSemId(Mockito.anyString(),Mockito.anyString()))
		.thenReturn(courses);
		this.mockMvc.perform(get("/api/user/1/semester/1/findAllEnrolledCourses"))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#findCourseByUserIdAndSemId(java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindCourseByUserIdAndSemIdFail() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(mockCourse);
		Mockito.when(mockCourseService.findCourseByUserIdAndSemId(Mockito.anyString(),Mockito.anyString()))
		.thenReturn(null);
		this.mockMvc.perform(get("/api/user/1/semester/1/findAllEnrolledCourses"))
		.andExpect(status().is5xxServerError());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#findCourseByUserIdAndSemId(java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindCourseByUserIdAndSemIdEmptyList() throws Exception {
		List<Course> courses = new ArrayList<>();
		Mockito.when(mockCourseService.findCourseByUserIdAndSemId(Mockito.anyString(),Mockito.anyString()))
		.thenReturn(courses);
		this.mockMvc.perform(get("/api/user/1/semester/1/findAllEnrolledCourses"))
		.andExpect(status().is5xxServerError());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#findCoursesBySemId(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindCoursesBySemId() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(mockCourse);
		Mockito.when(mockCourseService.findCoursesBySemId(Mockito.anyString()))
		.thenReturn(courses);
		this.mockMvc.perform(get("/api/user/semester/1/findAllCoursesForASemester"))
		.andExpect(status().isOk());
	}
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#findCoursesBySemId(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindCoursesBySemIdFail() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(mockCourse);
		Mockito.when(mockCourseService.findCoursesBySemId(Mockito.anyString()))
		.thenReturn(null);
		this.mockMvc.perform(get("/api/user/semester/1/findAllCoursesForASemester"))
		.andExpect(status().is5xxServerError());
	}
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#findCoursesBySemId(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindCoursesBySemIdEmptyList() throws Exception {
		List<Course> courses = new ArrayList<>();
		Mockito.when(mockCourseService.findCoursesBySemId(Mockito.anyString()))
		.thenReturn(courses);
		this.mockMvc.perform(get("/api/user/semester/1/findAllCoursesForASemester"))
		.andExpect(status().is5xxServerError());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#findUnselectedCoursesBySemId(java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindUnselectedCoursesBySemId() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(mockCourse);
		Mockito.when(mockCourseService.findUnselectedCoursesBySemId(Mockito.anyString(),Mockito.anyString()))
		.thenReturn(courses);
		this.mockMvc.perform(get("/api/user/1/semester/1/findAllUnregisteredCoursesForASemester"))
		.andExpect(status().isOk());
	}
	
	

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#enrollUserInACourse(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testEnrollUserInACourse() throws Exception {
		Mockito.when(mockCourseService.enrollUserInACourseForASemester(Mockito.anyString(),Mockito.anyString()))
		.thenReturn(mockCourse);
		this.mockMvc.perform(put("/api/user/1/semester/1/course/1"))
		.andExpect(status().isOk());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#enrollUserInACourse(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testEnrollUserInACourseFail() throws Exception {
		Mockito.when(mockCourseService.enrollUserInACourseForASemester(Mockito.anyString(),Mockito.anyString()))
		.thenReturn(null);
		this.mockMvc.perform(put("/api/user/1/semester/1/course/1"))
		.andExpect(status().is5xxServerError());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#addCourse(java.lang.String, java.lang.String, com.msd.project.codesniffer.model.Course)}.
	 * @throws Exception 
	 */
	@Test
	public void testAddCourse() throws Exception {
		Mockito.when(mockCourseService.createCourseByAProf(
				Mockito.anyString(), Mockito.anyString(), Mockito.any(Course.class)))
		.thenReturn(mockCourse);
		this.mockMvc.perform(post("/api/user/1/semester/1/addcourse").contentType(MediaType.APPLICATION_JSON)
				.content(mockCourseJson)).andExpect(status().isOk());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#addUserToAllCourses(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testAddUserToAllCourses() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(mockCourse);
		Mockito.when(mockCourseService.findAllCourses()).thenReturn(courses);
		Mockito.when(mockCourseService.enrollUserInACourseForASemester(Mockito.anyString(), Mockito.anyString()))
		.thenReturn(mockCourse);
		this.mockMvc.perform(put("/api/user/1/course/addUserToAllCourses"))
		.andExpect(status().isOk());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#addCourseToASem(java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testAddCourseToASem() throws Exception {
		Mockito.when(mockCourseService.addCourseToASem(Mockito.anyString(), Mockito.anyString()))
		.thenReturn(mockCourse);
		this.mockMvc.perform(put("/api/semster/1/course/1")).andExpect(status().isOk());
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#addCourseToASem(java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testAddCourseToASemFail() throws Exception {
		Mockito.when(mockCourseService.addCourseToASem(Mockito.anyString(), Mockito.anyString()))
		.thenReturn(null);
		this.mockMvc.perform(put("/api/semster/1/course/1")).andExpect(status().is5xxServerError());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#createCourse(com.msd.project.codesniffer.model.Course)}.
	 * @throws Exception 
	 */
	@Test
	public void testCreateCourse() throws Exception {
		Mockito.when(mockCourseService.createCourse(Mockito.any(Course.class))).thenReturn(mockCourse);
		this.mockMvc.perform(post("/api/course").contentType(MediaType.APPLICATION_JSON).content(mockCourseJson))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.CourseController#createCourse(com.msd.project.codesniffer.model.Course)}.
	 * @throws Exception 
	 */
	@Test
	public void testCreateCourseFail() throws Exception {
		Mockito.when(mockCourseService.createCourse(Mockito.any(Course.class))).thenReturn(null);
		this.mockMvc.perform(post("/api/course").contentType(MediaType.APPLICATION_JSON).content(mockCourseJson))
		.andExpect(status().is5xxServerError());
	}

}
