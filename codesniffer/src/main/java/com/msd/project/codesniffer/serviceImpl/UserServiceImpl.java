package com.msd.project.codesniffer.serviceImpl;
/**
   @author fibin
   @version 1.0
   @since 26-Mar-2018
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.repository.UserRepository;
import com.msd.project.codesniffer.service.UserService;

import java.util.List;


/**
 * @author wuhao
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.UserService#findUserByCredentials(java.lang.String, java.lang.String)
	 */
	@Override
	public User findUserByCredentials(String username, String password) {
        return userRepo.findByUsernameAndPassword(username, password);
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.UserService#createUser(com.msd.project.codesniffer.model.User)
	 */
	@Override
	public User createUser(User user) {
		return userRepo.save(user);
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.UserService#updateUser(com.msd.project.codesniffer.model.User)
	 */
	@Override
	public User updateUser(User user) {
		return userRepo.save(user);
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.UserService#deleteUser(long)
	 */
	@Override
	public void deleteUser(long id) {
		userRepo.delete(id);
	}
	
	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.UserService#findByUsername(java.lang.String)
	 */
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.UserService#findById(java.lang.String)
	 */
	public User findById(String id) {
		return userRepo.findOne(Long.parseLong(id));
	}

	/* (non-Javadoc)
	 * @see com.msd.project.codesniffer.service.UserService#findAllUser()
	 */
	public List<User> findAllUser() {
		return (List<User>) userRepo.findAll();
	}
}
