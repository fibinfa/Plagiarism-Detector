package com.msd.project.codesniffer.controller;

import com.google.gson.Gson;
import com.msd.project.codesniffer.CodesnifferApplication;
import com.msd.project.codesniffer.model.Assignment;
import com.msd.project.codesniffer.model.Course;
import com.msd.project.codesniffer.model.Role;
import com.msd.project.codesniffer.model.Semester;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.service.SemesterService;
import com.msd.project.codesniffer.service.UserService;
import com.msd.project.codesniffer.serviceImpl.SemesterServiceImpl;
import com.msd.project.codesniffer.serviceImpl.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SemesterController.class)
@ContextConfiguration(classes=CodesnifferApplication.class)
public class SemesterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SemesterService mockSemService;
    
    private Semester mockSemester;
    private String mockSemesterJson;
    
    @Before
	public void setup() {
		mockSemester = new Semester();
		mockSemester.setName("Fall2016");
		Gson gson = new Gson();
		mockSemesterJson = gson.toJson(mockSemester);
	}

    
    @Test
    public void testFindSemesterByUserIdSuccess() throws Exception {
    	List<Semester> sems = new ArrayList<>();
    	sems.add(mockSemester);
    	Mockito.when(mockSemService.findAllSemestersByUserId(Mockito.anyString())).thenReturn(sems);
        this.mockMvc.perform(get("/api/user/1/semester/findSemestersByUserId"))
                .andExpect(status().isOk());
    }

    @Test
    public void findSemesterByUserIdFail() throws Exception {
    	Mockito.when(mockSemService.findAllSemestersByUserId(Mockito.anyString())).thenReturn(null);
        this.mockMvc.perform(get("/api/user/1/semester/findSemestersByUserId"))
                .andExpect(status().is5xxServerError());
    }
    
    @Test
    public void findSemesterByUserIdFailAnother() throws Exception {
    	List<Semester> sems = new ArrayList<>();
    	Mockito.when(mockSemService.findAllSemestersByUserId(Mockito.anyString())).thenReturn(sems);
        this.mockMvc.perform(get("/api/user/1/semester/findSemestersByUserId"))
                .andExpect(status().is5xxServerError());
    }
    
    @Test
    public void testEnrollUserSuccess() throws Exception {
    	Mockito.when(mockSemService.adduserToASem(Mockito.anyString(), Mockito.anyString())).thenReturn(mockSemester);
        this.mockMvc.perform(put("/api/user/1/semester/1"))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testEnrollUserFail() throws Exception {
    	Mockito.when(mockSemService.adduserToASem(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        this.mockMvc.perform(put("/api/user/1/semester/1"))
                .andExpect(status().is5xxServerError());
    }


    @Test
    public void testAddUserToAllSemesters() throws Exception {
    	List<Semester> sems = new ArrayList<>();
    	sems.add(mockSemester);
    	Mockito.when(mockSemService.adduserToASem(Mockito.anyString(), Mockito.anyString())).thenReturn(mockSemester);
    	Mockito.when(mockSemService.findAllSemesters()).thenReturn(sems);
        this.mockMvc.perform(put("/api/user/1/semester/addUserToAllSemesters"))
                .andExpect(status().isOk());
    }

}
