package com.msd.project.codesniffer.serviceImpl;
/**
   @author fibin, Hao
   @version 1.0
   @since 26-Mar-2018
*/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msd.project.codesniffer.model.Semester;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.repository.SemesterRepository;
import com.msd.project.codesniffer.repository.UserRepository;
import com.msd.project.codesniffer.service.SemesterService;


@Service
public class SemesterServiceImpl implements SemesterService {

	@Autowired
	SemesterRepository semesterRepo;
	
	@Autowired
	UserRepository userRepo;


	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.SemesterService#findAllSemesters()
	 */
	@Override
	public List<Semester> findAllSemesters() {
		return (List<Semester>) semesterRepo.findAll();
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.SemesterService#findAllSemestersByUserId(java.lang.String)
	 */
	@Override
	public List<Semester> findAllSemestersByUserId(String userId) {
		List<Semester> result = semesterRepo.findSemesterByUsersId(Long.parseLong(userId));
		return new ArrayList<>(new HashSet<>(result));
	}
	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.SemesterService#createSemester(com.msd.project.codesniffer.model.Semester)
	 */
	@Override
	public Semester createSemester(Semester semester) {
		return semesterRepo.save(semester);
	}
	
	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.SemesterService#adduserToASem(java.lang.String, java.lang.String)
	 */
	@Override
	public Semester updateSemester(Semester semester) {
		return semesterRepo.save(semester);
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.UserService#deleteUser(long)
	 */
	@Override
	public void deleteSemester(long id) {
		semesterRepo.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.SemesterService#adduserToASem(java.lang.String, java.lang.String)
	 */
	@Override
	public Semester adduserToASem(String userId, String semesterId) {
		User user = userRepo.findOne(Long.parseLong(userId));
		Semester sem = semesterRepo.findOne(Long.parseLong(semesterId));
		List<Semester> enrolledSems= user.getSemesters();
		if(enrolledSems!=null && !enrolledSems.isEmpty()) {
			if(!enrolledSems.contains(sem))
				enrolledSems.add(sem);
		} else {
			enrolledSems = new ArrayList<>();
			enrolledSems.add(sem);
		}
		user.setSemesters(enrolledSems);
		List<User> usersInASem = sem.getUsers();
		if(usersInASem!=null && !usersInASem.isEmpty()) {
			usersInASem.add(user);
		} else {
			usersInASem = new ArrayList<>();
			usersInASem.add(user);
		}
		sem.setUsers(usersInASem);
		
		return semesterRepo.save(sem);
	}
}
