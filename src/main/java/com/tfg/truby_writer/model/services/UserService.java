package com.tfg.truby_writer.model.services;

import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.IncorrectLoginException;
import com.tfg.truby_writer.model.exceptions.IncorrectPasswordException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException; 

public interface UserService {

    void signUp(User user) throws DuplicateInstanceException;
	
	User login(String userName, String password) throws IncorrectLoginException;
	
	User loginFromId(Long id) throws InstanceNotFoundException;
		
	void changePassword(Long id, String oldPassword, String newPassword)
		throws InstanceNotFoundException, IncorrectPasswordException;


    
        
}
