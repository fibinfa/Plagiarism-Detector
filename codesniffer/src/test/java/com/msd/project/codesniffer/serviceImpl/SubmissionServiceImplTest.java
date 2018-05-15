package com.msd.project.codesniffer.serviceImpl;
/**
   @author fibin
   @version 1.0
   @since 15-Apr-2018
*/

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.msd.project.codesniffer.model.Assignment;
import com.msd.project.codesniffer.model.Submission;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.repository.AssignmentRepository;
import com.msd.project.codesniffer.repository.SubmissionRepository;
import com.msd.project.codesniffer.repository.UserRepository;
import com.msd.project.codesniffer.service.SubmissionService;

@RunWith(SpringRunner.class)
public class SubmissionServiceImplTest {
	@TestConfiguration
	static class SubmissionServiceImplTestConfiguration {
		@Bean
		public SubmissionService submissionService() {
			return new SubmissionServiceImpl();
		}
	}
	
	@Autowired
	SubmissionService subService;
	
	@MockBean
	SubmissionRepository mockSubRepo;
	
	@MockBean
	AssignmentRepository mockAsgmtRepo;
	
	@MockBean
	UserRepository mockUserRepo;
	

	/**
	 * Test method for {@link com.msd.project.codesniffer.serviceImpl.SubmissionServiceImpl#findAllSubmissionsByAsgmtId(java.lang.String)}.
	 */
	@Test
	public void testFindAllSubmissionsByAsgmtId() {
		Assignment mockAsgmt = new Assignment();
		mockAsgmt.setName("HW1");
		mockAsgmt.setLanguage("java17");
		List<Submission> subs = new ArrayList<>();
		Submission sub = new Submission();
		sub.setS3Link("asdasd");
		sub.setGitLink("aadd");
		subs.add(sub);
		Mockito.when(mockSubRepo.findByAssignment_Id(Mockito.anyLong())).thenReturn(subs);
		assertEquals(subs.size(), subService.findAllSubmissionsByAsgmtId("1").size());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.serviceImpl.SubmissionServiceImpl#createSubmission(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCreateSubmission() {
		Submission sub = new Submission();
		sub.setS3Link("asdasd");
		sub.setGitLink("aadd");
		User user = new User();
		Assignment asgmt = new Assignment();
		Mockito.when(mockUserRepo.findOne(Mockito.anyLong())).thenReturn(user);
		Mockito.when(mockAsgmtRepo.findOne(Mockito.anyLong())).thenReturn(asgmt);
		Mockito.when(mockSubRepo.save(Mockito.any(Submission.class))).thenReturn(sub);
		assertEquals("asdasd", subService.createSubmission("1", "2").getS3Link());
		
	}

	
	/**
	 * Test method for {@link com.msd.project.codesniffer.serviceImpl.SubmissionServiceImpl#createSubmission(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCreateSubmissionMultipleAsgmts() {
		Submission sub = new Submission();
		sub.setS3Link("asdasd");
		sub.setGitLink("aadd");
		User user = new User();
		List<Submission> subs = new ArrayList<>();
		subs.add(sub);
		user.setSubmissions(subs);
		Assignment asgmt = new Assignment();
		asgmt.setSubmissions(subs);
		Mockito.when(mockUserRepo.findOne(Mockito.anyLong())).thenReturn(user);
		Mockito.when(mockAsgmtRepo.findOne(Mockito.anyLong())).thenReturn(asgmt);
		Mockito.when(mockSubRepo.save(Mockito.any(Submission.class))).thenReturn(sub);
		assertEquals("asdasd", subService.createSubmission("1", "2").getS3Link());
		
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.serviceImpl.SubmissionServiceImpl#findById(long)}.
	 */
	@Test
	public void testFindById() {
		Submission sub = new Submission();
		sub.setS3Link("asdasd");
		sub.setGitLink("aadd");
		Mockito.when(mockSubRepo.findOne(Mockito.anyLong())).thenReturn(sub);
		assertEquals("asdasd", subService.findById(1L).getS3Link());
	}

}
