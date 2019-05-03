package com.permanovd.user_maintainance.User.ui;

import com.permanovd.user_maintainance.User.application.UserService;
import com.permanovd.user_maintainance.User.domain.model.User;
import com.permanovd.user_maintainance.User.domain.model.UserWithSameNameExistsException;
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


    public UsersMaintainAPIController(UserService userService, UserOutputDTOAssembler dtoAssembler) {
        this.userService = userService;
        this.dtoAssembler = dtoAssembler;
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<UserOutputDTO> getAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                      @RequestParam(value = "size", required = false, defaultValue = "50") int size) {
        if (page > 0) {
            // todo solve this issue https://stackoverflow.com/a/49575492/4082772
            --page;
        }

        List<User> userList = userService.getList(page, size);
        return dtoAssembler.assembleList(userList);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        if (null == user) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Not found");
            }});
        }

        return ResponseEntity.ok(dtoAssembler.assembleOne(user));
    }


    @RequestMapping(method = RequestMethod.POST, path = "/")
    public ResponseEntity<?> register(@RequestBody UserCreateDTO userCreateDTO, UriComponentsBuilder ucBuilder) {
        Long createdUserId;
        try {
            createdUserId = userService.register(userCreateDTO);
        } catch (UserWithSameNameExistsException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new HashMap<String, String>() {{
                put("error", "This login is already used");
            }});
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new HashMap<String, String>() {{
                        put("error", "Internal server error");
                    }}
            );
        }

        User user = userService.getUser(createdUserId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/users/{id}").buildAndExpand(user.getId()).toUri());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(user);
    }

}
