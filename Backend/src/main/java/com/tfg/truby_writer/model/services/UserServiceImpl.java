package com.tfg.truby_writer.model.services;

import java.util.Optional;

import com.tfg.truby_writer.model.enums.Enums;

import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.daos.UserDao;
import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.IncorrectLoginException;
import com.tfg.truby_writer.model.exceptions.IncorrectPasswordException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.services.Block;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





@Service
@Transactional
public class UserServiceImpl implements UserService {
	

	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDao userDao;

	@Override
	public User checkUser(Long userId) throws InstanceNotFoundException {

		Optional<User> user = userDao.findById(userId);
		
		if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}
		
		return user.get();
	}
		
	

	@Override
	public void signUp(User user) throws DuplicateInstanceException {
		
		if (userDao.existsByUsername(user.getUsername())) {
			throw new DuplicateInstanceException("project.entities.user", user.getUsername());
		}
		if (user.getBlocked()== true) {
			throw new IllegalArgumentException("User cannot be blocked upon registration");
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
		return checkUser(id);
	}


	@Override
	public void changePassword(Long id, String oldPassword, String newPassword)
		throws InstanceNotFoundException, IncorrectPasswordException {
		
		User user = checkUser(id);
		
		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new IncorrectPasswordException();
		} else {
			user.setPassword(passwordEncoder.encode(newPassword));
		}
		
	}

	
	// ADMIN


	@Override
	public Block<User> searchUsersByFilter(User user, Boolean blocked, String filter) {

		if (user.getRole() != Enums.UserRole.ADMIN) {
			throw new IllegalArgumentException("User does not have admin privileges");
		}

		Slice<User> slice = userDao.find(blocked, filter, 0, 10);
		return new Block<>(slice.getContent(), slice.hasNext());
		
	}

	@Override
	public Boolean blockUser(User user, Long id) throws InstanceNotFoundException {
		if (user.getRole() != Enums.UserRole.ADMIN) {
			throw new IllegalArgumentException("User does not have admin privileges");
		}
		User targetUser = checkUser(id);
		if (targetUser.getBlocked()== true) {
			return false;
		}
		targetUser.setBlocked(true);
		return userDao.save(targetUser) != null;
	}

	@Override
	public User findUserByUsername(User user, String username) throws InstanceNotFoundException {
		if (user.getRole() != Enums.UserRole.ADMIN) {
			throw new IllegalArgumentException("User does not have admin privileges");
		}
		return userDao.findByUsername(username)
				.orElseThrow(() -> new InstanceNotFoundException("project.entities.user", username));
	}

	@Override
	public Boolean unblockUser(User user, Long id) throws InstanceNotFoundException {
		if (user.getRole() != Enums.UserRole.ADMIN) {
			throw new IllegalArgumentException("User does not have admin privileges");
		}
		User targetUser = checkUser(id);
		if (targetUser.getBlocked()== false) {
			return false;
		}
		targetUser.setBlocked(false);
		return userDao.save(targetUser) != null;
	}

	

}
