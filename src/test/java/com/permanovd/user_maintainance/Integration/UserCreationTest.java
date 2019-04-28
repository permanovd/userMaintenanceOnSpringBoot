package com.permanovd.user_maintainance.Integration;

import com.permanovd.user_maintainance.User.application.UserService;
import com.permanovd.user_maintainance.User.domain.model.User;
import com.permanovd.user_maintainance.User.domain.model.UserRepository;
import com.permanovd.user_maintainance.User.ui.UserCreateDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCreationTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void systemAcceptsRegistrationWhenUserDefinesRequiredFields() {
        // Arrange.
        String login = UUID.randomUUID().toString();
        UserCreateDTO dto = new UserCreateDTO(login, "ffs");

        // Act.
        String id = userService.register(dto);

        // Assert.
        User user = userRepository.findById(Long.parseLong(id)).orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.login()).isEqualTo(login);
    }

    @Test
    public void systemAcceptsRegistrationWithAdditionalInfo() {
        // Arrange.
        UserCreateDTO dto = new UserCreateDTO(UUID.randomUUID().toString(), "ffs");
        Date date = new GregorianCalendar(1998, Calendar.FEBRUARY, 11).getTime();
        dto.setBirthDate(date);
        dto.setAddress("Palm str 22, 14");
        dto.setAboutMe("Test test test");

        // Act.
        String id = userService.register(dto);

        // Assert.
        User user = userRepository.findById(Long.parseLong(id)).orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.additionalInfo().wasBornAt()).isInSameDayAs(date);
        assertThat(user.additionalInfo().address()).isEqualTo("Palm str 22, 14");
        assertThat(user.additionalInfo().aboutMe()).isEqualTo("Test test test");
    }

}
