package com.tfg.truby_writer.model.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.daos.UserDao;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;


@Service
@Transactional(readOnly=true)
public class PermissionCheckerImpl implements PermissionChecker {
	
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

}
