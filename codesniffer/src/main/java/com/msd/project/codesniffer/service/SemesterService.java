package com.msd.project.codesniffer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msd.project.codesniffer.model.Semester;

/**
   @author fibin
   @version 1.0
   @since 26-Mar-2018
*/
@Service
public interface SemesterService {
	/**
	 * Find all semesters related to a user
	 * @param userId
	 * @return
	 */
	List<Semester> findAllSemestersByUserId(String userId);
	
	/**
	 * create a new semester
	 * @param semester
	 * @return
	 */
	Semester createSemester(Semester semester);
	
	/**
	 * update an existing Semester
	 * @param semester
	 * @return
	 */
	Semester updateSemester(Semester semester);
	
	/**
	 * remove a semester with given ID
	 * @param id
	 */
	void deleteSemester(long id);
	
	/**
	 * add User to a semester and vice ver sa
	 * @param userId
	 * @param semesterId
	 * @return
	 */
	Semester adduserToASem(String userId, String semesterId);
	
	/**
	 * find all semesters in the DB
	 * @return
	 */
	List<Semester> findAllSemesters();

}
