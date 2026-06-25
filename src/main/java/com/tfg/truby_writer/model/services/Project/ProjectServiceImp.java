package com.tfg.truby_writer.model.services.Project;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.tfg.truby_writer.model.entities.Project;
import com.tfg.truby_writer.model.entities.User;

import com.tfg.truby_writer.model.daos.ProjectDao;
import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.services.User.UserService;

@Service
@Transactional
public class ProjectServiceImp implements ProjectService {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private UserService userService;

    @Override
    public Project createProject(User user, String name, String description) throws DuplicateInstanceException, InstanceNotFoundException{

        if (userService.findUserByUsername(user, user.getUsername()) == null) {
            throw new InstanceNotFoundException("user.entities.user", user.getId());
        }

        if (projectDao.existsByName(name)) {
            throw new DuplicateInstanceException("project.entities.project", name);
        }
        
        Project project = new Project();
        project.setUser(user);
        project.setName(name);
        project.setDescription(description);
        project.setCreatedAt(LocalDateTime.now());
        project.setModifiedAt(LocalDateTime.now());
        project.setPlots(null);
        project.setCharacters(null);
        projectDao.save(project);

        return project;
    }

    @Override
    public Project getProject(Long id) throws InstanceNotFoundException, InstanceNotFoundException {

        if (id == null) {
            throw new InstanceNotFoundException("project.entities.project", id);
        }
        Project project = projectDao.findById(id).orElseThrow(() -> new InstanceNotFoundException("project.entities.project", id));
        return project;
    }

    @Override
    public void deleteProject(User user, Long id) throws InstanceNotFoundException {
        Project project = projectDao.findById(id).orElseThrow(() -> new InstanceNotFoundException("project.entities.project", id));
        if (project.getUser().getId() != user.getId() || userService.findUserByUsername(user, user.getUsername()) == null) {
           throw new InstanceNotFoundException("user.entities.user", user.getId());
        }
        projectDao.deleteById(id);
    }

   

}