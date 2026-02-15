package com.tfg.truby_writer.model.services;

import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;

public interface PermissionChecker {

	User checkUser(Long userId) throws InstanceNotFoundException;
	
	
}
