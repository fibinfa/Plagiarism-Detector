package com.msd.project.codesniffer.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msd.project.codesniffer.model.Report;
import com.msd.project.codesniffer.service.ReportService;

/**
 * performs all report related operations
 */
@RestController

public class ReportController {


	@Autowired
	private ReportService reportService;
	

	
	/**
	 * find all reports by assignment id
	 * @param asgmtId
	 * @return
	 */
	@GetMapping("/api/assignment/{asgmtId}/findReportsByAsgmtId")
	public ResponseEntity<List<Report>> findReportsByAsgmtId(@PathVariable String asgmtId) {
		List<Report> result = reportService.findReportsByAsgmtId(asgmtId);
		if (result != null && !result.isEmpty())
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * shares the particular report with multiple students involved in the 
	 * plagiarism
	 * @param asgmtId
	 * @param rid
	 * @throws Exception
	 */
	@PostMapping("/api/assignment/{asgmtId}/report/{rid}/share")
	public void shareReport(@PathVariable long asgmtId,@PathVariable long rid) throws Exception {
		reportService.shareReport(asgmtId, rid);
	}
	
	
}
