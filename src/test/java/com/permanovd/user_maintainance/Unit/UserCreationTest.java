package com.permanovd.user_maintainance.Unit;

import com.permanovd.user_maintainance.User.domain.model.User;
import com.permanovd.user_maintainance.User.domain.model.UserMaintainService;
import com.permanovd.user_maintainance.User.domain.model.UserRepository;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserCreationTest {


    @Test
    public void passwordIsHashedOnUserRegistration() {
        String plainPassword = "123456";
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.userWithSameName(anyString())).thenReturn(null);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserMaintainService service = new UserMaintainService(passwordEncoder, userRepository);
        User user = service.register("login", plainPassword);

        assertThat(user.password()).isNotEqualTo(plainPassword);
        assertThat(passwordEncoder.matches(plainPassword, user.password())).isEqualTo(true);
    }
}
