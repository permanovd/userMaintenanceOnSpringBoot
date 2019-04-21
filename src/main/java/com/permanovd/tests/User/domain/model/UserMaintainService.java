package com.permanovd.tests.User.domain.model;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserMaintainService {

    public User register(String login,
                         String password,
                         String firstName,
                         String lastName,
                         Date bornAt,
                         String address,
                         String aboutMe) {
        // todo encode password.
        User user = new User(login, password);
        user.provideAdditionalInfo(new UserAdditionalInfo(bornAt, address, aboutMe));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }
}
