package com.msd.project.codesniffer.controller;

import com.msd.project.codesniffer.model.Course;
import com.msd.project.codesniffer.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.msd.project.codesniffer.model.Semester;
import com.msd.project.codesniffer.service.SemesterService;

import java.util.List;

/**
 * All admin related functions
 */
@RestController

public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(SemesterController.class);

    @Autowired
    private SemesterService semService;

    @Autowired
    private CourseService courseService;

    // Semester End-Points
    /**
     * creates a new semester entry in the database
     * @param semester
     * @return
     */
    @PostMapping("/api/semester")
    public ResponseEntity<Semester> createSemester(@RequestBody Semester semester) {
        Semester result = semService.createSemester(semester);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * updates a semester object
     * @param semester
     * @return
     */
    @PutMapping("/api/semester")
    public ResponseEntity<Semester> updateSemester(@RequestBody Semester semester) {
        logger.debug("--Entered update service--");
        Semester result = semService.updateSemester(semester);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * deletes a semester based on semester id
     * @param semesterId
     * @return
     */
    @RequestMapping(value = "/api/semester/{semesterId}", method = RequestMethod.DELETE)
    public ResponseEntity<Semester> deleteSemester(@PathVariable(value="semesterId") String semesterId) {
        logger.debug("--Entered delete service--");
        logger.debug("Semester ID: ", semesterId);
        semService.deleteSemester(Long.parseLong(semesterId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * find all semesters from the semester table
     * @return
     */
    @RequestMapping(value = "/api/semester/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Semester>> getAllSemesters() {
        List<Semester> semesters = semService.findAllSemesters();
        return new ResponseEntity<>(semesters, HttpStatus.OK);
    }


    // Course End-Points
    /**
     * updates a course
     * @param course
     * @return
     */
    @PutMapping("/api/course")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course) {
        logger.debug("--Entered update service--");
        Course result = courseService.updateCourse(course);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * deletes a particular course based on the id
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/api/course/{courseId}", method = RequestMethod.DELETE)
    public ResponseEntity<Course> deleteCourse(@PathVariable(value="courseId") String courseId) {
        logger.debug("--Entered delete service--");
        logger.debug("Course ID: ", courseId);
        courseService.deleteCourse(Long.parseLong(courseId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * finds all courses from the table
     * @return - list of courses
     */
    @RequestMapping(value = "/api/course/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Course> findAllCourses() {
        logger.debug("in admin controller find all courses");
        List<Course> courses = courseService.findAllCourses();
        return courses;
    }
}
