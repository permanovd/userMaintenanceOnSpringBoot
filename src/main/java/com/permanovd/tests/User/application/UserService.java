package com.permanovd.tests.User.application;

import com.permanovd.tests.User.domain.model.User;
import com.permanovd.tests.User.domain.model.UserRepository;
import com.permanovd.tests.User.ui.UserCreateDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;

    private com.permanovd.tests.User.domain.model.UserMaintainService userMaintainService;

    public UserService(UserRepository userRepository, com.permanovd.tests.User.domain.model.UserMaintainService userMaintainService) {
        this.userRepository = userRepository;
        this.userMaintainService = userMaintainService;
    }

    @Transactional
    public String register(UserCreateDTO dto) {
        User user = userMaintainService.register(
                dto.getLogin(),
                dto.getPassword(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getAddress(),
                dto.getAboutMe()
        );
        userRepository.save(user);

        return String.valueOf(user.getId());
    }
}
