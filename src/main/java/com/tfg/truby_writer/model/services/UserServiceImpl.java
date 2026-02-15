package com.tfg.truby_writer.model.services;

import java.util.Optional;

import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.daos.UserDao;
import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.IncorrectLoginException;
import com.tfg.truby_writer.model.exceptions.IncorrectPasswordException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException; 


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PermissionChecker permissionChecker;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDao userDao;
	

	@Override
	public void signUp(User user) throws DuplicateInstanceException {
		
		if (userDao.existsByUsername(user.getUsername())) {
			throw new DuplicateInstanceException("project.entities.user", user.getUsername());
		}
		
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userDao.save(user);
		
	}

	@Override
	@Transactional(readOnly=true)
	public User login(String userName, String password) throws IncorrectLoginException {
		
		Optional<User> user = userDao.findByUsername(userName);
		
		if (!user.isPresent()) {
			throw new IncorrectLoginException(userName, password);
		}
		
		if (!passwordEncoder.matches(password, user.get().getPassword())) {
			throw new IncorrectLoginException(userName, password);
		}
		
		return user.get();
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public User loginFromId(Long id) throws InstanceNotFoundException {
		return permissionChecker.checkUser(id);
	}


	@Override
	public void changePassword(Long id, String oldPassword, String newPassword)
		throws InstanceNotFoundException, IncorrectPasswordException {
		
		User user = permissionChecker.checkUser(id);
		
		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new IncorrectPasswordException();
		} else {
			user.setPassword(passwordEncoder.encode(newPassword));
		}
		
	}

}
