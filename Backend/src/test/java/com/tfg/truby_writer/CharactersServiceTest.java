package com.tfg.truby_writer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.services.User.UserService;
import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.services.Estructure.EstructureService;
import com.tfg.truby_writer.model.services.Project.ProjectService;
import com.tfg.truby_writer.model.entities.Project;
import com.tfg.truby_writer.model.services.Character.CharacterService;
import com.tfg.truby_writer.model.entities.Character;
import com.tfg.truby_writer.model.services.Block;
import com.tfg.truby_writer.model.exceptions.IncorrectLoginException;
import com.tfg.truby_writer.model.exceptions.IncorrectPasswordException;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CharactersServiceTest {

    @Autowired
    private EstructureService estructureService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CharacterService characterService;

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

    @Test
    public void testAddCharacter() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Character character = characterService.addCharacter(project.getId(), "character", "description", "imageUrl");
        assertEquals(characterService.findCharacterByName(project.getId(), character.getName()), character);
    
    }

    @Test
    public void testAddCharacterWithNonExistentProject() throws DuplicateInstanceException, InstanceNotFoundException{
        
        assertThrows(InstanceNotFoundException.class, () -> characterService.addCharacter(1L, "character", "description", "imageUrl"));
        
    }

    @Test
    public void testAddCharacterWithDuplicateName() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        characterService.addCharacter(project.getId(), "character", "description", "imageUrl");
        assertThrows(DuplicateInstanceException.class, () -> characterService.addCharacter(project.getId(), "character", "description", "imageUrl"));
        
    }

    @Test
    public void testModifyCharacter() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Character character = characterService.addCharacter(project.getId(), "character", "description", "imageUrl");
        characterService.modifyCharacter(character.getId(), "newCharacter", "newDescription", "newImageUrl");
        assertEquals(characterService.findCharacterByName(project.getId(), "newCharacter"), character);
        
    }

    @Test
    public void testModifyCharacterWithNonExistentCharacter() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        createProject(user);
        assertThrows(InstanceNotFoundException.class, () -> characterService.modifyCharacter(1L, "newCharacter", "newDescription", "newImageUrl"));
        
    }

    @Test
    public void testDeleteCharacter() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Character character = characterService.addCharacter(project.getId(), "character", "description", "imageUrl");
        characterService.deleteCharacter(character.getId());
        assertThrows(InstanceNotFoundException.class, () -> characterService.getCharacterById(character.getId()));
        
    }

    @Test
    public void testDeleteCharacterWithNonExistentCharacter() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        createProject(user);
        assertThrows(InstanceNotFoundException.class, () -> characterService.deleteCharacter(1L));
        
    }

    @Test 
    public void testFindCharactersByFilter() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Character character1 = characterService.addCharacter(project.getId(), "character1", "description", "imageUrl");
        Character character2 = characterService.addCharacter(project.getId(), "character2", "description", "imageUrl");
        Block<Character> block = characterService.findCharactersByFilter(project.getId(), "");
        assertTrue(block.getItems().contains(character1));
        assertTrue(block.getItems().contains(character2));
        Block<Character> block2 = characterService.findCharactersByFilter(project.getId(), "2");
        assertTrue(block2.getItems().contains(character2));
        assertFalse(block2.getItems().contains(character1));
    }

    
}
