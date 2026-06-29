package com.tfg.truby_writer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.services.User.UserService;
import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.services.Project.ProjectService;
import com.tfg.truby_writer.model.entities.Project;

import com.tfg.truby_writer.model.services.Estructure.EstructureService;
import com.tfg.truby_writer.model.entities.LineTime;
import com.tfg.truby_writer.model.entities.Event;
import com.tfg.truby_writer.model.services.Block;
import com.tfg.truby_writer.model.services.Events.EventService;
import com.tfg.truby_writer.model.entities.Plot;



import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EventsServiceTest {

    @Autowired
    private EstructureService estructureService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private EventService eventService;


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

    private Project createProject(User user) throws DuplicateInstanceException, InstanceNotFoundException{
        return projectService.createProject(user, "project", "description");
    }

    
    private Plot createPlot(Project project) throws DuplicateInstanceException, InstanceNotFoundException{
        return estructureService.addPlot(project.getId(), "plot", "dramatic situation");  
    }


    @Test
    public void testAddEvent() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = eventService.getLineTimeByPlot(plot.getId());
        Event event = eventService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        Block<Event> block = eventService.findEventsByFilter(lineTime.getId(), "", null, null);
        assertTrue(block.getItems().contains(event));
        
    }

    @Test
    public void testFailAddEventBySameTitle() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = eventService.getLineTimeByPlot(plot.getId());
        Event event = eventService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        assertThrows(DuplicateInstanceException.class, () -> eventService.addEvent(lineTime.getId(), "event", "description", 1, 1));
        
    }

    @Test
    public void testFailAddEventByNotFoundTimeline() throws DuplicateInstanceException, InstanceNotFoundException{
        
        assertThrows(InstanceNotFoundException.class, () -> eventService.addEvent(1000L, "event", "description", 1, 1));
        
    }

    @Test
    public void testModifyEvent() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = eventService.getLineTimeByPlot(plot.getId());
        Event event = eventService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        Event modifiedEvent = eventService.modifyEvent(event.getId(), "event modified", "description modified", 1, 1);
        assertEquals(event.getId(), modifiedEvent.getId());
        assertEquals("event modified", modifiedEvent.getTitle());
        assertEquals("description modified", modifiedEvent.getContent());
        assertEquals(1, modifiedEvent.getChapterNumber().intValue());
        assertEquals(1, modifiedEvent.getChronoOrder().intValue());
        
    }

    @Test
    public void testFailModifyEventByNotFound() throws DuplicateInstanceException, InstanceNotFoundException{
        
        assertThrows(InstanceNotFoundException.class, () -> eventService.modifyEvent(1000L, "event", "description", 1, 1));
        
    }

    @Test
    public void testDeleteEvent() throws DuplicateInstanceException, InstanceNotFoundException{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = eventService.getLineTimeByPlot(plot.getId());
        Event event = eventService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        eventService.deleteEvent(event.getId());
        Block<Event> block = eventService.findEventsByFilter(lineTime.getId(), "", null, null);
        assertFalse(block.getItems().contains(event));
        
    }

    @Test
    public void testFailDeleteEventByNotFound() throws DuplicateInstanceException, InstanceNotFoundException{
        
        assertThrows(InstanceNotFoundException.class, () -> eventService.deleteEvent(1000L));
        
    }

    @Test
    public void testFindEventsByFilter() throws DuplicateInstanceException, InstanceNotFoundException{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = eventService.getLineTimeByPlot(plot.getId());
        Event event = eventService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        Block<Event> block = eventService.findEventsByFilter(lineTime.getId(), "", null, null);
        assertTrue(block.getItems().contains(event));
        
    }

    @Test
    public void testFindEventsByFilterByTitle() throws DuplicateInstanceException, InstanceNotFoundException{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = eventService.getLineTimeByPlot(plot.getId());
        Event event = eventService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        Block<Event> block = eventService.findEventsByFilter(lineTime.getId(), "event", null, null);
        assertTrue(block.getItems().contains(event));
        
    }

    @Test
    public void testFindEventsByFilterByChapterNumber() throws DuplicateInstanceException, InstanceNotFoundException{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = eventService.getLineTimeByPlot(plot.getId());
        Event event = eventService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        Block<Event> block = eventService.findEventsByFilter(lineTime.getId(), "", 1, null);
        assertTrue(block.getItems().contains(event));
        
    }

    @Test
    public void testFindEventsByFilterByChronoOrder() throws DuplicateInstanceException, InstanceNotFoundException{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = eventService.getLineTimeByPlot(plot.getId());
        Event event = eventService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        Block<Event> block = eventService.findEventsByFilter(lineTime.getId(), "", null, 1);
        assertTrue(block.getItems().contains(event));
        
    }



}