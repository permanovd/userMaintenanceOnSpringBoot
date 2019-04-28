package com.permanovd.user_maintainance.User.domain.model;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserMaintainService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserMaintainService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User register(String login,
                         String password,
                         String firstName,
                         String lastName,
                         Date bornAt,
                         String address,
                         String aboutMe) throws UserWithSameNameExistsException {

        User withSameName = userRepository.userWithSameName(login);
        if (null != withSameName) {
            throw new UserWithSameNameExistsException(withSameName);
        }

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
