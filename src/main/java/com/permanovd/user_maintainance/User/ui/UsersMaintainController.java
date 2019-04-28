package com.permanovd.user_maintainance.User.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.permanovd.user_maintainance.User.application.UserService;
import com.permanovd.user_maintainance.User.domain.model.User;
import com.permanovd.user_maintainance.User.domain.model.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping(path = "/users")
public class UsersMaintainController {

    private UserService userService;

    private UserRepository userRepository;

    private ObjectMapper serializer;

    public UsersMaintainController(UserRepository userRepository, ObjectMapper serializer, UserService userService) {
        this.userRepository = userRepository;
        this.serializer = serializer;
        this.userService = userService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public void getAll(HttpServletResponse response) throws IOException {
        response.addHeader("Content-Type", "application/json");
        List<User> userList = userRepository.findAll();
        String result = "";

        try {
            result = serializer.writeValueAsString(userList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result = "{\"error\": \"Internal server error.\"}";
        }

        response.getOutputStream().write(result.getBytes());
    }


    @RequestMapping(method = RequestMethod.POST, path = "/new")
    public void registerNew(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String result;
        Scanner s = new Scanner(request.getInputStream()).useDelimiter("\\A");
        String payload = s.hasNext() ? s.next() : "";

        try {
            UserCreateDTO userCreateDTO = serializer.readValue(payload, UserCreateDTO.class);
            String id = userService.register(userCreateDTO);
            result = "{\"id\": \"" + id + "\"}";
        } catch (Exception e) {
            result = "{\"error\": \"Internal server error.\"}";
        }

        response.addHeader("Content-Type", "application/json");
        response.getOutputStream().write(result.getBytes());
    }

}
