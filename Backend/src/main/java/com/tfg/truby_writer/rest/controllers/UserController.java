package com.tfg.truby_writer.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.context.MessageSource;

import com.tfg.truby_writer.model.entities.User;
import com.tfg.truby_writer.model.exceptions.DuplicateInstanceException;
import com.tfg.truby_writer.model.exceptions.IncorrectLoginException;
import com.tfg.truby_writer.model.exceptions.IncorrectPasswordException;
import com.tfg.truby_writer.model.exceptions.InstanceNotFoundException;
import com.tfg.truby_writer.model.exceptions.PermissionException;
import com.tfg.truby_writer.model.services.UserService;
import com.tfg.truby_writer.model.services.Block;
import com.tfg.truby_writer.rest.common.JwtGenerator;
import com.tfg.truby_writer.rest.common.JwtInfo;
import com.tfg.truby_writer.rest.dtos.*;

import java.net.URI;
import java.util.Locale;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final String INCORRECT_LOGIN_EXCEPTION_CODE = "project.exceptions.IncorrectLoginException";
    private static final String INCORRECT_PASS_EXCEPTION_CODE = "project.exceptions.IncorrectPasswordException";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtGenerator jwtGenerator;

    @ExceptionHandler(IncorrectLoginException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsDto handleIncorrectLoginException(IncorrectLoginException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(INCORRECT_LOGIN_EXCEPTION_CODE, null,
                INCORRECT_LOGIN_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsDto handleIncorrectPasswordException(IncorrectPasswordException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(INCORRECT_PASS_EXCEPTION_CODE, null,
                INCORRECT_PASS_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @PostMapping("/signUp")
    public ResponseEntity<AuthenticatedUserDto> signUp(
        @Validated({UserDto.AllValidations.class}) @RequestBody UserDto userDto) 
        throws DuplicateInstanceException {
       
        User user = UserConversor.toUser(userDto);
        userService.signUp(user);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).body(generateAuthenticatedUserDto(user));
    }

    @PostMapping("/login")
    public AuthenticatedUserDto login(@Validated @RequestBody LoginParamsDto params) 
        throws IncorrectLoginException {
        User user = userService.login(params.getUserName(), params.getPassword());
        return generateAuthenticatedUserDto(user);
    }

    @PostMapping("/loginFromId")
    public AuthenticatedUserDto loginFromId(@RequestAttribute Long userId) 
        throws InstanceNotFoundException {
        User user = userService.loginFromId(userId);
        return generateAuthenticatedUserDto(user);
    }

    @PostMapping("/{id}/changePassword")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            @RequestAttribute Long userId, 
            @PathVariable Long id,
            @Validated @RequestBody ChangePasswordParamsDto params)
            throws PermissionException, InstanceNotFoundException, IncorrectPasswordException {

        if (!id.equals(userId)) {
            throw new PermissionException();
        }
        userService.changePassword(id, params.getOldPassword(), params.getNewPassword());
    }

    @PostMapping("/loginFromServiceToken")
    public AuthenticatedUserDto loginFromServiceToken(@RequestAttribute Long userId) 
        throws InstanceNotFoundException {
        User user = userService.loginFromId(userId);
        return generateAuthenticatedUserDto(user);
    }

    // ==========================================
    //                  ADMIN
    // ==========================================

    @GetMapping("/search")
    public BlockDto<UserDto> searchUsersByFilter(
        @RequestAttribute Long userId,
        @RequestParam(required = false) Boolean blocked,
        @RequestParam(required = false) String filter) throws InstanceNotFoundException {

        User authenticatedUser = userService.checkUser(userId);
        Block<User> userBlock = userService.searchUsersByFilter(authenticatedUser, blocked, filter);

        java.util.List<UserDto> userDtos = userBlock.getItems().stream()
                .map(UserConversor::toUserDto)
                .toList();

        return new BlockDto<>(userDtos, userBlock.getExistMoreItems());
    }

    @PostMapping("/{id}/block")
    public Boolean blockUser(
        @RequestAttribute Long userId, 
        @PathVariable Long id) throws InstanceNotFoundException {

        User authenticatedUser = userService.checkUser(userId);
        return userService.blockUser(authenticatedUser, id);
    }

    @PostMapping("/{id}/unblock")
    public Boolean unblockUser(
        @RequestAttribute Long userId, 
        @PathVariable Long id) throws InstanceNotFoundException, IllegalArgumentException {

        User authenticatedUser = userService.checkUser(userId);
        return userService.unblockUser(authenticatedUser, id);
    }

    @GetMapping("/username/{username}")
    public UserDto findUserByUsername(
        @RequestAttribute Long userId, 
        @PathVariable String username) throws InstanceNotFoundException {

        User authenticatedUser = userService.checkUser(userId);
        User user = userService.findUserByUsername(authenticatedUser, username);
        return UserConversor.toUserDto(user);
    }

    private AuthenticatedUserDto generateAuthenticatedUserDto(User user) {
        JwtInfo jwtInfo = new JwtInfo(user.getId(), user.getRole().toString());
        String token = jwtGenerator.generateToken(jwtInfo);

        return AuthenticatedUserDto.builder()
                .serviceToken(token)
                .userDto(UserConversor.toUserDto(user))
                .build();
    }
}