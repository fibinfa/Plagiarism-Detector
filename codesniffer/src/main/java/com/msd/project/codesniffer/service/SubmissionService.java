package com.msd.project.codesniffer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msd.project.codesniffer.model.Submission;

/**
   @author fibin
   @version 1.0
   @since 26-Mar-2018
*/
@Service
public interface SubmissionService {
	/**
	 * Find all submissions related to the given assignment ID
	 * @param asgmtId
	 * @return
	 */
	List<Submission> findAllSubmissionsByAsgmtId(String asgmtId);

	/**
	 * Create a new submission by given user with userId and assignmentId
	 * @param submission
	 * @return
	 */
	Submission createSubmission(String userId, String asgmtId);

	/**
	 * Find the submission by given submission id
	 * @param parseLong
	 * @return
	 */
	Submission findById(long subId);
}
