package com.msd.project.codesniffer.controller;
/**
   @author fibin
   @version 1.0
   @since 14-Apr-2018
*/

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.msd.project.codesniffer.model.Semester;
import com.msd.project.codesniffer.service.CourseService;
import com.msd.project.codesniffer.service.SemesterService;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes=CodesnifferApplication.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SemesterService mockSemService;
	
	@MockBean
	private CourseService courseService;
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AdminController#createSemester(com.msd.project.codesniffer.model.Semester)}.
	 * @throws Exception 
	 */
	@Test
	public void testCreateSemester() throws Exception {
		Semester sem = new Semester();
		sem.setName("Fall16");
		Gson gson = new Gson();
		String semJson = gson.toJson(sem);
		Mockito.when(mockSemService.createSemester(Mockito.any(Semester.class))).thenReturn(sem);
		this.mockMvc.perform(post("/api/semester").contentType(MediaType.APPLICATION_JSON).content(semJson))
		.andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AdminController#createSemester(com.msd.project.codesniffer.model.Semester)}.
	 * @throws Exception 
	 */
	@Test
	public void testCreateSemesterFail() throws Exception {
		Semester sem = new Semester();
		sem.setName("Fall16");
		Gson gson = new Gson();
		String semJson = gson.toJson(sem);
		Mockito.when(mockSemService.createSemester(Mockito.any(Semester.class))).thenReturn(null);
		this.mockMvc.perform(post("/api/semester").contentType(MediaType.APPLICATION_JSON).content(semJson))
		.andExpect(status().is5xxServerError());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AdminController#updateSemester(com.msd.project.codesniffer.model.Semester)}.
	 * @throws Exception 
	 */
	@Test
	public void testUpdateSemester() throws Exception {
		Semester sem = new Semester();
		sem.setName("Fall16");
		Gson gson = new Gson();
		String semJson = gson.toJson(sem);
		Mockito.when(mockSemService.updateSemester(Mockito.any(Semester.class))).thenReturn(sem);
		this.mockMvc.perform(put("/api/semester").contentType(MediaType.APPLICATION_JSON).content(semJson))
		.andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AdminController#updateSemester(com.msd.project.codesniffer.model.Semester)}.
	 * @throws Exception 
	 */
	@Test
	public void testUpdateSemesterFail() throws Exception {
		Semester sem = new Semester();
		sem.setName("Fall16");
		Gson gson = new Gson();
		String semJson = gson.toJson(sem);
		Mockito.when(mockSemService.updateSemester(Mockito.any(Semester.class))).thenReturn(null);
		this.mockMvc.perform(put("/api/semester").contentType(MediaType.APPLICATION_JSON).content(semJson))
		.andExpect(status().is5xxServerError());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AdminController#deleteSemester(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testDeleteSemester() throws Exception {
		doNothing().when(mockSemService).deleteSemester(Mockito.anyLong());
		this.mockMvc.perform(delete("/api/semester/1")).andExpect(status().isOk());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AdminController#getAllSemesters()}.
	 * @throws Exception 
	 */
	@Test
	public void testGetAllSemesters() throws Exception {
		List<Semester> sems = new ArrayList<>();
		Semester sem = new Semester();
		sem.setName("Fall16");
		sems.add(sem);
		Mockito.when(mockSemService.findAllSemesters()).thenReturn(sems);
		this.mockMvc.perform(get("/api/semester/all")).andExpect(status().isOk());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AdminController#updateSemester(com.msd.project.codesniffer.model.Semester)}.
	 * @throws Exception 
	 */
	@Test
	public void testUpdateCourse() throws Exception {
		Course course = new Course();
		course.setName("MSD");
		Gson gson = new Gson();
		String courseJson = gson.toJson(course);
		Mockito.when(courseService.updateCourse(Mockito.any(Course.class))).thenReturn(course);
		this.mockMvc.perform(put("/api/course").contentType(MediaType.APPLICATION_JSON).content(courseJson))
		.andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AdminController#updateSemester(com.msd.project.codesniffer.model.Semester)}.
	 * @throws Exception 
	 */
	@Test
	public void testUpdateCourseFail() throws Exception {
		Course course = new Course();
		course.setName("MSD");
		Gson gson = new Gson();
		String courseJson = gson.toJson(course);
		Mockito.when(courseService.updateCourse(Mockito.any(Course.class))).thenReturn(null);
		this.mockMvc.perform(put("/api/course").contentType(MediaType.APPLICATION_JSON).content(courseJson))
		.andExpect(status().is5xxServerError());
	}
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AdminController#deleteCourse(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testDeleteCourse() throws Exception {
		doNothing().when(courseService).deleteCourse(Mockito.anyLong());
		this.mockMvc.perform(delete("/api/course/1")).andExpect(status().isOk());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AdminController#findAllCourses()}.
	 * @throws Exception 
	 */
	@Test
	public void testFindAllCourses() throws Exception {
		List<Course> courses = new ArrayList<>();
		Course course = new Course();
		course.setName("MSD");
		courses.add(course);
		Mockito.when(courseService.findAllCourses()).thenReturn(courses);
		this.mockMvc.perform(get("/api/course/all"));
	}

}
