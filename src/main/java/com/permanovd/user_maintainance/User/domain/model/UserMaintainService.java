package com.permanovd.user_maintainance.User.domain.model;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserMaintainService {

    private PasswordEncoder passwordEncoder;

    public UserMaintainService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String login,
                         String password,
                         String firstName,
                         String lastName,
                         Date bornAt,
                         String address,
                         String aboutMe) {
        User user = new User(login, passwordEncoder.encode(password));
        user.provideAdditionalInfo(new UserAdditionalInfo(bornAt, address, aboutMe));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    public User register(String login,
                         String password) {

        return register(login, password, null, null, null, null, null);
    }
}
