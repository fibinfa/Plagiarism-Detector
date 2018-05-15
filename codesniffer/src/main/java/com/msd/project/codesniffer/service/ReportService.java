package com.msd.project.codesniffer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msd.project.codesniffer.model.Report;

/**
   @author fibin, wuhao
   @version 1.0
   @since 26-Mar-2018
*/
@Service
public interface ReportService {
	/**
	 * Return all the reports related to given assignment id
	 * @param asgmtId
	 * @return
	 */
	List<Report> findReportsByAsgmtId(String asgmtId);
	
	/**
	 * Share the report of an assignment
	 * @param asgmtId
	 * @param reportId
	 * @throws Exception
	 */
	void shareReport(long asgmtId, long reportId) throws Exception;
	
	/**
	 * create a new report
	 * @param report
	 * @return
	 */
	Report createReport(Report report);

}