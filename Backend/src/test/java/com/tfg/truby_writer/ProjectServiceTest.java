package com.tfg.truby_writer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.entities.Project;
import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.services.Project.ProjectService;
import com.tfg.truby_writer.model.services.User.UserService;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    private User createUser(String userName) throws DuplicateInstanceException, InstanceNotFoundException{
		 User user = User.builder()
            .username(userName)
            .password("password")
            .email(userName + "@example.com")
			.blocked(false)
			.role(1)
            .build();

            userService.signUp(user);
            return user;

	}

    @Test
    public void testCreateProject() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = projectService.createProject(user, "project", "description");
        assertNotNull(projectService.getProject(project.getId()));
        
    }

    @Test
    public void testGetProject() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = projectService.createProject(user, "project", "description");
        assertNotNull(projectService.getProject(project.getId()));
        
    }

    @Test
    public void testGetProjectWithNonExistentId() throws DuplicateInstanceException, InstanceNotFoundException{
        
        assertThrows(InstanceNotFoundException.class, () -> projectService.getProject(1L));
        
    }


    @Test
    public void testDeleteProject() throws DuplicateInstanceException, InstanceNotFoundException{
            
        User user = createUser("user");
        Project project = projectService.createProject(user, "project", "description");
        assertNotNull(projectService.getProject(project.getId()));
        projectService.deleteProject(user, project.getId());
        assertThrows(InstanceNotFoundException.class, () -> projectService.getProject(project.getId()));
        
    }

    @Test
    public void testDeleteProjectWithNonExistentId() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        assertThrows(InstanceNotFoundException.class, () -> projectService.deleteProject(user, 1L));
        
    }

    
}
