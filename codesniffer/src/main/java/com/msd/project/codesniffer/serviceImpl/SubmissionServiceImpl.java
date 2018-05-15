package com.msd.project.codesniffer.serviceImpl;
/**
   @author fibin
   @version 1.0
   @since 26-Mar-2018
*/

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msd.project.codesniffer.model.Assignment;
import com.msd.project.codesniffer.model.Submission;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.repository.AssignmentRepository;
import com.msd.project.codesniffer.repository.SubmissionRepository;
import com.msd.project.codesniffer.repository.UserRepository;
import com.msd.project.codesniffer.service.SubmissionService;

/**
 * @author wuhao
 *
 */
@Service
public class SubmissionServiceImpl implements SubmissionService {

	@Autowired
	SubmissionRepository subRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	AssignmentRepository asgmtRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.msd.project.codesniffer.service.SubmissionService#findAllSubsByAsgmtId(
	 * java.lang.String)
	 */
	@Override
	public List<Submission> findAllSubmissionsByAsgmtId(String asgmtId) {
		return subRepo.findByAssignment_Id(Long.parseLong(asgmtId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.msd.project.codesniffer.service.SubmissionService#createSubmission(com.
	 * msd.project.codesniffer.model.Submission)
	 */
	@Override
	public Submission createSubmission(String userId, String asgmtId) {
		Submission submission = new Submission();
		User user = userRepo.findOne(Long.parseLong(userId));
		Assignment asgmt = asgmtRepo.findOne(Long.parseLong(asgmtId));
		submission.setUser(user);
		submission.setAssignment(asgmt);
		if (user.getSubmissions() != null && !user.getSubmissions().isEmpty()) {
			user.getSubmissions().add(submission);
		} else {
			List<Submission> list = new ArrayList<>();
			list.add(submission);
			user.setSubmissions(list);
		}
		if (asgmt.getSubmissions() != null && !asgmt.getSubmissions().isEmpty()) {
			asgmt.getSubmissions().add(submission);
		} else {
			List<Submission> list = new ArrayList<>();
			list.add(submission);
			asgmt.setSubmissions(list);
		}
		Calendar cal = Calendar.getInstance();
		java.util.Date currentDate = cal.getTime();
		submission.setUploadDate(new Date(currentDate.getTime()));
		return subRepo.save(submission);
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.SubmissionService#findById(long)
	 */
	@Override
	public Submission findById(long subId) {
		return subRepo.findOne(subId);
	}

}
