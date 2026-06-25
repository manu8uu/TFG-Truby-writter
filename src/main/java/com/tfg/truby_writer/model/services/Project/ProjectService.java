package com.tfg.truby_writer.model.services.Project;


import com.tfg.truby_writer.model.entities.Project;
import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.entities.User;


public interface ProjectService {

    Project createProject(User user, String name, String description) throws DuplicateInstanceException, InstanceNotFoundException;
    
    Project getProject(Long id) throws InstanceNotFoundException;
        
    void deleteProject(User user,Long id) throws InstanceNotFoundException;


    //Ver estadísticas de avance del proyecto

}
