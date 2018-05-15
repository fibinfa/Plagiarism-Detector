package com.msd.project.codesniffer.controller;
/**
   @author fibin
   @version 1.0
   @since 15-Apr-2018
*/

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

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
import com.msd.project.codesniffer.serviceImpl.StorageServiceImpl;


@RunWith(SpringRunner.class)
@WebMvcTest(FileUploadController.class)
@ContextConfiguration(classes=CodesnifferApplication.class)
public class FileUploadControllerTest {
	
	@MockBean
	private SubmissionService mockSubService;
	
	@MockBean
	private StorageServiceImpl mockStorageService;
	
	@Autowired
	private MockMvc mockMvc;

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.FileUploadController#uploadFileMulti(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testCloneGit() throws Exception {
		Submission sub = new Submission();
		Mockito.when(mockSubService.createSubmission(Mockito.anyString(),Mockito.anyString())).thenReturn(sub);
		Mockito.when(mockStorageService.cloneGit(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
		.thenReturn(true);
		this.mockMvc.perform(post("/api/user/1/sem/1/course/"
				+ "1/assignment/1/gitclone").param("gitRepo", "abc"))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.FileUploadController#uploadFileMulti(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testCloneGitFail() throws Exception {
		Submission sub = new Submission();
		Mockito.when(mockSubService.createSubmission(Mockito.anyString(),Mockito.anyString())).thenReturn(sub);
		Mockito.when(mockStorageService.cloneGit(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
		.thenReturn(false);
		this.mockMvc.perform(post("/api/user/1/sem/1/course/"
				+ "1/assignment/1/gitclone").param("gitRepo", "abc"))
		.andExpect(status().is5xxServerError());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.FileUploadController#comapreFiles(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testComapreFiles() throws Exception {
		doNothing().when(mockStorageService).compare(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());
		this.mockMvc.perform(post("/api/user/1/sem/1/course/1/assignment/1/submission/1/compare"))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.FileUploadController#comapreFiles(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testComapreFilesFail() throws Exception {
		doThrow(Exception.class).when(mockStorageService).compare(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString());
		this.mockMvc.perform(post("/api/user/1/sem/1/course/1/assignment/1/submission/1/compare"))
		.andExpect(status().is5xxServerError());
	}
	


}
