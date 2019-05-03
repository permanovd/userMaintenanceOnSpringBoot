package com.permanovd.user_maintainance.User.ui;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserCreateDTO {

    @NotNull
    @Size(min = 2, max = 255)
    private String login;

    @Size(min = 7, max = 32)
    private String password;

    @JsonProperty(value = "first-name")
    @Size(min = 2, max = 255)
    private String firstName;

    @JsonProperty(value = "last-name")
    @Size(min = 2, max = 255)
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(value = "birth-date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "about-me")
    private String aboutMe;

    @JsonCreator
    public UserCreateDTO(@JsonProperty(value = "login", required = true) String login,
                         @JsonProperty(value = "password", required = true) String password) {
        this.login = login;
        this.password = password;
    }
}
