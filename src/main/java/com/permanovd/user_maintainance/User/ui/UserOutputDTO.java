package com.permanovd.user_maintainance.User.ui;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
class UserOutputDTO {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String login;

    @JsonProperty("first-name")
    private String firstName;

    @JsonProperty("last-name")
    private String lastName;

    @JsonProperty("about-me")
    private String aboutMe;

    @JsonProperty("birth-date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;
}
