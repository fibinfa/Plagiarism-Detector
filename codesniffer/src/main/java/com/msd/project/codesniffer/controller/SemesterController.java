package com.msd.project.codesniffer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msd.project.codesniffer.model.Semester;
import com.msd.project.codesniffer.service.SemesterService;

/**
 * performs semester related CRUD operations
 */
@RestController

public class SemesterController {
    private static final Logger logger = LoggerFactory.getLogger(SemesterController.class);

    @Autowired
    private SemesterService semService;

    /**
     * find all semesters for which a user is enrolled for
     * @param userId
     * @return
     */
    @GetMapping("/api/user/{userId}/semester/findSemestersByUserId")
    public ResponseEntity<List<Semester>> findSemesterByUserId(@PathVariable String userId) {
        List<Semester> result = semService.findAllSemestersByUserId(userId);
        if (result != null && !result.isEmpty())
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * enroll user to a particular semester
     * @param userId
     * @param semesterId
     * @return
     */
    @PutMapping("/api/user/{userId}/semester/{semesterId}")
    public ResponseEntity<Semester> enrollUserForASem(@PathVariable String userId,
                                                      @PathVariable String semesterId) {
        Semester result = semService.adduserToASem(userId, semesterId);
        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * add user to all semesters
     * @param userId
     * @return
     */
    @PutMapping("/api/user/{userId}/semester/addUserToAllSemesters")
    public ResponseEntity addUserToAllSemesters(@PathVariable String userId) {
        List<Semester> semesters = semService.findAllSemesters();

        for (Semester sem : semesters) {
            logger.debug("adding user " + userId + " to semester " + sem.getName());
            semService.adduserToASem(userId, String.valueOf(sem.getId()));
        }

        return new ResponseEntity(HttpStatus.OK);
    }
    
}
