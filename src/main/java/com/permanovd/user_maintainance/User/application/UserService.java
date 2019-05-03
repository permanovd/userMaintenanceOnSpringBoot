package com.permanovd.user_maintainance.User.application;

import com.permanovd.user_maintainance.User.domain.model.User;
import com.permanovd.user_maintainance.User.domain.model.UserRepository;
import com.permanovd.user_maintainance.User.domain.model.UserWithSameNameExistsException;
import com.permanovd.user_maintainance.User.ui.UserCreateDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    private com.permanovd.user_maintainance.User.domain.model.UserMaintainService userMaintainService;

    public UserService(UserRepository userRepository, com.permanovd.user_maintainance.User.domain.model.UserMaintainService userMaintainService) {
        this.userRepository = userRepository;
        this.userMaintainService = userMaintainService;
    }

    @Transactional
    public Long register(UserCreateDTO dto) throws UserWithSameNameExistsException {
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

        return user.getId();
    }

    @Transactional
    public Long changeUser(UserCreateDTO dto, Long userToChangeId) throws IllegalStateException, UserWithSameNameExistsException {
        User userToUpdate = getUser(userToChangeId);
        User user = userMaintainService.changeUser(
                userToUpdate,
                dto.getLogin(),
                dto.getPassword(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getAddress(),
                dto.getAboutMe()
        );

        userRepository.save(user);

        return user.getId();
    }

    @Transactional
    public void deleteUser(Long id) throws IllegalStateException {
        User user = getUser(id);
        if (null == user) {
            throw new IllegalStateException();
        }

        userRepository.deleteById(id);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).getContent();
    }
}
