package com.permanovd.user_maintainance.User.application;

import com.permanovd.user_maintainance.User.domain.model.User;
import com.permanovd.user_maintainance.User.domain.model.UserRepository;
import com.permanovd.user_maintainance.User.ui.UserCreateDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;

    private com.permanovd.user_maintainance.User.domain.model.UserMaintainService userMaintainService;

    public UserService(UserRepository userRepository, com.permanovd.user_maintainance.User.domain.model.UserMaintainService userMaintainService) {
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
