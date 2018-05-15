package com.msd.project.codesniffer.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.msd.project.codesniffer.model.Submission;
import com.msd.project.codesniffer.service.SubmissionService;
import com.msd.project.codesniffer.serviceImpl.StorageServiceImpl;

import net.lingala.zip4j.exception.ZipException;
	
/**
 * controller for performing all file related operations
 */
@RestController
public class FileUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	StorageServiceImpl storageService;

	@Autowired
	SubmissionService subService;

	

	// Multiple file upload
	/**
	 * uploads a multipart file and creates a submisison entry in the database
	 * @param uploadfile
	 * @param userid
	 * @param semid
	 * @param courseid
	 * @param assignmentid
	 * @return
	 */
	@PostMapping("/api/user/{userid}/sem/{semid}/course/{courseid}/assignment/{assignmentid}/uploadFile")
	public ResponseEntity<Submission> uploadFileMulti(@RequestParam MultipartFile uploadfile,
			@PathVariable String userid, @PathVariable String semid, @PathVariable String courseid,
			@PathVariable String assignmentid) {
		try {
			Submission result = subService.createSubmission(userid, assignmentid);
			storageService.store(uploadfile, userid,semid, courseid, assignmentid, String.valueOf(result.getId()));
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * accepts a git link from gthe user and creates a submission entry after cloning the 
	 * particular asssignment from that git
	 * @param gitRepo
	 * @param userid
	 * @param semid
	 * @param courseid
	 * @param assignmentid
	 * @return
	 * @throws ZipException
	 * @throws IOException
	 */
	@PostMapping("/api/user/{userid}/sem/{semid}/course/{courseid}/assignment/{assignmentid}/gitclone")
	public ResponseEntity<Submission> cloneGit(@RequestParam String gitRepo, @PathVariable String userid,
			@PathVariable String semid, @PathVariable String courseid, @PathVariable String assignmentid)
			throws ZipException, IOException {
		Submission result = subService.createSubmission(userid, assignmentid);
		boolean flag = storageService.cloneGit(gitRepo, userid, semid, courseid, assignmentid, String.valueOf(result.getId()));
		if (flag)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * performs comparison
	 * @param userid
	 * @param semid
	 * @param courseid
	 * @param assignmentid
	 * @param submissionid
	 * @return
	 */
	@PostMapping("/api/user/{userid}/sem/{semid}/course/{courseid}/assignment/{assignmentid}/submission/{submissionid}/compare")
	public ResponseEntity<String> comapreFiles(@PathVariable String userid, @PathVariable String semid, @PathVariable String courseid,
			@PathVariable String assignmentid,
			@PathVariable String submissionid) {
		try {
			storageService.compare(userid,semid, courseid, assignmentid, submissionid);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("exception in compare method");
			return new ResponseEntity<>("Exception thrown", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	


}