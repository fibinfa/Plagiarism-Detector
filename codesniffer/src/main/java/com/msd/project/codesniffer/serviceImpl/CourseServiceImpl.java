package com.msd.project.codesniffer.serviceImpl;
/**
   @author fibin
   @version 1.0
   @since 26-Mar-2018
*/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msd.project.codesniffer.model.Course;
import com.msd.project.codesniffer.model.Role;
import com.msd.project.codesniffer.model.Semester;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.repository.CourseRepository;
import com.msd.project.codesniffer.repository.SemesterRepository;
import com.msd.project.codesniffer.repository.UserRepository;
import com.msd.project.codesniffer.service.CourseService;


@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	SemesterRepository semRepo;
	
	@Autowired UserRepository userRepo;

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.CourseService#addCourse(java.lang.String, java.lang.String, com.msd.project.codesniffer.model.Course)
	 */
	@Override
	public Course enrollUserInACourseForASemester(String userId, String courseId) {
		User user = userRepo.findOne(Long.parseLong(userId));
		Course course = courseRepo.findOne(Long.parseLong(courseId));
		if(user.getUserType().equals(Role.PROFESSOR)) {
			course.setRecipientEmail(user.getEmail());
		}
		List<Course> enrolledCourses = user.getCourses();
		if(!enrolledCourses.contains(course))
			enrolledCourses.add(course);
		user.setCourses(enrolledCourses);
		List<User> usersForCourse = course.getUsers();
		//course should already exits for that semester 
		if(usersForCourse!=null) {
			usersForCourse.add(user);
		} else {
			usersForCourse = new ArrayList<>();
			usersForCourse.add(user);
		}
		course.setUsers(usersForCourse);
		return courseRepo.save(course);
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.CourseService#addCourseToASem(java.lang.String, java.lang.String)
	 */
	@Override
	public Course addCourseToASem(String semesterId, String courseId) {
		Semester sem = semRepo.findOne(Long.parseLong(semesterId));
		Course course = courseRepo.findOne(Long.parseLong(courseId));
		List<Course> courseInSem = sem.getCourses();
		courseInSem.add(course);
		sem.setCourses(courseInSem);
		List<Semester> semsForCourse = course.getSemesters();
		//course should already exits for that semester 
		if(semsForCourse!=null) {
			semsForCourse.add(sem);
		} else {
			semsForCourse = new ArrayList<>();
			semsForCourse.add(sem);
		}
		course.setSemesters(semsForCourse);
		return courseRepo.save(course);
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.CourseService#createCourse(com.msd.project.codesniffer.model.Course)
	 */
	@Override
	public Course createCourse(Course course) {
		return courseRepo.save(course);
	}


	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.CourseService#findCoursesBySemId(java.lang.String)
	 */
	@Override
	public List<Course> findCoursesBySemId(String semesterId) {
		return courseRepo.findBySemesters_Id(Long.parseLong(semesterId));
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.CourseService#findAllCourses()
	 */
	@Override
	public List<Course> findAllCourses() {
		return (List<Course>) courseRepo.findAll();
	}
	
	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.CourseService#findUnselectedCoursesBySemId(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Course> findUnselectedCoursesBySemId(String userId, String semesterId) {		
		List<Course> courses = (List<Course>) courseRepo.findAll();
		List<Course> coursesenrolledByUser = courseRepo.findByUsers_Id(Long.parseLong(userId));
		List<Course> result = new ArrayList<>();
		for(Course c: courses) {
			if(!coursesenrolledByUser.contains(c))
				result.add(c);
		}
		return new ArrayList<>(new HashSet<>(result));
	}


	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.CourseService#updateCourse(com.msd.project.codesniffer.model.Course)
	 */
	@Override
	public Course updateCourse(Course course) {
		return courseRepo.save(course);
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.UserService#deleteUser(long)
	 */
	@Override
	public void deleteCourse(long id) {
		courseRepo.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.CourseService#createCourseByAProf(java.lang.String, java.lang.String, com.msd.project.codesniffer.model.Course)
	 */
	@Override
	public Course createCourseByAProf(String userId, String semesterid, Course course) {
		
		User user = userRepo.findOne(Long.parseLong(userId));
		Semester sem = semRepo.findOne(Long.parseLong(semesterid));
		//add new course to this semester
		List<Course> courses = sem.getCourses();
		courses.add(course);
		sem.setCourses(courses);
		
		//add new course to user
		List<Course> coursesByUser = user.getCourses();
		coursesByUser.add(course);
		user.setCourses(coursesByUser);
		
		//add sem to course
		List<Semester> newSemList = new ArrayList<>();
		newSemList.add(sem);
		course.setSemesters(newSemList);
		
		//enroll user to the course
		List<User> newUserList = new ArrayList<>();
		newUserList.add(user);
		course.setUsers(newUserList);
		
		course.setRecipientEmail(user.getEmail());
		
		return courseRepo.save(course);
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.CourseService#findById(java.lang.Long)
	 */
	@Override
	public Course findById(Long courseId) {
		return courseRepo.findOne(courseId);
	}
	
	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.CourseService#findCourseByUserIdAndSemId(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Course> findCourseByUserIdAndSemId(String userId, String semesterId) {
		List<Course> courses = (List<Course>) courseRepo.findAll();
		List<Course> coursesenrolledByUser = courseRepo.findByUsers_Id(Long.parseLong(userId));
		List<Course> result = new ArrayList<>();
		for(Course c: courses) {
			if(coursesenrolledByUser.contains(c))
				result.add(c);
		}
		return new ArrayList<>(new HashSet<>(result));
	}
}
