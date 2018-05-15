package com.msd.project.codesniffer.repository;
/**
   @author fibin
   @version 1.0
   @since 23-Mar-2018
*/

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.msd.project.codesniffer.model.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {
	Course findByName(String name);
	List<Course> findByUsers_IdAndSemesters_Id(Long userId, Long semesterId);
	List<Course> findBySemesters_Id(Long semesterId);
	List<Course> findByUsers_Id(Long userId);
}
