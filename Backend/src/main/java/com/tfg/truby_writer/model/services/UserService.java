package com.tfg.truby_writer.model.services;

import java.util.List;

import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.IncorrectLoginException;
import com.tfg.truby_writer.model.exceptions.IncorrectPasswordException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.services.Block; 

public interface UserService {

	User checkUser(Long userId) throws InstanceNotFoundException;

    void signUp(User user) throws DuplicateInstanceException;
	
	User login(String userName, String password) throws IncorrectLoginException;
	
	User loginFromId(Long id) throws InstanceNotFoundException;
		
	void changePassword(Long id, String oldPassword, String newPassword)
		throws InstanceNotFoundException, IncorrectPasswordException;
	

	// ADMIN

	Block<User> searchUsersByFilter(User user, Boolean blocked, String filter);

	Boolean blockUser(User user, Long id) throws InstanceNotFoundException;

	User findUserByUsername(User user, String username) throws InstanceNotFoundException;

	Boolean unblockUser(User user, Long id) throws InstanceNotFoundException, IllegalArgumentException;
        
}
