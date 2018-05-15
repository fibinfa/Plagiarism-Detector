package com.msd.project.codesniffer.serviceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.msd.project.codesniffer.model.Assignment;
import com.msd.project.codesniffer.model.Report;
import com.msd.project.codesniffer.model.Submission;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.repository.AssignmentRepository;
import com.msd.project.codesniffer.repository.ReportRepository;
import com.msd.project.codesniffer.repository.SubmissionRepository;
import com.msd.project.codesniffer.repository.UserRepository;
import com.msd.project.codesniffer.service.ReportService;

@RunWith(SpringRunner.class)
public class ReportServiceImplTest {
    @TestConfiguration
    static class ReportServiceImplTestConfiguration {
        @Bean
        public ReportService reportService() {
            return new ReportServiceImpl();
        }
    }

    @Autowired
    ReportService reportService;

    @MockBean
    private ReportRepository rRepo;
    
    @MockBean
	SubmissionRepository subRepo;


	@MockBean
	UserRepository userRepo;

	@MockBean
	AssignmentRepository asgmtRepo;

	@MockBean
	private StorageServiceImpl storageService;

    private Report r1, r2;
    private List<Report> reports;

    @Before
    public void setUp() throws Exception {
    	reports = new ArrayList<>();
        r1 = new Report();
        r1.setId(42);
        r1.setReportLink("www.github.com/1");

        r2 = new Report();
        r2.setId(43);
        r2.setReportLink("www.github.com/2");
        
        reports.add(r1);
        reports.add(r2);
        
        Mockito.when(rRepo.findBySubmissions_Id(Mockito.anyLong())).thenReturn(reports);
    }

    @Test
    public void findReportsByAsgmtId() {
    	Submission s1 = new Submission();
    	s1.setId(123);
    	List<Submission> subs = new ArrayList<>();
    	subs.add(s1);
    	Mockito.when(subRepo.findByAssignment_Id(Mockito.anyLong())).thenReturn(subs);
    	Mockito.when(rRepo.findBySubmissions_Id(Mockito.anyLong())).thenReturn(reports);
    	assertFalse(reportService.findReportsByAsgmtId("1").isEmpty());
    }

   
    @Test
    public void shareReport() throws Exception {
    	r1.setSimilarityScore(98);
    	r1.setReportLink("user37_sem3_course2_asgmthw111_sub61_user36_sem3_course2_asgmthw111_sub57");
    	Assignment asgmt = new Assignment();
    	asgmt.setName("adsas");
    	Mockito.when(asgmtRepo.findOne(Mockito.anyLong())).thenReturn(asgmt);
    	User user = new User();
    	user.setEmail("adas");
    	Mockito.when(userRepo.findOne(Mockito.anyLong())).thenReturn(user);
    	Mockito.when(rRepo.findOne(Mockito.anyLong())).thenReturn(r1);
    	doNothing().when(storageService).sendEmail(Mockito.anyString(), 
    			Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble(), Mockito.anyString(), Mockito.anyString());
    	reportService.shareReport(1, 1);
    }
    
    @Test
    public void createReport() {
    	Mockito.when(rRepo.save(Mockito.any(Report.class))).thenReturn(r1);
    	assertEquals(r1, reportService.createReport(r1));
    }
}