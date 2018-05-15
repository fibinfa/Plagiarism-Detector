package com.msd.project.codesniffer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.msd.project.codesniffer.model.Assignment;
import com.msd.project.codesniffer.service.AssignmentService;

/**
 * Controller for all operations related to assignments
 */
@RestController

public class AssignmentController {
	private static final Logger logger = LoggerFactory.getLogger(AssignmentController.class);

	@Autowired
	private AssignmentService asgmtService;

	/**
	 * creates an assignment for a particular course
	 * @param courseId - id of the the course under which assignment needs to be created
	 * @param asgmt
	 * @return - newly created assignment object
	 */
	@PostMapping("/api/course/{courseId}/createAsgmt")
	public ResponseEntity<Assignment> findSemesterByUserId(@PathVariable String courseId, 
			@RequestBody Assignment asgmt) {
		Assignment result = asgmtService.createAsgmtUnderCourse(courseId, asgmt);
		if (result != null )
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * find all assignments under a particular course
	 * @param courseId
	 * @return
	 */
	@GetMapping("/api/course/{courseId}/findAllAsgmtsByCourse")
	public ResponseEntity<List<Assignment>> findAllAsgmtsByCourse(@PathVariable String courseId) {
		List<Assignment> result = asgmtService.findAllAsgmtsByCourse(courseId);
		if (result != null )
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * professor can update the threshold of a particular assignment based on the 
	 * difficulty level
	 * An assignment wil be considered plagiarised if the similarity score is greater than threshold
	 * @param newAsgmt
	 * @return
	 */
	@PutMapping("/api/assignment/updateThreshold")
	public ResponseEntity<Assignment> updateThreshold(@RequestBody Assignment newAsgmt) {
		Assignment result = asgmtService.updateThreshold(newAsgmt);
		if (result != null )
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
