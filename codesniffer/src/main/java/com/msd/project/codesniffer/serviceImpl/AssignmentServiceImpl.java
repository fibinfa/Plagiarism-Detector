package com.msd.project.codesniffer.serviceImpl;
/**
   @author fibin
   @version 1.0
   @since 26-Mar-2018
*/

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msd.project.codesniffer.model.Assignment;
import com.msd.project.codesniffer.model.Course;
import com.msd.project.codesniffer.model.Report;
import com.msd.project.codesniffer.repository.AssignmentRepository;
import com.msd.project.codesniffer.repository.CourseRepository;
import com.msd.project.codesniffer.repository.ReportRepository;
import com.msd.project.codesniffer.service.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	CourseRepository courseRepo;

	@Autowired
	AssignmentRepository asgmtRepo;

	@Autowired
	ReportRepository reportRepo;

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.AssignmentService#createAsgmtUnderCourse(java.lang.String, com.msd.project.codesniffer.model.Assignment)
	 */
	@Override
	public Assignment createAsgmtUnderCourse(String courseId, Assignment asgmt) {
		Course course = courseRepo.findOne(Long.parseLong(courseId));
		List<Assignment> asgmtsUnderCourse = course.getAssignments();
		asgmt.setCourse(course);
		if (asgmtsUnderCourse != null && !asgmtsUnderCourse.isEmpty()) {
			asgmtsUnderCourse.add(asgmt);
		} else {
			asgmtsUnderCourse = new ArrayList<>();
			asgmtsUnderCourse.add(asgmt);
		}
		return asgmtRepo.save(asgmt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.msd.project.codesniffer.service.AssignmentService#findAllAsgmtsByCourse(
	 * java.lang.String)
	 */
	@Override
	public List<Assignment> findAllAsgmtsByCourse(String courseId) {
		return asgmtRepo.findAssignmentsByCourse_Id(Long.parseLong(courseId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.msd.project.codesniffer.service.AssignmentService#updateThreshold(com.msd
	 * .project.codesniffer.model.Assignment)
	 */
	@Override
	public Assignment updateThreshold(Assignment newAsgmt) {
		// while updating threshold, flag of atleast one pagdetected should also be
		// updated
		boolean flag = false;
		Long asgmtId = newAsgmt.getId();
		Assignment asgmt = asgmtRepo.findOne(asgmtId);
		Iterable<Report> reports = reportRepo.findAll();
		for (Report report : reports) {
			if (report.getReportLink().contains("asgmt" + asgmt.getName())
					&& report.getSimilarityScore() > newAsgmt.getThreshold()) {
				flag = true;
				break;
			}
		}
		newAsgmt.setFlag(flag);
		newAsgmt.setCourse(asgmt.getCourse());
		return asgmtRepo.save(newAsgmt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.msd.project.codesniffer.service.AssignmentService#findById(long)
	 */
	@Override
	public Assignment findById(long id) {
		return asgmtRepo.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.msd.project.codesniffer.service.AssignmentService#update(com.msd.project.
	 * codesniffer.model.Assignment)
	 */
	@Override
	public void update(Assignment currentAsgmtObject) {
		asgmtRepo.save(currentAsgmtObject);
	}
}
