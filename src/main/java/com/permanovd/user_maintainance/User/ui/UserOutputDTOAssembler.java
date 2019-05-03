package com.permanovd.user_maintainance.User.ui;

import com.permanovd.user_maintainance.User.domain.model.User;
import com.permanovd.user_maintainance.User.domain.model.UserAdditionalInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserOutputDTOAssembler {

    UserOutputDTO assembleOne(User user) {
        return new UserOutputDTO(user.getId(), user.login(),
                user.firstName(),
                user.lastName(), user.additionalInfo().aboutMe(), user.additionalInfo().address(), user.additionalInfo().wasBornAt());
    }

    List<UserOutputDTO> assembleList(List<User> userList) {
        ArrayList<UserOutputDTO> userOutputDTOS = new ArrayList<>();

        for (User user : userList) {
            userOutputDTOS.add(assembleOne(user));
        }

        return userOutputDTOS;
    }

    UserCreateDTO assembleForChanges(User user) {
        UserAdditionalInfo userAdditionalInfo = user.additionalInfo();
        return new UserCreateDTO(user.login(), user.password(), user.firstName(), user.lastName(),
                userAdditionalInfo.wasBornAt(), userAdditionalInfo.address(), userAdditionalInfo.aboutMe());
    }
}
