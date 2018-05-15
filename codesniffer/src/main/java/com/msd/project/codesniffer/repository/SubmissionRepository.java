package com.msd.project.codesniffer.repository;
/**
   @author fibin
   @version 1.0
   @since 23-Mar-2018
*/

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.msd.project.codesniffer.model.Submission;

public interface SubmissionRepository extends CrudRepository<Submission, Long> {
	List<Submission> findByAssignment_Id(Long asgmtId);
}
