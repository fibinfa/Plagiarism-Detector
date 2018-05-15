package com.msd.project.codesniffer.serviceImpl;

import com.msd.project.codesniffer.model.Semester;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.repository.SemesterRepository;
import com.msd.project.codesniffer.repository.UserRepository;
import com.msd.project.codesniffer.service.SemesterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringRunner.class)
public class SemesterServiceImplTest {
    @TestConfiguration
    static class SemesterServiceImplTestConfiguration {
        @Bean
        public SemesterService semesterService() {
            return new SemesterServiceImpl();
        }
    }

    @Autowired
    SemesterService semesterService;

    @MockBean
    SemesterRepository sRepo;

    @MockBean
    UserRepository uRepo;

    Semester s1, s2, s3;
    List<Semester> semesters;

    @Before
    public void setUp() throws Exception {
        s1 = new Semester();
        s1.setId(101);
        s1.setName("2018Fall");

        s2 = new Semester();
        s2.setId(102);
        s2.setName("2018Spring");

        s3 = new Semester();
        s3.setId(103);
        s3.setName("2018Summer");

        semesters = new ArrayList<>(3);
        semesters.add(s1);
        semesters.add(s2);
        semesters.add(s3);

        Mockito.when(sRepo.findOne(s1.getId())).thenReturn(s1);
        Mockito.when(sRepo.findOne(s2.getId())).thenReturn(s2);
        Mockito.when(sRepo.findOne(s3.getId())).thenReturn(null);
//        Mockito.when(sRepo.delete(s3.getId())).thenReturn(null);
//        Mockito.doNothing().when(semesterService).deleteSemester(s3.getId());

        Mockito.when(sRepo.findAll()).thenReturn(semesters);

        Mockito.when(sRepo.save(s1)).thenReturn(s1);
        Mockito.when(sRepo.save(s2)).thenReturn(s2);
        Mockito.when(sRepo.save(s3)).thenReturn(s3);
    }

    @Test
    public void findAllSemesters() {
        assertEquals(semesters, semesterService.findAllSemesters());
    }

    @Test
    public void findAllSemestersByUserId() {
    	Mockito.when(sRepo.findSemesterByUsersId(Mockito.anyLong())).thenReturn(semesters);
    	assertEquals(3, semesterService.findAllSemestersByUserId("1").size());
    }

    @Test
    public void createSemester() {
        assertEquals(s1, semesterService.createSemester(s1));
    }

    @Test
    public void updateSemester() {
        assertEquals(s2, semesterService.updateSemester(s2));
    }

    @Test
    public void deleteSemester() {
        semesterService.deleteSemester(s3.getId());
        assertNull(sRepo.findOne(s3.getId()));
    }

    @Test
    public void adduserToASem() {
    	User user = new User();
    	user.setfName("asjkcbbkja");
    	Mockito.when(uRepo.findOne(Mockito.anyLong())).thenReturn(user);
    	Mockito.when(sRepo.findOne(Mockito.anyLong())).thenReturn(s1);
    	Mockito.when(sRepo.save(Mockito.any(Semester.class))).thenReturn(s1);
    	assertEquals(s1.getId(), semesterService.adduserToASem("1", "1").getId());
    }
    
    @Test
    public void adduserToASemEnrolledSemNotEmpty() {
    	User user = new User();
    	user.setfName("asjkcbbkja");
    	List<Semester> sems = new ArrayList<>();
    	sems.add(s2);
    	user.setSemesters(sems);
    	Mockito.when(uRepo.findOne(Mockito.anyLong())).thenReturn(user);
    	Mockito.when(sRepo.findOne(Mockito.anyLong())).thenReturn(s1);
    	Mockito.when(sRepo.save(Mockito.any(Semester.class))).thenReturn(s1);
    	assertEquals(s1.getId(), semesterService.adduserToASem("1", "1").getId());
    }
    
    @Test
    public void adduserToASemUserNotEmpty() {
    	User user = new User();
    	user.setfName("asjkcbbkja");
    	user.setId(22);
    	List<Semester> sems = new ArrayList<>();
    	sems.add(s2);
    	user.setSemesters(sems);
    	User user1 = new User();
    	user1.setfName("dsa");
    	user1.setId(23);
    	List<User> users = new ArrayList<>();
    	users.add(user1);
    	s1.setUsers(users);
    	Mockito.when(uRepo.findOne(Mockito.anyLong())).thenReturn(user);
    	Mockito.when(sRepo.findOne(Mockito.anyLong())).thenReturn(s1);
    	Mockito.when(sRepo.save(Mockito.any(Semester.class))).thenReturn(s1);
    	assertEquals(s1.getId(), semesterService.adduserToASem("1", "1").getId());
    }
}