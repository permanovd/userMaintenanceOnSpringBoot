package com.permanovd.tests.Integration;

import com.permanovd.tests.User.application.UserService;
import com.permanovd.tests.User.domain.model.User;
import com.permanovd.tests.User.domain.model.UserRepository;
import com.permanovd.tests.User.ui.UserCreateDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        UserCreateDTO dto = new UserCreateDTO("test", "ffs");

        // Act.
        String id = userService.register(dto);

        // Assert.
        User user = userRepository.findById(Long.parseLong(id)).orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.login()).isEqualTo("test");
    }

    @Test
    public void systemAcceptsRegistrationWithAdditionalInfo() {
        // Arrange.
        UserCreateDTO dto = new UserCreateDTO("test", "ffs");
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
