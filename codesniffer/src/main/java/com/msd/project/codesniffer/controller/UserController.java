package com.msd.project.codesniffer.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.msd.project.codesniffer.model.Role;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.service.UserService;

/**
 * Performs all user related CRUD operations
 */
@RestController

public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * login functionality
	 * @param username
	 * @param password
	 * @param httpSession
	 * @return
	 */
	@GetMapping("/api/user/findUserByCredentials")
	public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password,
			HttpSession httpSession) {
		// user service login functionality
		logger.debug("--Entered login service--");
		User user = userService.findUserByCredentials(username, password);
		if (user == null) {
			logger.debug("User not registered or username/password does not match");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			String userIdStr = String.valueOf(user.getId());
			httpSession.setAttribute(userIdStr, user);
			logger.debug("User returned successfully");
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}

	/**
	 * finds all users in database
	 * @return
	 */
	@RequestMapping(value = "/api/user/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers() {
		List<User> users = userService.findAllUser();
		return users;
	}

	/**
	 * register functionality
	 * @param user
	 * @return
	 */
	@PostMapping(path = "/api/user/createUser")
	public ResponseEntity<User> addNewUser(@RequestBody User user) {
		logger.debug("--Entered register service--");
		User result = userService.createUser(user);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * finds a user by username
	 * @param username
	 * @return
	 */
	@GetMapping("/api/user/findUserByUsername")
	public ResponseEntity<?> findUserByUsername(@RequestParam String username) {
		if (userService.findByUsername(username) != null)
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		else
			return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * finds a user by userid
	 * @param userId
	 * @return
	 */
	@GetMapping("/api/user/findUserById")
	public ResponseEntity<User> findUserById(@RequestParam String userId) {
		User user = userService.findById(userId);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * updates a user
	 * @param user
	 * @return
	 */
	@PutMapping(path = "/api/user/updateUser")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		logger.debug("--Entered update service--");
		User result = userService.updateUser(user);
		if (result != null)
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * deletes a particular user
	 * this is an admin functionality
	 * @param userId
	 * @return
	 */
	@DeleteMapping(path = "/api/user/deleteUser/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable String userId) {
		logger.debug("--Entered delete service--");
		userService.deleteUser(Long.parseLong(userId));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * for role based authentication
	 * some endpoints are only accessed by admins
	 * @param uid
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/api/auth/isAdmin/{uid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Boolean> isAdmin(@PathVariable String uid, HttpSession httpSession) {

		logger.debug("UserController isAdmin(): ", uid);

		// 1. check if the user is logged in
		User resFromSession = (User) httpSession.getAttribute(uid);

		if (resFromSession != null) {
			String resFromSessionStr = String.valueOf(resFromSession.getId());

			if (resFromSessionStr.equals(uid) && resFromSession.getUserType().equals(Role.ADMIN)) {
				// user already logged in
				return new ResponseEntity<>(true, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
	}

	/**
	 * for role based authentication
	 * some endpoints are only accessed by admins
	 * @param uid
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/api/auth/isFaculty/{uid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Boolean> isFaculty(@PathVariable String uid, HttpSession httpSession) {

		logger.debug("UserController isFaculty(): ", uid);

		// 1. check if the user is logged in
		User resFromSession = (User) httpSession.getAttribute(uid);

		String resFromSessionStr = String.valueOf(resFromSession.getId());
		if (resFromSession != null && resFromSessionStr.equals(uid)
				&& resFromSession.getUserType().equals(Role.PROFESSOR)) {
			// user already logged in
			return new ResponseEntity<>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * check if  a user is logged in
	 * session management
	 * @param uid
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/api/auth/loggedIn/{uid}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> loggedIn(@PathVariable String uid, HttpSession httpSession) {
		logger.debug("UserController, loggedIn()" + uid);
		// try to find the user in session
		User resFromSession = (User) httpSession.getAttribute(uid);
		if (resFromSession != null) {
			// user already logged in
			return new ResponseEntity<>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(false, HttpStatus.CONFLICT);
		}
	}

	/**
	 * login functionality with user object stored in session
	 * @param user
	 * @param httpSession
	 * @return
	 */
	@PostMapping("/api/auth/logIn")
	public ResponseEntity<User> logIn(@Validated @RequestBody User user, HttpSession httpSession) {
		logger.debug("UserController, logIn(): ");
		// user service login functionality
		User resFromDB = userService.findUserByCredentials(user.getUsername(), user.getPassword());

		if (resFromDB == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			String userIdStr = String.valueOf(resFromDB.getId());
			httpSession.setAttribute(userIdStr, resFromDB);
			return new ResponseEntity<>(resFromDB, HttpStatus.OK);
		}
	}

	/**
	 * logout functionality
	 * @param uid
	 * @param httpSession
	 * @return
	 */
	@GetMapping("/api/auth/logOut/{uid}")
	public ResponseEntity<User> logOut(@PathVariable String uid, HttpSession httpSession) {
		logger.debug("UserController, logOut(): ");

		User resFromSession = (User) httpSession.getAttribute(uid);

		if (resFromSession != null) {
			String resFromSessionStr = String.valueOf(resFromSession.getId());
			httpSession.removeAttribute(resFromSessionStr);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
