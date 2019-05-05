package com.permanovd.user_maintainance.User.ui;

import com.permanovd.user_maintainance.User.application.UserService;
import com.permanovd.user_maintainance.User.domain.model.User;
import com.permanovd.user_maintainance.User.domain.model.UserWithSameNameExistsException;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UsersMaintainAPIController {

    private UserService userService;

    private UserOutputDTOAssembler dtoAssembler;
    private Logger logger;


    public UsersMaintainAPIController(UserService userService, UserOutputDTOAssembler dtoAssembler, Logger logger) {
        this.userService = userService;
        this.dtoAssembler = dtoAssembler;
        this.logger = logger;
    }

    @GetMapping
    public List<UserOutputDTO> getAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(value = "size", required = false, defaultValue = "50") int size) {
        if (page > 0) {
            // todo solve this issue https://stackoverflow.com/a/49575492/4082772
            --page;
        }

        List<User> userList = userService.getList(page, size);
        return dtoAssembler.assembleList(userList);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        if (null == user) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Not found");
            }});
        }

        return ResponseEntity.ok(dtoAssembler.assembleOne(user));
    }


    @PostMapping(path = "/")
    public ResponseEntity<?> register(@RequestBody UserCreateDTO userCreateDTO, UriComponentsBuilder ucBuilder) throws UserWithSameNameExistsException {
        Long createdUserId = userService.register(userCreateDTO);
        User user = userService.getUser(createdUserId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/users/{id}").buildAndExpand(user.getId()).toUri());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(dtoAssembler.assembleOne(user));
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<?> changeUser(@RequestBody UserUpdateDTO userUpdateDTO,
                                        UriComponentsBuilder ucBuilder,
                                        @PathVariable("id") Long userId) throws UserWithSameNameExistsException {
        userService.changeUser(userUpdateDTO, userId);
        User user = userService.getUser(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/users/{id}").buildAndExpand(user.getId()).toUri());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(dtoAssembler.assembleOne(user));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) throws UserWithSameNameExistsException {
        try {
            userService.deleteUser(userId);
            logger.info("User " + userId + " is deleted.");
        } catch (IllegalStateException ignored) {
            // Ignoring if user is already deleted to provide idempotent behaviour.
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler(UserWithSameNameExistsException.class)
    public ResponseEntity<?> handleUserNameDuplicateException() {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new HashMap<String, String>() {{
            put("error", "This login is already used");
        }});
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        logger.warn("Unhandled exception", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<String, String>() {{
            put("error", "Internal server error");
        }});
    }

}
