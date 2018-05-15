package com.msd.project.codesniffer.serviceImpl;

import com.msd.project.codesniffer.model.Assignment;
import com.msd.project.codesniffer.model.Course;
import com.msd.project.codesniffer.model.Report;
import com.msd.project.codesniffer.repository.AssignmentRepository;
import com.msd.project.codesniffer.repository.CourseRepository;
import com.msd.project.codesniffer.repository.ReportRepository;
import com.msd.project.codesniffer.service.AssignmentService;
import org.apache.velocity.util.ArrayListWrapper;
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

@RunWith(SpringRunner.class)
public class AssignmentServiceImplTest {
    @TestConfiguration
    static class AssignmentServiceImplTestConfiguration {
        @Bean
        public AssignmentService assignmentService() {
            return new AssignmentServiceImpl();
        }
    }

    @Autowired
    AssignmentService assignmentService;

    @MockBean
    private CourseRepository cRepo;

    @MockBean
    private AssignmentRepository aRepo;

    @MockBean
    private ReportRepository rRepo;

    private Course c1;
    private Assignment a1, a2, a3;
    private List<Assignment> assignments;

    @Before
    public void setUp() throws Exception {
        c1 = new Course();
        c1.setId(30);
        c1.setName("Phy1");

        a1 = new Assignment();
        a1.setId(42);
        a1.setName("hw1");
        a1.setCourse(c1);

        a2 = new Assignment();
        a2.setId(43);
        a2.setName("hw2");
        a2.setCourse(c1);

        a3 = new Assignment();
        a3.setId(44);
        a3.setName("hw3");
        a3.setCourse(c1);

        assignments = new ArrayList<>(3);
        assignments.add(a1);
        assignments.add(a2);
        assignments.add(a3);

        Mockito.when(aRepo.save(a1)).thenReturn(a1);
        Mockito.when(aRepo.save(a2)).thenReturn(a2);
        Mockito.when(aRepo.save(a3)).thenReturn(a3);

        Mockito.when(aRepo.findOne(a1.getId())).thenReturn(a1);
        Mockito.when(aRepo.findOne(a2.getId())).thenReturn(a2);
        Mockito.when(aRepo.findOne(a3.getId())).thenReturn(a3);

        Mockito.when(aRepo.findAssignmentsByCourse_Id(c1.getId())).thenReturn(assignments);

        Mockito.when(cRepo.save(c1)).thenReturn(c1);
        Mockito.when(cRepo.findOne(c1.getId())).thenReturn(c1);
    }

    @Test
    public void createAsgmtUnderCourse() {
        assertEquals(a1, assignmentService.createAsgmtUnderCourse("30", a1));
    }
    
    @Test
    public void createAsgmtUnderCourseNotEmpty() {
    	c1.setId(31);
    	c1.setAssignments(assignments);
    	Mockito.when(cRepo.findOne(31L)).thenReturn(c1);
    	Mockito.when(aRepo.findOne(Mockito.anyLong())).thenReturn(a1);
        assertEquals(a1, assignmentService.createAsgmtUnderCourse("31", a1));
    }

    @Test
    public void findAllAsgmtsByCourse() {
        assert (assignmentService.findAllAsgmtsByCourse(String.valueOf(c1.getId())).equals(assignments));
    }

    @Test
    public void updateThreshold() {
        // uses report repo
        List<Report> reports = new ArrayList<Report>(2);
        Mockito.when(rRepo.findAll()).thenReturn(reports);
        Mockito.when(aRepo.findOne(Mockito.anyLong())).thenReturn(a1);

        Report r1 = new Report();
        r1.setId(1900);
        r1.setReportLink("asgmt1");
        r1.setSimilarityScore(80.0);
        
        Report r2 = new Report();
        r2.setId(1901);
        r2.setReportLink("link2");
        r2.setSimilarityScore(70.0);

        reports.add(r1);
        reports.add(r2);
        a1.setId(1);
        a1.setThreshold(50);

        assertEquals(a1, assignmentService.updateThreshold(a1));
    }

    @Test
    public void findById() {
        assertEquals(a2, assignmentService.findById(a2.getId()));
    }

    @Test
    public void update() {
        assignmentService.update(a3);
        assertEquals(a3, assignmentService.findById(a3.getId()));
    }
}