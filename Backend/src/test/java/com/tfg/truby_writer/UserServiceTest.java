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
import com.tfg.truby_writer.model.exceptions.IncorrectLoginException;
import com.tfg.truby_writer.model.exceptions.IncorrectPasswordException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {
	
	private final Long NON_EXISTENT_ID = Long.valueOf(-1);
	
	@Autowired
	private UserService userService;
	
	private User createUser(String userName) {
		return User.builder()
            .username(userName)
            .password("password")
            .email(userName + "@example.com")
			.blocked(false)
			.role(0)
            .build();
	}

	private User createAdminUser(String userName) {
		User admin = createUser(userName);
		admin.setRole(1);
		return admin;
	}
	
	@Test
	public void testSignUpAndLoginFromId() throws DuplicateInstanceException, InstanceNotFoundException {
		
		User user = createUser("user");
		userService.signUp(user);
		User loggedInUser = userService.loginFromId(user.getId());
		assertEquals(user, loggedInUser);
		
	}
	
	@Test
	public void testSignUpDuplicatedUserName() throws DuplicateInstanceException {
		
		User user = createUser("user");
		userService.signUp(user);
		assertThrows(DuplicateInstanceException.class, () -> userService.signUp(user));
		
	}
	
	@Test
	public void testLoginFromNonExistentId() {
		assertThrows(InstanceNotFoundException.class, () -> userService.loginFromId(NON_EXISTENT_ID));
	}
	
	@Test
	public void testLogin() throws DuplicateInstanceException, IncorrectLoginException {
		
		User user = createUser("user");
		String clearPassword = user.getPassword();
		userService.signUp(user);
		User loggedInUser = userService.login(user.getUsername(), clearPassword);
		assertEquals(user, loggedInUser);
		
	}
	
	@Test
	public void testLoginWithIncorrectPassword() throws DuplicateInstanceException {
		
		User user = createUser("user");
		String clearPassword = user.getPassword();
		userService.signUp(user);
		assertThrows(IncorrectLoginException.class, () ->
			userService.login(user.getUsername(), 'X' + clearPassword));
		
	}
	
	@Test
	public void testLoginWithNonExistentUserName() {
		assertThrows(IncorrectLoginException.class, () -> userService.login("X", "Y"));
	}
	

	@Test
	public void testChangePassword() throws DuplicateInstanceException, InstanceNotFoundException,
		IncorrectPasswordException, IncorrectLoginException {
		
		User user = createUser("user");
		String oldPassword = user.getPassword();
		String newPassword = 'X' + oldPassword;
		userService.signUp(user);
		userService.changePassword(user.getId(), oldPassword, newPassword);
		userService.login(user.getUsername(), newPassword);
		
	}
	
	@Test
	public void testChangePasswordWithNonExistentId() {

		assertThrows(InstanceNotFoundException.class, () ->
			userService.changePassword(NON_EXISTENT_ID, "X", "Y"));
	}
	
	@Test
	public void testChangePasswordWithIncorrectPassword() throws DuplicateInstanceException {
		
		User user = createUser("user");
		String oldPassword = user.getPassword();
		String newPassword = 'X' + oldPassword;
		userService.signUp(user);
		assertThrows(IncorrectPasswordException.class, () ->
			userService.changePassword(user.getId(), 'Y' + oldPassword, newPassword));
		
	}

	@Test
	public void testChangePasswordWithIncorrectOldPassword() throws DuplicateInstanceException {
		
		User user = createUser("user");
		String oldPassword = user.getPassword();
		String newPassword = 'X' + oldPassword;
		userService.signUp(user);
		assertThrows(IncorrectPasswordException.class, () ->
			userService.changePassword(user.getId(), 'Y' + oldPassword, newPassword));
		
	}

	// ADMIN

	

	@Test
	public void testBlockUser() throws DuplicateInstanceException, InstanceNotFoundException {
				
		User admin = createAdminUser("admin");
		User user = createUser("user2");
		userService.signUp(admin);
		userService.signUp(user);
		assertFalse(user.getBlocked());
		assertTrue(userService.blockUser(admin, user.getId()));
		assertTrue(user.getBlocked());
		
	}
	
	@Test
	public void testBlockUserWithNonExistentId() {

		assertThrows(InstanceNotFoundException.class, () -> userService.blockUser(createAdminUser("admin"), NON_EXISTENT_ID));
	}

	@Test
	public void testSearchUsersByFilter() throws DuplicateInstanceException, InstanceNotFoundException {
		
		User admin = createAdminUser("admin");
		userService.signUp(admin);
		User user = createUser("user");
		userService.signUp(user);
		userService.blockUser(admin, user.getId());
		User user2 = createUser("user2");
		userService.signUp(user2);
		User user3 = createUser("user3");
		userService.signUp(user3);
		assertTrue(userService.searchUsersByFilter(admin, null, null).getItems().size() == 4);
		assertTrue(userService.searchUsersByFilter(admin, null,  "user").getItems().size() == 3);
		assertTrue(userService.searchUsersByFilter(admin, false, null).getItems().size() == 3);
		assertTrue(userService.searchUsersByFilter(admin, true, null).getItems().size() == 1);
		
	}

	@Test
	public void testFindUserByUsername() throws DuplicateInstanceException, InstanceNotFoundException {
		
		User user = createUser("user");
		userService.signUp(user);
		assertEquals(user, userService.findUserByUsername(createAdminUser("admin"), user.getUsername()));
		
	}

	@Test
	public void testFindUserByUsernameWithNonExistentUsername() {
		
		assertThrows(InstanceNotFoundException.class, () -> userService.findUserByUsername(createAdminUser("admin"), "X"));
	}

}
