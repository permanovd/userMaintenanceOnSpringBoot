package com.permanovd.user_maintainance.User.ui;

import com.permanovd.user_maintainance.User.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserOutputDTOAssembler {

    UserOutputDTO assembleOne(User user) {
        return new UserOutputDTO(user.login(),
                user.firstName(),
                user.lastName(), user.additionalInfo().aboutMe(), user.additionalInfo().wasBornAt());
    }

    List<UserOutputDTO> assembleList(List<User> userList) {
        ArrayList<UserOutputDTO> userOutputDTOS = new ArrayList<>();

        for (User user : userList) {
            userOutputDTOS.add(assembleOne(user));
        }

        return userOutputDTOS;
    }
}
