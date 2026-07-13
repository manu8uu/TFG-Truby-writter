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
import com.tfg.truby_writer.model.entities.Location;
import com.tfg.truby_writer.model.services.LocationsService;
import com.tfg.truby_writer.model.entities.LocationPoint;
import com.tfg.truby_writer.model.services.EstructureService;
import com.tfg.truby_writer.model.entities.Plot;
import com.tfg.truby_writer.model.services.Block;


import com.tfg.truby_writer.model.enums.Enums;
import com.tfg.truby_writer.model.enums.Enums.UserRole;



import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class LocationsServiceTest {

    @Autowired
    private EstructureService estructureService;
    @Autowired
    private UserService userService;
    
    @Autowired
    private LocationsService locationService;

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

    private Project createProject(User user) throws DuplicateInstanceException, InstanceNotFoundException{
        return estructureService.createProject(user, "project", "description");
    }

    
    private Plot createPlot(Project project) throws DuplicateInstanceException, InstanceNotFoundException{
        return estructureService.addPlot(project.getId(), "plot", "dramatic situation");  
    }

    @Test
    public void testAddLocation() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        Location location = locationService.addLocation(plot.getId(), "location", "backgroundHistory", "backgroundImageUrl");
        assertEquals(locationService.findLocationById(location.getId()), location);
        
    }

    @Test
    public void testAddLocationWithNonExistentPlot() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        assertThrows(InstanceNotFoundException.class, () -> locationService.addLocation(1L, "location", "backgroundHistory", "backgroundImageUrl"));
        
    }

    @Test
    public void testAddLocationWithDuplicateName() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        locationService.addLocation(plot.getId(), "location", "backgroundHistory", "backgroundImageUrl");
        assertThrows(DuplicateInstanceException.class, () -> locationService.addLocation(plot.getId(), "location", "backgroundHistory", "backgroundImageUrl"));
        
    }

    @Test
    public void testModifyLocation() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        Location location = locationService.addLocation(plot.getId(), "location", "backgroundHistory", "backgroundImageUrl");
        locationService.modifyLocation(location.getId(), "newLocation", "newBackgroundHistory", "newBackgroundImageUrl");
        assertEquals(locationService.findLocationById(location.getId()).getName(), "newLocation");
        
    }

    @Test
    public void testModifyLocationWithNonExistentLocation() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        assertThrows(InstanceNotFoundException.class, () -> locationService.modifyLocation(1L, "newLocation", "newBackgroundHistory", "newBackgroundImageUrl"));
        
    }

    @Test
    public void testDeleteLocation() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        Location location = locationService.addLocation(plot.getId(), "location", "backgroundHistory", "backgroundImageUrl");
        locationService.deleteLocation(location.getId());
        assertThrows(InstanceNotFoundException.class, () -> locationService.findLocationById(location.getId()));
        
    }

    @Test
    public void testDeleteLocationWithNonExistentLocation() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        assertThrows(InstanceNotFoundException.class, () -> locationService.deleteLocation(1L));
        
    }

    @Test
    public void testFindLocationsByFilter() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        Location location1 = locationService.addLocation(plot.getId(), "location1", "backgroundHistory", "backgroundImageUrl");
        Location location2 = locationService.addLocation(plot.getId(), "location2", "backgroundHistory", "backgroundImageUrl");
        Block<Location> block = locationService.findLocationsByFilter(plot.getId(), "");
        assertTrue(block.getItems().contains(location1));
        assertTrue(block.getItems().contains(location2));
        Block<Location> block2 = locationService.findLocationsByFilter(plot.getId(), "2");
        assertTrue(block2.getItems().contains(location2));
        assertFalse(block2.getItems().contains(location1));
    }

    @Test
    public void testAddLocationPoint() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        Location location = locationService.addLocation(plot.getId(), "location", "backgroundHistory", "backgroundImageUrl");
        LocationPoint locationPoint = locationService.addLocationPoint(location.getId(), "locationPoint", "description", 1.0f, 1.0f, Enums.MarkerType.CITY, "icon");
        assertEquals(locationService.findLocationPointById(locationPoint.getId()), locationPoint);
        
    }

    @Test void testFailAddLocationPointWithNonExistentPlot() throws DuplicateInstanceException, InstanceNotFoundException{

        assertThrows(InstanceNotFoundException.class, () -> locationService.addLocationPoint(1L, "locationPoint", "description", 1.0f, 1.0f, Enums.MarkerType.CITY, "icon"));

    }

    @Test 
    public void testFailAddLocationPointWithDuplicatedName() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        Location location = locationService.addLocation(plot.getId(), "location", "backgroundHistory", "backgroundImageUrl");
        locationService.addLocationPoint(location.getId(), "locationPoint", "description", 1.0f, 1.0f, Enums.MarkerType.CITY, "icon");
        assertThrows(DuplicateInstanceException.class, () -> locationService.addLocationPoint(location.getId(), "locationPoint", "description", 1.0f, 1.0f, Enums.MarkerType.CITY, "icon"));
        
    }

    @Test 
    public void testModifyLocationPoint() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        Location location = locationService.addLocation(plot.getId(), "location", "backgroundHistory", "backgroundImageUrl");
        LocationPoint locationPoint = locationService.addLocationPoint(location.getId(), "locationPoint", "description", 1.0f, 1.0f, Enums.MarkerType.CITY, "icon");
        locationService.modifyLocationPoint(locationPoint.getId(), "newLocationPoint", "newDescription", 2.0f, 2.0f, Enums.MarkerType.CITY, "icon");
        assertEquals(locationService.findLocationPointById(locationPoint.getId()).getName(), "newLocationPoint");
        
    }

    @Test
    public void testModifyLocationPointWithNonExistentLocationPoint() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        Location location = locationService.addLocation(plot.getId(), "location", "backgroundHistory", "backgroundImageUrl");
        assertThrows(InstanceNotFoundException.class, () -> locationService.modifyLocationPoint(1L, "newLocationPoint", "newDescription", 2.0f, 2.0f, Enums.MarkerType.CITY, "icon"));
        
    }

    @Test
    public void testDeleteLocationPoint() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        Location location = locationService.addLocation(plot.getId(), "location", "backgroundHistory", "backgroundImageUrl");
        LocationPoint locationPoint = locationService.addLocationPoint(location.getId(), "locationPoint", "description", 1.0f, 1.0f, Enums.MarkerType.CITY, "icon");
        assertNotNull(locationService.findLocationPointById(locationPoint.getId()));
        locationService.deleteLocationPoint(locationPoint.getId());
        assertThrows(InstanceNotFoundException.class, () -> locationService.findLocationPointById(locationPoint.getId()));
        
    }

    @Test
    public void testDeleteLocationPointWithNonExistentLocationPoint() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        Location location = locationService.addLocation(plot.getId(), "location", "backgroundHistory", "backgroundImageUrl");
        assertThrows(InstanceNotFoundException.class, () -> locationService.deleteLocationPoint(1L));
        
    }

    @Test
    public void testFindLocationPointById() throws DuplicateInstanceException, InstanceNotFoundException{
        
        User user = createUser("user");
        Project project = createProject(user);
        Plot plot = createPlot(project);
        Location location = locationService.addLocation(plot.getId(), "location", "backgroundHistory", "backgroundImageUrl");
        LocationPoint locationPoint = locationService.addLocationPoint(location.getId(), "locationPoint", "description", 1.0f, 1.0f, Enums.MarkerType.CITY, "icon");
        assertEquals(locationService.findLocationPointById(locationPoint.getId()), locationPoint);
        
    }







}