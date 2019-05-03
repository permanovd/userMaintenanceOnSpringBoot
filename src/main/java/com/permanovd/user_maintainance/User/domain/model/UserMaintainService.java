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

    public User changeUser(User userToUpdate,
                           String login,
                           String password,
                           String firstName,
                           String lastName,
                           Date bornAt,
                           String address,
                           String aboutMe) throws UserWithSameNameExistsException {
        boolean isSameLogin = userToUpdate.login().equals(login);
        if (!isSameLogin) {
            User userWithSameName = userRepository.userWithSameName(login);
            if (null != userWithSameName) {
                if (!userWithSameName.equals(userToUpdate)) {
                    throw new UserWithSameNameExistsException(userWithSameName);
                }
            }

            userToUpdate.changeLogin(login);
        }

        boolean isSamePassword = passwordEncoder.matches(password, userToUpdate.password());
        if (!isSamePassword) {
            userToUpdate.changePassword(passwordEncoder.encode(password));
        }

        boolean isSameName = userToUpdate.firstName().equals(firstName) && userToUpdate.lastName().equals(lastName);

        if (!isSameName) {
            userToUpdate.rename(firstName, lastName);
        }

        userToUpdate.provideAdditionalInfo(new UserAdditionalInfo(bornAt, address, aboutMe));

        return userToUpdate;
    }

    public User register(String login,
                         String password) {

        return register(login, password, null, null, null, null, null);
    }
}
