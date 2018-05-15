package com.msd.project.codesniffer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.msd.project.codesniffer.model.Submission;
import com.msd.project.codesniffer.service.SubmissionService;

/**
 * performs submission related CRUD operations
 */
@RestController

public class SubmissionController {
	private static final Logger logger = LoggerFactory.getLogger(SubmissionController.class);

	@Autowired
	private SubmissionService subService;

	/**
	 * find all submissions under a particular assignment
	 * @param assignmentId
	 * @return
	 */
	@GetMapping("/api/assignment/{assignmentId}/findAllSubmissions")
	public ResponseEntity<List<Submission>> findSubmissionsByAsgmtId(@PathVariable String assignmentId) {
		List<Submission> result = subService.findAllSubmissionsByAsgmtId(assignmentId);
		if (result != null && !result.isEmpty())
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}