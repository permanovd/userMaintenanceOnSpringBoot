package com.permanovd.user_maintainance.Unit;

import com.permanovd.user_maintainance.User.domain.model.User;
import com.permanovd.user_maintainance.User.domain.model.UserMaintainService;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;


public class UserCreationTest {

    @Test
    public void passwordIsHashedOnUserRegistration() {
        String plainPassword = "123456";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserMaintainService service = new UserMaintainService(passwordEncoder);
        User user = service.register("login", plainPassword);

        assertThat(user.password()).isNotEqualTo(plainPassword);
        assertThat(passwordEncoder.matches(plainPassword, user.password())).isEqualTo(true);
    }
}
