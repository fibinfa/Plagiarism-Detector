package com.msd.project.codesniffer.controller;
/**
   @author fibin
   @version 1.0
   @since 15-Apr-2018
*/

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.msd.project.codesniffer.CodesnifferApplication;
import com.msd.project.codesniffer.model.Submission;
import com.msd.project.codesniffer.service.SubmissionService;

@RunWith(SpringRunner.class)
@WebMvcTest(SubmissionController.class)
@ContextConfiguration(classes=CodesnifferApplication.class)
public class SubmissionControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SubmissionService mockSubService;


	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.SubmissionController#findSubmissionsByAsgmtId(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindSubmissionsByAsgmtId() throws Exception {
		List<Submission> subs = new ArrayList<>();
		Submission mockSub = new Submission();
		mockSub.setId(1);
		mockSub.setGitLink("abc");
		subs.add(mockSub);
		Mockito.when(mockSubService.findAllSubmissionsByAsgmtId(Mockito.anyString()))
		.thenReturn(subs);
		this.mockMvc.perform(get("/api/assignment/1/findAllSubmissions"))
		.andExpect(status().isOk());
		
	}
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.SubmissionController#findSubmissionsByAsgmtId(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindSubmissionsByAsgmtIdFail() throws Exception {
		List<Submission> subs = new ArrayList<>();
		Submission mockSub = new Submission();
		mockSub.setId(1);
		mockSub.setGitLink("abc");
		subs.add(mockSub);
		Mockito.when(mockSubService.findAllSubmissionsByAsgmtId(Mockito.anyString()))
		.thenReturn(null);
		this.mockMvc.perform(get("/api/assignment/1/findAllSubmissions"))
		.andExpect(status().is5xxServerError());
		
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.SubmissionController#findSubmissionsByAsgmtId(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindSubmissionsByAsgmtIdFailEmpty() throws Exception {
		List<Submission> subs = new ArrayList<>();
		Submission mockSub = new Submission();
		mockSub.setId(1);
		mockSub.setGitLink("abc");
		Mockito.when(mockSubService.findAllSubmissionsByAsgmtId(Mockito.anyString()))
		.thenReturn(subs);
		this.mockMvc.perform(get("/api/assignment/1/findAllSubmissions"))
		.andExpect(status().is5xxServerError());
		
	}

}
