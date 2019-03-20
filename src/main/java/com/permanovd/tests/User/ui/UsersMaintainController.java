package com.permanovd.tests.User.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.permanovd.tests.User.domain.model.User;
import com.permanovd.tests.User.domain.model.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/users")
public class UsersMaintainController {


    public UsersMaintainController(UserRepository userRepository, ObjectMapper serializer) {
        this.userRepository = userRepository;
        this.serializer = serializer;
    }

    private UserRepository userRepository;

    private ObjectMapper serializer;

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
}
