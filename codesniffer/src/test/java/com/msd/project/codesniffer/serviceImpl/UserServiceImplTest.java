package com.msd.project.codesniffer.serviceImpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.msd.project.codesniffer.model.Role;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.repository.UserRepository;
import com.msd.project.codesniffer.service.UserService;


@RunWith(SpringRunner.class)
public class UserServiceImplTest {
	@TestConfiguration
	static class UserServiceImplTestConfiguration {
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
	}
    @Autowired
    UserService userService;

    @MockBean
    private UserRepository userRepo;

    private User mockUser, mockUser1, mockUser2;

    @Before
    public void setUp() throws Exception {
//        userService = mock(UserService.class);
        mockUser = new User();
        mockUser.setfName("test");
        mockUser.setlName("test");
        mockUser.setUsername("test");
        mockUser.setPassword("test");
        mockUser.setEmail("test@test.com");
        mockUser.setUserType(Role.PROFESSOR);
        mockUser.setId(1000);

        mockUser1 = new User();
        mockUser1.setfName("test1");
        mockUser1.setlName("test1");
        mockUser1.setUsername("test1");
        mockUser1.setPassword("test1");
        mockUser1.setEmail("test1@test.com");
        mockUser1.setUserType(Role.STUDENT);
        mockUser1.setId(1);

        mockUser2 = new User();
        mockUser2.setfName("test2");
        mockUser2.setlName("test2");
        mockUser2.setUsername("test2");
        mockUser2.setPassword("test2");
        mockUser2.setEmail("test2@test.com");
        mockUser2.setUserType(Role.ADMIN);
        mockUser2.setId(2);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findUserByCredentials() {
//        Mockito.when(userRepo.findByUsernameAndPassword(Mockito.anyString(),
//                Mockito.anyString())).thenReturn(mockUser);
        Mockito.when(userRepo.findByUsernameAndPassword(mockUser.getUsername(), mockUser.getPassword()))
                .thenReturn(mockUser);
        assertEquals(mockUser, userService.findUserByCredentials("test", "test"));
    }

    @Test
    public void createUser() {
        Mockito.when(userRepo.save(mockUser)).thenReturn(mockUser);
        assertEquals(mockUser, userService.createUser(mockUser));
    }

    @Test
    public void updateUser() {
        User updatedMockUser = new User();
        updatedMockUser.setfName("i'm new");
        updatedMockUser.setlName("joe");
        updatedMockUser.setUsername("test");
        updatedMockUser.setPassword("newName");
        updatedMockUser.setEmail("test@test.com");
        updatedMockUser.setUserType(Role.STUDENT);
        updatedMockUser.setId(1000);

        Mockito.when(userRepo.save(mockUser)).thenReturn(mockUser);
        Mockito.when(userRepo.save(updatedMockUser)).thenReturn(updatedMockUser);

        assertEquals(updatedMockUser,  userService.updateUser(updatedMockUser));
    }

    @Test
    public void deleteUser() {
        Mockito.when(userRepo.save(mockUser)).thenReturn(mockUser);
        userService.deleteUser(mockUser.getId());
        assertEquals(null, userService.findByUsername(mockUser.getUsername()));
    }

    @Test
    public void findByUsername() {
        Mockito.when(userRepo.findByUsername(mockUser.getUsername())).thenReturn(mockUser);

        assertEquals(mockUser, userService.findByUsername("test"));
    }

    @Test
    public void findById() {
        Mockito.when(userRepo.findOne(mockUser.getId())).thenReturn(mockUser);
        assertEquals(mockUser, userService.findById("1000"));
    }

    @Test
    public void findAllUser() {
        List<User> users = new ArrayList<>(3);
        users.add(mockUser);
        users.add(mockUser1);
        users.add(mockUser2);

        Mockito.when(userRepo.findAll()).thenReturn(users);
        assert(userService.findAllUser().equals(users));
    }
}