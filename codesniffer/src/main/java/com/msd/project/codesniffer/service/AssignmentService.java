package com.msd.project.codesniffer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msd.project.codesniffer.model.Assignment;

/**
   @author fibin
   @version 1.0
   @since 26-Mar-2018
*/
@Service
public interface AssignmentService {
	
	/**
	 * create a new assignment related to the course
	 * @param courseId
	 * @param asgmt
	 * @return
	 */
	Assignment createAsgmtUnderCourse(String courseId, Assignment asgmt);

	/**
	 * find all assignments based on the courseId
	 * @param courseId
	 * @return
	 */
	List<Assignment> findAllAsgmtsByCourse(String courseId);

	/**
	 * update the threshold of plagiarism for a given assignment 
	 * @param newAsgmt
	 * @return
	 */
	Assignment updateThreshold(Assignment newAsgmt);

	/**
	 * find the assignment by id
	 * @param parseLong
	 * @return
	 */
	Assignment findById(long parseLong);

	/**
	 * update a given assignment
	 * @param currentAsgmtObject
	 */
	void update(Assignment currentAsgmtObject);
}
