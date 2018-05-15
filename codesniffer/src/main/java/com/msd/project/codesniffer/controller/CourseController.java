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

import com.msd.project.codesniffer.model.Course;
import com.msd.project.codesniffer.service.CourseService;

/**
 * Controller for all course related CRUD operations
 */
@RestController

public class CourseController {
	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

	@Autowired
	private CourseService courseService;

	// user finds all enrolled courses for a particular semester enrolled by him
	@GetMapping("/api/user/{userId}/semester/{semesterId}/findAllEnrolledCourses")
	public ResponseEntity<List<Course>> findCourseByUserIdAndSemId(@PathVariable String userId,
			@PathVariable String semesterId) {
		List<Course> result = courseService.findCourseByUserIdAndSemId(userId, semesterId);
		if (result != null && !result.isEmpty())
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * finds all courses under a particular semester
	 * @param semesterId
	 * @return
	 */
	@GetMapping("/api/user/semester/{semesterId}/findAllCoursesForASemester")
	public ResponseEntity<List<Course>> findCoursesBySemId(@PathVariable String semesterId) {
		List<Course> result = courseService.findCoursesBySemId(semesterId);
		if (result != null && !result.isEmpty())
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// list will be displayed on a side by which student/professor can register for
	// a course
	// in a particular semester
	@GetMapping("/api/user/{userId}/semester/{semesterId}/findAllUnregisteredCoursesForASemester")
	public ResponseEntity<List<Course>> findUnselectedCoursesBySemId(@PathVariable String userId,
			@PathVariable String semesterId) {
		List<Course> result = courseService.findUnselectedCoursesBySemId(userId, semesterId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/**
	 * enrolls user for a particular course in a particular semester
	 * @param userId
	 * @param semesterid
	 * @param courseId
	 * @return
	 */
	@PutMapping("/api/user/{userId}/semester/{semesterid}/course/{courseId}")
	public ResponseEntity<Course> enrollUserInACourse(@PathVariable String userId, @PathVariable String semesterid,
			@PathVariable String courseId) {
		Course result = courseService.enrollUserInACourseForASemester(userId, courseId);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * add course by professor
	 * professor can add a course in a particular semester
	 * @param userId
	 * @param semesterid
	 * @param course
	 * @return
	 */
	@PostMapping("/api/user/{userId}/semester/{semesterid}/addcourse")
	public ResponseEntity<Course> addCourse(@PathVariable String userId, @PathVariable String semesterid,
			@RequestBody Course course) {
		Course result = courseService.createCourseByAProf(userId, semesterid, course);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/**
	 * adds user to all courses in a particular semester
	 * @param userId
	 * @return
	 */
	@PutMapping("/api/user/{userId}/course/addUserToAllCourses")
	public ResponseEntity addUserToAllCourses(@PathVariable String userId) {
		List<Course> courses = courseService.findAllCourses();

		for (Course course : courses) {
			logger.debug("adding user " + userId + " to course " + course.getName());
			courseService.enrollUserInACourseForASemester(userId, String.valueOf(course.getId()));
		}

		return new ResponseEntity(HttpStatus.OK);
	}

	// admin functionality
	/**
	 * adds a course to a particular semester 
	 * this is basically an admin functionality
	 * @param semesterId
	 * @param courseId
	 * @return
	 */
	@PutMapping("/api/semster/{semesterId}/course/{courseId}")
	public ResponseEntity<Course> addCourseToASem(@PathVariable String semesterId, @PathVariable String courseId) {
		Course result = courseService.addCourseToASem(semesterId, courseId);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// admin functionality
	/**
	 * create a course entry in the database
	 * @param course
	 * @return
	 */
	@PostMapping("/api/course")
	public ResponseEntity<Course> createCourse(@RequestBody Course course) {
		Course result = courseService.createCourse(course);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
