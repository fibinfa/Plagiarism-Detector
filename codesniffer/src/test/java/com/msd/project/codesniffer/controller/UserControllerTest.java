package com.msd.project.codesniffer.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.msd.project.codesniffer.CodesnifferApplication;
import com.msd.project.codesniffer.model.Role;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.service.UserService;

/**
 * @author fibin
 * @version 1.0
 * @since 26-Mar-2018 Test class for testing user controller
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=CodesnifferApplication.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	User mockUser;
	String mockUserJson;

	@Before
	public void setup() {
		mockUser = new User();
		mockUser.setfName("test");
		mockUser.setlName("test");
		mockUser.setUsername("test");
		mockUser.setPassword("test");
		mockUser.setEmail("test@test.com");
		mockUser.setUserType(Role.ADMIN);
		mockUser.setId(1L);
		Gson gson = new Gson();
		mockUserJson = gson.toJson(mockUser);
	}

	/**
	 * test success case for create user
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateUserSuccess() throws Exception {
		Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(mockUser);
		this.mockMvc.perform(post("/api/user/createUser").contentType(MediaType.APPLICATION_JSON).content(mockUserJson))
				.andExpect(status().is2xxSuccessful());
	}

	/**
	 * @throws Exception
	 *             test case for testing the failure cases of create user
	 */
	@Test
	public void testCreateUserFail() throws Exception {
		Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(null);
		this.mockMvc.perform(post("/api/user/createUser").contentType(MediaType.APPLICATION_JSON).content(mockUserJson))
				.andExpect(status().is5xxServerError());
	}

	/**
	 * testing the success case of login
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoginSucces() throws Exception {
		Mockito.when(userService.findUserByCredentials(Mockito.anyString(), Mockito.anyString())).thenReturn(mockUser);
		this.mockMvc.perform(get("/api/user/findUserByCredentials").param("username", mockUser.getUsername())
				.param("password", mockUser.getPassword())).andExpect(status().isOk());
	}

	/**
	 * testing the error case of login
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoginFail() throws Exception {
		Mockito.when(userService.findUserByCredentials(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		this.mockMvc.perform(get("/api/user/findUserByCredentials").param("username", mockUser.getUsername())
				.param("password", mockUser.getPassword())).andExpect(status().is5xxServerError());
	}

	/**
	 * testing the success case of getallusers
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetAllUsers() throws Exception {
		List<User> users = new ArrayList<>();
		users.add(mockUser);
		Mockito.when(userService.findAllUser()).thenReturn(users);
		this.mockMvc.perform(get("/api/user/all")).andExpect(status().isOk());
	}

	/**
	 * testing the success case of findUserByUsername
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindUserByUsernameSuccess() throws Exception {
		Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(mockUser);
		this.mockMvc.perform(get("/api/user/findUserByUsername").param("username", mockUser.getUsername()))
				.andExpect(status().isConflict());
	}

	/**
	 * testing the error case of findUserByUsername
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindUserByUsernameFail() throws Exception {
		Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(null);
		this.mockMvc.perform(get("/api/user/findUserByUsername").param("username", mockUser.getUsername()))
				.andExpect(status().isOk());
	}

	/**
	 * testing the success case of findUserById
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindUserByIdSuccess() throws Exception {
		Mockito.when(userService.findById(Mockito.anyString())).thenReturn(mockUser);
		this.mockMvc.perform(get("/api/user/findUserById").param("userId", String.valueOf(mockUser.getId())))
				.andExpect(status().isOk());
	}

	/**
	 * testing the error case of findUserByUsername
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindUserByIdFail() throws Exception {
		Mockito.when(userService.findById(Mockito.anyString())).thenReturn(null);
		this.mockMvc.perform(get("/api/user/findUserById").param("userId", String.valueOf(mockUser.getId())))
				.andExpect(status().is5xxServerError());
	}

	/**
	 * testing success case of update user
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateUserSuccess() throws Exception {
		Mockito.when(userService.updateUser(Mockito.any(User.class))).thenReturn(mockUser);
		this.mockMvc.perform(put("/api/user/updateUser").contentType(MediaType.APPLICATION_JSON).content(mockUserJson))
				.andExpect(status().isOk());
	}

	/**
	 * testing failure case of update user
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateUserFail() throws Exception {
		Mockito.when(userService.updateUser(Mockito.any(User.class))).thenReturn(null);
		this.mockMvc.perform(put("/api/user/updateUser").contentType(MediaType.APPLICATION_JSON).content(mockUserJson))
				.andExpect(status().is5xxServerError());
	}

	/**
	 * testing success case of delete user
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteUser() throws Exception {
		doNothing().when(userService).deleteUser(mockUser.getId());
		this.mockMvc.perform(delete("/api/user/deleteUser/1"))
		.andExpect(status().isOk());
	}

	/**
	 * testing success case of isAdmin
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsAdmin() throws Exception {
		this.mockMvc.perform(get("/api/auth/isAdmin/1").sessionAttr(String.valueOf(1), mockUser))
				.andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * testing failure case of isAdmin
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsAdminFail() throws Exception {
		this.mockMvc.perform(get("/api/auth/isAdmin/1").sessionAttr(String.valueOf(2), mockUser))
				.andExpect(status().isForbidden());
	}
	
	/**
	 * testing success case of isAdmin
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsFaculty() throws Exception {
		mockUser.setUserType(Role.PROFESSOR);
		this.mockMvc.perform(get("/api/auth/isFaculty/1").sessionAttr(String.valueOf(1), mockUser))
				.andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * testing failure case of isFaculty
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsFacultyFail() throws Exception {
		this.mockMvc.perform(get("/api/auth/isFaculty/1").sessionAttr(String.valueOf(1), mockUser))
				.andExpect(status().isForbidden());
	}
	
	/**
	 * testing success case of isAdmin
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsLoggedInSuccess() throws Exception {
		mockUser.setUserType(Role.PROFESSOR);
		this.mockMvc.perform(get("/api/auth/loggedIn/1").sessionAttr(String.valueOf(1), mockUser))
				.andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * testing failure case of isFaculty
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsLoggedInFail() throws Exception {
		this.mockMvc.perform(get("/api/auth/loggedIn/1").sessionAttr(String.valueOf(2), mockUser))
				.andExpect(status().isConflict());
	}

	/**
	 * testing success case of isAdmin
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoggedInSuccess() throws Exception {
		mockUser.setUserType(Role.PROFESSOR);
		Mockito.when(userService.findUserByCredentials(Mockito.anyString(), Mockito.anyString())).thenReturn(mockUser);
		this.mockMvc.perform(post("/api/auth/logIn").
				contentType(MediaType.APPLICATION_JSON)
				.content(mockUserJson))
				.andExpect(status().is2xxSuccessful());
	}
	
	/**
	 * testing failure case of isFaculty
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLoggedInFail() throws Exception {
		Mockito.when(userService.findUserByCredentials(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		this.mockMvc.perform(post("/api/auth/logIn").
				contentType(MediaType.APPLICATION_JSON)
				.content(mockUserJson))
				.andExpect(status().is5xxServerError());
	}
	
	
	/**
	 * testing success case of isAdmin
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogoutSuccess() throws Exception {
		this.mockMvc.perform(get("/api/auth/logOut/1").sessionAttr(String.valueOf(1), mockUser))
				.andExpect(status().is2xxSuccessful());
	}
	
	
	
	
}
