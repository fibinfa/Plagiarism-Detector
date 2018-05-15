package com.msd.project.codesniffer.service;

import com.msd.project.codesniffer.model.User;

import java.util.List;

/**
   @author fibin
   @version 1.0
   @since 26-Mar-2018
*/

public interface UserService {
	/**
	 * Find the User object from DB with given user name and password
	 * @param userName
	 * @param password
	 * @return
	 */
	User findUserByCredentials(String userName, String password);

	/**
	 * Find the User object from DB with given id
	 * @param id
	 * @return
	 */
	User findById(String id);

	/**
	 * Find the User object from DB with given user name
	 * @param userName
	 * @return
	 */
	User findByUsername(String userName);
	
	/**
	 * Find all User objects in the DB
	 * @return
	 */
	List<User> findAllUser();
	
	/**
	 * Create new User object in the DB
	 * @param user
	 * @return
	 */
	User createUser(User user);
	
	/**
	 * Update existing User object in the DB
	 * @param user
	 * @return
	 */
	User updateUser(User user);
	
	/**
	 * Delete existing User object in the DB with given ID
	 * @param id
	 */
	void deleteUser(long id);
}
