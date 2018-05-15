package com.msd.project.codesniffer.controller;
/**
   @author fibin
   @version 1.0
   @since 15-Apr-2018
*/

import static org.junit.Assert.*;
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
import com.msd.project.codesniffer.model.Assignment;
import com.msd.project.codesniffer.model.Role;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.service.AssignmentService;
import com.msd.project.codesniffer.service.UserService;

/**
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AssignmentController.class)
@ContextConfiguration(classes=CodesnifferApplication.class)
public class AssignementControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AssignmentService mockAsgmtService;

	Assignment mockAsgmt;
	String mockAsgmtJson;

	@Before
	public void setup() {
		mockAsgmt = new Assignment();
		mockAsgmt.setName("HW1");
		mockAsgmt.setLanguage("java17");
		Gson gson = new Gson();
		mockAsgmtJson = gson.toJson(mockAsgmt);
	}


	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AssignmentController#findSemesterByUserId(java.lang.String, com.msd.project.codesniffer.model.Assignment)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindSemesterByUserIdStringAssignment() throws Exception {
		Mockito.when(mockAsgmtService.createAsgmtUnderCourse(Mockito.anyString(),Mockito.any(Assignment.class)))
		.thenReturn(mockAsgmt);
		this.mockMvc.perform(post("/api/course/1/createAsgmt")
				.contentType(MediaType.APPLICATION_JSON).content(mockAsgmtJson))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AssignmentController#findSemesterByUserId(java.lang.String, com.msd.project.codesniffer.model.Assignment)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindSemesterByUserIdStringAssignmentFail() throws Exception {
		Mockito.when(mockAsgmtService.createAsgmtUnderCourse(Mockito.anyString(),Mockito.any(Assignment.class)))
		.thenReturn(null);
		this.mockMvc.perform(post("/api/course/1/createAsgmt")
				.contentType(MediaType.APPLICATION_JSON).content(mockAsgmtJson))
		.andExpect(status().is5xxServerError());
	}


	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AssignmentController#findSemesterByUserId(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testfindAllAsgmtsByCourse() throws Exception {
		List<Assignment> asgmts = new ArrayList<>();
		Mockito.when(mockAsgmtService.findAllAsgmtsByCourse(Mockito.anyString()))
		.thenReturn(asgmts);
		this.mockMvc.perform(get("/api/course/1/findAllAsgmtsByCourse"))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AssignmentController#findSemesterByUserId(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testfindAllAsgmtsByCourseFail() throws Exception {
		List<Assignment> asgmts = new ArrayList<>();
		Mockito.when(mockAsgmtService.findAllAsgmtsByCourse(Mockito.anyString()))
		.thenReturn(null);
		this.mockMvc.perform(get("/api/course/1/findAllAsgmtsByCourse"))
		.andExpect(status().is5xxServerError());
	}


	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AssignmentController#updateThreshold(com.msd.project.codesniffer.model.Assignment)}.
	 * @throws Exception 
	 */
	@Test
	public void testUpdateThreshold() throws Exception {
		Mockito.when(mockAsgmtService.updateThreshold(Mockito.any(Assignment.class)))
		.thenReturn(mockAsgmt);
		this.mockMvc.perform(put("/api/assignment/updateThreshold")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mockAsgmtJson))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.AssignmentController#updateThreshold(com.msd.project.codesniffer.model.Assignment)}.
	 * @throws Exception 
	 */
	@Test
	public void testUpdateThresholdFail() throws Exception {
		Mockito.when(mockAsgmtService.updateThreshold(Mockito.any(Assignment.class)))
		.thenReturn(null);
		this.mockMvc.perform(put("/api/assignment/updateThreshold")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mockAsgmtJson))
		.andExpect(status().is5xxServerError());
	}

}
