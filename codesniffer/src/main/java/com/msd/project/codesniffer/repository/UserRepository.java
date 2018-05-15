package com.msd.project.codesniffer.repository;

import org.springframework.data.repository.CrudRepository;

import com.msd.project.codesniffer.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByUsername(String username);
	User findByUsernameAndPassword(String username, String password);
}
