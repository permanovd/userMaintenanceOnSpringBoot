package com.permanovd.user_maintainance.Integration;

import com.permanovd.user_maintainance.User.application.UserService;
import com.permanovd.user_maintainance.User.domain.model.User;
import com.permanovd.user_maintainance.User.domain.model.UserRepository;
import com.permanovd.user_maintainance.User.domain.model.UserWithSameNameExistsException;
import com.permanovd.user_maintainance.User.ui.UserCreateDTO;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersUniqueNameTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private Collection<String> createdUsers = new ArrayList<>();

    @Test
    public void registrationFailsWhenUsernameIsNotUnique() {
        UserCreateDTO dto = new UserCreateDTO(UUID.randomUUID().toString(), "password");

        String idOfCreatedUser = userService.register(dto);
        createdUsers.add(idOfCreatedUser);
        try {
            userService.register(dto);
            fail("Unique username validation is not working");
        } catch (UserWithSameNameExistsException ex) {
            assertThat(String.valueOf(ex.getUser().getId())).isEqualTo(idOfCreatedUser);
        }
    }

    @After
    public void cleanup() {
        for (String id : createdUsers) {
            User byId = userRepository.findById(Long.parseLong(id)).orElse(null);
            if (null == byId) {
                continue;
            }

            userRepository.delete(byId);
        }
    }
}
