package com.msd.project.codesniffer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.msd.project.codesniffer.model.Semester;

public interface SemesterRepository extends CrudRepository<Semester, Long>{
	List<Semester> findSemesterByUsersId(Long userId);
}
