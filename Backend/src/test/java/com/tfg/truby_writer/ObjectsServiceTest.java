package com.tfg.truby_writer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.services.UserService;
import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.entities.Project;

import com.tfg.truby_writer.model.services.EstructureService;
import com.tfg.truby_writer.model.entities.LineTime;
import com.tfg.truby_writer.model.entities.Event;
import com.tfg.truby_writer.model.services.Block;
import com.tfg.truby_writer.model.entities.Plot;
import com.tfg.truby_writer.model.entities.Character;
import com.tfg.truby_writer.model.services.ObjectsService;

import com.tfg.truby_writer.model.enums.Enums;
import com.tfg.truby_writer.model.enums.Enums.UserRole;




import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ObjectsServiceTest {

    @Autowired
    private EstructureService estructureService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectsService objectsService;


     private User createUser(String userName) throws DuplicateInstanceException, InstanceNotFoundException{
		 User user = User.builder()
            .username(userName)
            .password("password")
            .email(userName + "@example.com")
			.blocked(false)
			.role(Enums.UserRole.USER)
            .build();

            userService.signUp(user);
            return user;

	}


    private Project createProject(User user) throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        return estructureService.createProject(user, "project", "description");
    }

    
    private Plot createPlot(Project project) throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        return estructureService.addPlot(project.getId(), "plot", "dramatic situation");  
    }


   
    @Test
    public void testAddCharacter() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        User user = createUser("user");
        Project project = createProject(user);
        Character character = objectsService.addCharacter(project.getId(), "character", "description", "imageUrl");
        assertEquals(objectsService.findCharacterByName(project.getId(), character.getName()), character);
    
    }

    @Test
    public void testAddCharacterWithNonExistentProject() throws DuplicateInstanceException, InstanceNotFoundException{
        
        assertThrows(InstanceNotFoundException.class, () -> objectsService.addCharacter(1L, "character", "description", "imageUrl"));
        
    }

    @Test
    public void testAddCharacterWithDuplicateName() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        User user = createUser("user");
        Project project = createProject(user);
        objectsService.addCharacter(project.getId(), "character", "description", "imageUrl");
        assertThrows(DuplicateInstanceException.class, () -> objectsService.addCharacter(project.getId(), "character", "description", "imageUrl"));
        
    }

    @Test
    public void testModifyCharacter() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        User user = createUser("user");
        Project project = createProject(user);
        Character character = objectsService.addCharacter(project.getId(), "character", "description", "imageUrl");
        objectsService.modifyCharacter(character.getId(), "newCharacter", "newDescription", "newImageUrl");
        assertEquals(objectsService.findCharacterByName(project.getId(), "newCharacter"), character);
        
    }

    @Test
    public void testModifyCharacterWithNonExistentCharacter() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        User user = createUser("user");
        createProject(user);
        assertThrows(InstanceNotFoundException.class, () -> objectsService.modifyCharacter(1L, "newCharacter", "newDescription", "newImageUrl"));
        
    }

    @Test
    public void testDeleteCharacter() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        User user = createUser("user");
        Project project = createProject(user);
        Character character = objectsService.addCharacter(project.getId(), "character", "description", "imageUrl");
        objectsService.deleteCharacter(character.getId());
        assertThrows(InstanceNotFoundException.class, () -> objectsService.getCharacterById(character.getId()));
        
    }

    @Test
    public void testDeleteCharacterWithNonExistentCharacter() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        User user = createUser("user");
        createProject(user);
        assertThrows(InstanceNotFoundException.class, () -> objectsService.deleteCharacter(1L));
        
    }

    @Test 
    public void testFindCharactersByFilter() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        User user = createUser("user");
        Project project = createProject(user);
        Character character1 = objectsService.addCharacter(project.getId(), "character1", "description", "imageUrl");
        Character character2 = objectsService.addCharacter(project.getId(), "character2", "description", "imageUrl");
        Block<Character> block = objectsService.findCharactersByFilter(project.getId(), "");
        assertTrue(block.getItems().contains(character1));
        assertTrue(block.getItems().contains(character2));
        Block<Character> block2 = objectsService.findCharactersByFilter(project.getId(), "2");
        assertTrue(block2.getItems().contains(character2));
        assertFalse(block2.getItems().contains(character1));
    }



    @Test
    public void testAddEvent() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = objectsService.getLineTimeByPlot(plot.getId());
        Event event = objectsService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        Block<Event> block = objectsService.findEventsByName(lineTime.getId(), "event");
        assertTrue(block.getItems().contains(event));
        
    }

    @Test
    public void testFailAddEventBySameTitle() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = objectsService.getLineTimeByPlot(plot.getId());
        Event event = objectsService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        assertThrows(DuplicateInstanceException.class, () -> objectsService.addEvent(lineTime.getId(), "event", "description", 1, 1));
        
    }

    @Test
    public void testFailAddEventByNotFoundTimeline() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        assertThrows(InstanceNotFoundException.class, () -> objectsService.addEvent(1000L, "event", "description", 1, 1));
        
    }

    @Test
    public void testModifyEvent() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = objectsService.getLineTimeByPlot(plot.getId());
        Event event = objectsService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        Event modifiedEvent = objectsService.modifyEvent(event.getId(), "event modified", "description modified", 1, 1);
        assertEquals(event.getId(), modifiedEvent.getId());
        assertEquals("event modified", modifiedEvent.getTitle());
        assertEquals("description modified", modifiedEvent.getContent());
        assertEquals(1, modifiedEvent.getChapterNumber().intValue());
        assertEquals(1, modifiedEvent.getChronoOrder().intValue());
        
    }

    @Test
    public void testFailModifyEventByNotFound() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        assertThrows(InstanceNotFoundException.class, () -> objectsService.modifyEvent(1000L, "event", "description", 1, 1));
        
    }

    @Test
    public void testDeleteEvent() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = objectsService.getLineTimeByPlot(plot.getId());
        Event event = objectsService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        objectsService.deleteEvent(event.getId());
        Block<Event> block = objectsService.findEventsByName(lineTime.getId(), "event");
        assertFalse(block.getItems().contains(event));
        
    }

    @Test
    public void testFailDeleteEventByNotFound() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        
        assertThrows(InstanceNotFoundException.class, () -> objectsService.deleteEvent(1000L));
        
    }

    @Test
    public void testFindEventsByFilter() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = objectsService.getLineTimeByPlot(plot.getId());
        Event event = objectsService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        Block<Event> block = objectsService.findEventsByName(lineTime.getId(), "event");
        assertTrue(block.getItems().contains(event));
        
    }

    @Test
    public void testFindEventsByFilterByTitle() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = objectsService.getLineTimeByPlot(plot.getId());
        Event event = objectsService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        Block<Event> block = objectsService.findEventsByName(lineTime.getId(), "event");
        assertTrue(block.getItems().contains(event));
        
    }

   /* @Test
    public void testFindEventsByFilterByChapterNumber() throws DuplicateInstanceException, InstanceNotFoundException, Exception{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = objectsService.getLineTimeByPlot(plot.getId());
        Event event = objectsService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        Block<Event> block = objectsService.findLineTimesByFilter(lineTime.getId(), true, 0 , 10);
        assertTrue(block.getItems().contains(event));
        
    }

    @Test
    public void testFindEventsByFilterByChronoOrder() throws DuplicateInstanceException, InstanceNotFoundException , Exception{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        LineTime lineTime = objectsService.getLineTimeByPlot(plot.getId());
        Event event = objectsService.addEvent(lineTime.getId(), "event", "description", 1, 1);
        Block<Event> block = objectsService.findLineTimesByFilter(lineTime.getId(), false, 0 ,10);
        assertTrue(block.getItems().contains(event));
        
    }
*/


}