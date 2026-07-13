package com.tfg.truby_writer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.exceptions.ProjectPermissionException;
import com.tfg.truby_writer.model.services.UserService;
import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.entities.Project;
import com.tfg.truby_writer.model.entities.Character;
import com.tfg.truby_writer.model.services.EstructureService;
import com.tfg.truby_writer.model.entities.Plot;
import com.tfg.truby_writer.model.entities.Premise;
import com.tfg.truby_writer.model.entities.NetworkNode;
import com.tfg.truby_writer.model.entities.NetworkRelationship;
import com.tfg.truby_writer.model.services.ObjectsService;

import com.tfg.truby_writer.model.enums.Enums;
import com.tfg.truby_writer.model.enums.Enums.UserRole;



import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;



@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EstructureServiceTest {

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


    @Test
    public void testCreateProject() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = estructureService.createProject(user, "project", "description");
        assertNotNull(estructureService.getProject(project.getId()));
        
    }

    @Test
    public void testGetProject() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = estructureService.createProject(user, "project", "description");
        assertNotNull(estructureService.getProject(project.getId()));
        
    }

    @Test
    public void testGetProjectWithNonExistentId() throws DuplicateInstanceException, InstanceNotFoundException{
        
        assertThrows(InstanceNotFoundException.class, () -> estructureService.getProject(1L));
        
    }


    @Test
    public void testDeleteProject() throws DuplicateInstanceException, InstanceNotFoundException, ProjectPermissionException{
            
        User user = createUser("user");
        Project project = estructureService.createProject(user, "project", "description");
        assertNotNull(estructureService.getProject(project.getId()));
        estructureService.deleteProject(user, project.getId());
        assertThrows(InstanceNotFoundException.class, () -> estructureService.getProject(project.getId()));
        
    }

    @Test
    public void testDeleteProjectWithNonExistentId() throws DuplicateInstanceException, InstanceNotFoundException, ProjectPermissionException{
        
        User user = createUser("user");
        assertThrows(InstanceNotFoundException.class, () -> estructureService.deleteProject(user, 1L));
        
    }

    @Test
    public void testTrytoDeleteProjectFromAnotherUser() throws DuplicateInstanceException, InstanceNotFoundException, ProjectPermissionException{
        
        User user = createUser("user");
        User user2 = createUser("user2");
        Project project = estructureService.createProject(user, "project", "description");
        assertThrows(ProjectPermissionException.class, () -> estructureService.deleteProject(user2, project.getId()));
        
    }


    private Project createProject(User user) throws DuplicateInstanceException, InstanceNotFoundException{
        return estructureService.createProject(user, "project", "description");
    }

    @Test
    public void testAddPlot() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        assertEquals(estructureService.findPlotById(plot.getId()), plot);
        assertNotNull(estructureService.findPlotByName(project.getId(), plot.getName()));
        
    }

    @Test
    public void testFindPlotByName() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        assertEquals(estructureService.findPlotByName(project.getId(), plot.getName()), plot);
        
    }
    @Test
    public void testFailAddPlotWithDuplicatedName() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        assertThrows(DuplicateInstanceException.class, () -> estructureService.addPlot(project.getId(), "plot", "dramatic situation"));
        
    }

    @Test
    public void testModifyPlot() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        estructureService.modifyPlot(plot.getId(), "plot2", "dramatic situation2", "struct weakness need", "struct desire", "struct adversary", "struct plan", "struct struggle", "struct self-revelation", "struct new equilibrium");
        assertEquals(estructureService.findPlotById(plot.getId()).getName(), "plot2");
        
    }

    @Test
    public void testDeletePlot() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        estructureService.deletePlot(plot.getId());
        assertThrows(InstanceNotFoundException.class, () -> estructureService.findPlotById(plot.getId()));
        
    }

    //PREMISE
    
    @Test
    public void testAddPremise() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot =estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        Premise premise = estructureService.addPremise(plot.getId(), "uno");
        assertNotNull(estructureService.findPremiseById(premise.getId()));
        
    }

    @Test
    public void testFindPremiseByIdWithNonExistentId() throws DuplicateInstanceException, InstanceNotFoundException{
        
        assertThrows(InstanceNotFoundException.class, () -> estructureService.findPremiseById(1L));
        
    }

    @Test
    public void testModifyPremise() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot =estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        Premise premise = estructureService.addPremise(plot.getId(), "uno");
        estructureService.modifyPremise(premise.getId(), "dos", "narrative possibilities", "narrative challenges", "problems", "founding principle", "conflict", "moral decision");
        assertEquals(estructureService.findPremiseById(premise.getId()).getPremise(), "dos");

    }

    @Test
    public void testModifyPremiseWithNonExistentId() throws DuplicateInstanceException, InstanceNotFoundException{
        
        assertThrows(InstanceNotFoundException.class, () -> estructureService.modifyPremise(1L, "dos", "narrative possibilities", "narrative challenges", "problems", "founding principle", "conflict", "moral decision"));
        
    }

    @Test
    public void testDeletePremise() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot =estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        Premise premise = estructureService.addPremise(plot.getId(), "uno");
        assertNotNull(estructureService.findPremisesByFilterName(project.getId(), "uno"));
        estructureService.deletePremise(premise.getId());
        assertThrows(InstanceNotFoundException.class, () -> estructureService.findPremiseById(premise.getId()));
        
    }

    @Test 
    public void testDeletePremiseWithNonExistentId() throws DuplicateInstanceException, InstanceNotFoundException{
        
        assertThrows(InstanceNotFoundException.class, () -> estructureService.deletePremise(1L));
        
    }

    @Test
    public void testFindPremiseByFIlterName() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot =estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        estructureService.addPremise(plot.getId(), "uno");
        estructureService.addPremise(plot.getId(), "dos");
        estructureService.addPremise(plot.getId(), "tres");
        assertTrue(estructureService.findPremisesByFilterName(plot.getId(), "uno").getItems().size() == 1);
        assertTrue(estructureService.findPremisesByFilterName(plot.getId(), "").getItems().size() == 3);
        assertTrue(estructureService.findPremisesByFilterName(plot.getId(), "o").getItems().size() == 2);
   
    }

    // NETWORK

    @Test
    public void testAddCharactertoToNetwork() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot =estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        Character character = objectsService.addCharacter(project.getId(), "character", "description", "imageUrl");
        estructureService.addCharactertoNetwork(plot.getId(), character.getId(), Enums.Role.PRINCIPAL);
        assertNotNull(estructureService.findCharacterNetworkByCharacterId(plot.getNetwork().getId(), character.getId()));
        
    }

    @Test
    public void testAddCharactertoToNetworkWithNonExistentData() throws DuplicateInstanceException, InstanceNotFoundException{

        assertThrows(InstanceNotFoundException.class, () -> estructureService.addCharactertoNetwork(1L, 1L, Enums.Role.PRINCIPAL));
        
    }

    @Test
    public void testFindCharacterNetworkByCharacterIdWithNonExistentData() throws DuplicateInstanceException, InstanceNotFoundException{

        assertThrows(InstanceNotFoundException.class, () -> estructureService.findCharacterNetworkByCharacterId(1L, 1L));
        
    }
    @Test 
    public void testFailAddCharactertoNetworkWithDuplicatedCharacterId() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot =estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        Character character = objectsService.addCharacter(project.getId(), "character", "description", "imageUrl");
        estructureService.addCharactertoNetwork(plot.getId(), character.getId(), Enums.Role.PRINCIPAL);
        assertThrows(DuplicateInstanceException.class, () -> estructureService.addCharactertoNetwork(plot.getId(), character.getId(), Enums.Role.PRINCIPAL));
        
    }

    @Test
    public void testModifyCharacterRoleInNetwork() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot =estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        Character character = objectsService.addCharacter(project.getId(), "character", "description", "imageUrl");
        estructureService.addCharactertoNetwork(plot.getId(), character.getId(), Enums.Role.PRINCIPAL);
        assertEquals(estructureService.findCharacterNetworkByCharacterId(plot.getNetwork().getId(), character.getId()).getRole(), Enums.Role.PRINCIPAL);
        estructureService.modifyCharacterRoleInNetwork(plot.getId(), character.getId(), Enums.Role.SUPPORTING_CHARACTER);
        assertEquals(estructureService.findCharacterNetworkByCharacterId(plot.getNetwork().getId(), character.getId()).getRole(), Enums.Role.SUPPORTING_CHARACTER);
        
    }

    @Test
    public void testModifyCharacterRoleInNetworkWithNonExistentData() throws DuplicateInstanceException, InstanceNotFoundException{

        assertThrows(InstanceNotFoundException.class, () -> estructureService.modifyCharacterRoleInNetwork(1L, 1L, Enums.Role.PRINCIPAL));
        
    }

    @Test
    public void testDeleteCharacterfromNetwork() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot =estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        Character character = objectsService.addCharacter(project.getId(), "character", "description", "imageUrl");
        estructureService.addCharactertoNetwork(plot.getId(), character.getId(), Enums.Role.PRINCIPAL);
        assertNotNull(estructureService.findCharacterNetworkByCharacterId(plot.getNetwork().getId(), character.getId()));
        estructureService.deleteCharacterfromNetwork(plot.getId(), character.getId());
        assertThrows(InstanceNotFoundException.class, () -> estructureService.findCharacterNetworkByCharacterId(plot.getNetwork().getId(), character.getId()));
        
    }

    @Test
    public void testDeleteCharacterfromNetworkWithNonExistentData() throws DuplicateInstanceException, InstanceNotFoundException{

        assertThrows(InstanceNotFoundException.class, () -> estructureService.deleteCharacterfromNetwork(1L, 1L));
        
    }   

    @Test
    public void testAddRelationshiptoNetwork() throws DuplicateInstanceException, InstanceNotFoundException{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        Character character = objectsService.addCharacter(project.getId(), "character", "description", "imageUrl");
        NetworkNode networkNode1 = estructureService.addCharactertoNetwork(plot.getId(), character.getId(), Enums.Role.PRINCIPAL);
        Character character2 = objectsService.addCharacter(project.getId(), "character2", "description2", "imageUrl2");
        NetworkNode networkNode2 = estructureService.addCharactertoNetwork(plot.getId(), character2.getId(), Enums.Role.PRINCIPAL);
        
        NetworkRelationship relationship = estructureService.addRelationshiptoNetwork(plot.getId(), character.getId(), character2.getId(), Enums.RelationshipType.ALLY);
        assertEquals(estructureService.findRelationshipByNodeFromAndNodeTo(networkNode1.getId(), networkNode2.getId()), relationship);
    }


    @Test
    public void testAddRelationshiptoNetworkWithNonExistentData() throws DuplicateInstanceException, InstanceNotFoundException{

        assertThrows(InstanceNotFoundException.class, () -> estructureService.addRelationshiptoNetwork(1L, 1L, 1L, Enums.RelationshipType.ALLY));
        
    }

    @Test
    public void testFailAddRelationshiptoNetworkWithDuplicatedNodeFrom() throws DuplicateInstanceException, InstanceNotFoundException{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        Character character = objectsService.addCharacter(project.getId(), "character", "description", "imageUrl");
        estructureService.addCharactertoNetwork(plot.getId(), character.getId(), Enums.Role.PRINCIPAL);
        Character character2 = objectsService.addCharacter(project.getId(), "character2", "description2", "imageUrl2");
        estructureService.addCharactertoNetwork(plot.getId(), character2.getId(), Enums.Role.PRINCIPAL);
        
        estructureService.addRelationshiptoNetwork(plot.getId(), character.getId(), character2.getId(), Enums.RelationshipType.ALLY);
        assertThrows(DuplicateInstanceException.class, () -> estructureService.addRelationshiptoNetwork(plot.getId(), character.getId(), character2.getId(), Enums.RelationshipType.ALLY));
    }

    @Test
    public void testFindRelationshipByNodeFromAndNodeToWithNonExistentData() throws DuplicateInstanceException, InstanceNotFoundException{    
        assertThrows(InstanceNotFoundException.class, () -> estructureService.findRelationshipByNodeFromAndNodeTo(1L, 1L));
    }

    @Test
    public void testModifyRelationship() throws DuplicateInstanceException, InstanceNotFoundException{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        Character character = objectsService.addCharacter(project.getId(), "character", "description", "imageUrl");
        estructureService.addCharactertoNetwork(plot.getId(), character.getId(), Enums.Role.PRINCIPAL);
        Character character2 = objectsService.addCharacter(project.getId(), "character2", "description2", "imageUrl2");
        estructureService.addCharactertoNetwork(plot.getId(), character2.getId(), Enums.Role.PRINCIPAL);
        
        NetworkRelationship relationship = estructureService.addRelationshiptoNetwork(plot.getId(), character.getId(), character2.getId(), Enums.RelationshipType.ALLY);
        assertEquals(relationship.getRelationship(), Enums.RelationshipType.ALLY);
        relationship.setRelationship(Enums.RelationshipType.ADVERSARY);
        
        estructureService.modifyRelationship(plot.getId(), character.getId(), character2.getId(), relationship.getRelationship());
        assertEquals(relationship.getRelationship(), Enums.RelationshipType.ADVERSARY);
    }

    @Test
    public void testModifyRelationshipWithNonExistentData() throws DuplicateInstanceException, InstanceNotFoundException{
        
        assertThrows(InstanceNotFoundException.class, () -> estructureService.modifyRelationship(1L, 1L, 1L, Enums.RelationshipType.ALLY));
        
    }

    @Test
    public void testDeleteRelationship() throws DuplicateInstanceException, InstanceNotFoundException{
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = estructureService.addPlot(project.getId(), "plot", "dramatic situation");
        Character character = objectsService.addCharacter(project.getId(), "character", "description", "imageUrl");
        NetworkNode networkNode1 = estructureService.addCharactertoNetwork(plot.getId(), character.getId(), Enums.Role.PRINCIPAL);
        Character character2 = objectsService.addCharacter(project.getId(), "character2", "description2", "imageUrl2");
        NetworkNode networkNode2 = estructureService.addCharactertoNetwork(plot.getId(), character2.getId(), Enums.Role.PRINCIPAL);
        
        NetworkRelationship relationship = estructureService.addRelationshiptoNetwork(plot.getId(), character.getId(), character2.getId(), Enums.RelationshipType.ALLY);
        assertEquals(relationship.getRelationship(), Enums.RelationshipType.ALLY);
        
        estructureService.deleteRelationship(plot.getId(), character.getId(), character2.getId());
        assertThrows(InstanceNotFoundException.class, () -> estructureService.findRelationshipByNodeFromAndNodeTo(networkNode1.getId(), networkNode2.getId()));
    }
    
  



} 

    

    


