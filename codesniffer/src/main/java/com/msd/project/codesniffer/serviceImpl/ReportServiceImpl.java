package com.msd.project.codesniffer.serviceImpl;
/**
   @author fibin, wuhao
   @version 1.0
   @since 26-Mar-2018
*/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.msd.project.codesniffer.model.Assignment;
import com.msd.project.codesniffer.repository.AssignmentRepository;
import com.msd.project.codesniffer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msd.project.codesniffer.model.Report;
import com.msd.project.codesniffer.model.Submission;
import com.msd.project.codesniffer.repository.ReportRepository;
import com.msd.project.codesniffer.repository.SubmissionRepository;
import com.msd.project.codesniffer.service.ReportService;



@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	ReportRepository reportRepo;
	
	@Autowired
	SubmissionRepository subRepo;


	@Autowired
	UserRepository userRepo;

	@Autowired
	AssignmentRepository asgmtRepo;

	@Autowired
	private StorageServiceImpl storageService;


	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.ReportService#findReportsByAsgmtId(java.lang.String)
	 */
	@Override
	public List<Report> findReportsByAsgmtId(String asgmtId) {
		Set<Report> result = new HashSet<>();
		List<Submission> subs = subRepo.findByAssignment_Id(Long.parseLong(asgmtId));
		for(Submission sub: subs) {
			result.addAll(reportRepo.findBySubmissions_Id(sub.getId()));
		}
		List<Report> reportList = new ArrayList<>(result);
		return reportList;
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.ReportService#createReport(com.msd.project.codesniffer.model.Report)
	 */
	@Override
	public Report createReport(Report report) {
		return reportRepo.save(report);
	}


	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.ReportService#shareReport(long, long)
	 */
	public void shareReport(long asgmtId, long reportId) throws Exception {
		Report report = reportRepo.findOne(reportId);
		double score = report.getSimilarityScore();
		String reportLink = report.getReportLink();
		String userId1 = reportLink.split("_")[0].split("user")[1];
		String userId2 = reportLink.split("_")[5].split("user")[1];
		Assignment asgmt = asgmtRepo.findOne(asgmtId);
		String recipientMail = userRepo.findOne(Long.parseLong(userId1)).getEmail();
		storageService.sendEmail(userId1, userId2, asgmt.getName(), score, recipientMail, reportLink);
		recipientMail = userRepo.findOne(Long.parseLong(userId2)).getEmail();
		storageService.sendEmail(userId1, userId2, asgmt.getName(), score, recipientMail, reportLink);
	}
}