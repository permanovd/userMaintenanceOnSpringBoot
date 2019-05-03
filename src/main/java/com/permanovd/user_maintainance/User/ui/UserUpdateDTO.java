package com.permanovd.user_maintainance.User.ui;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @NotNull
    @Size(min = 2, max = 255)
    private String login;

    private String password;

    @JsonProperty(value = "first-name")
    private String firstName;

    @JsonProperty(value = "last-name")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(value = "birth-date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "about-me")
    private String aboutMe;

}
