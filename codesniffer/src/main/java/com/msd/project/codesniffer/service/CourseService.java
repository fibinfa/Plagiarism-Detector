package com.msd.project.codesniffer.service;

import java.util.List;

import com.msd.project.codesniffer.model.Course;

/**
   @author fibin, wuhao
   @version 1.0
   @since 26-Mar-2018
*/

public interface CourseService {
	Course enrollUserInACourseForASemester(String userId, String courseId);
	Course addCourseToASem(String semesterId, String courseId);
	Course createCourse(Course course);
	Course updateCourse(Course course);
	Course findById(Long courseId);
	void deleteCourse(long id);
	List<Course> findCourseByUserIdAndSemId(String userId, String semesterId);
	List<Course> findCoursesBySemId(String semesterId);
	List<Course> findAllCourses();
	/**
	 * @param userId
	 * @param semesterId
	 * @return
	 */
	List<Course> findUnselectedCoursesBySemId(String userId, String semesterId);
	/**
	 * @param userId
	 * @param semesterid
	 * @param course
	 * @return
	 */
	Course createCourseByAProf(String userId, String semesterid, Course course);
}
