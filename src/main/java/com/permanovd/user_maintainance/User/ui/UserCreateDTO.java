package com.permanovd.user_maintainance.User.ui;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class UserCreateDTO {

    private String login;

    private String password;

    @JsonProperty(value = "first-name")
    private String firstName;

    @JsonProperty(value = "last-name")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(value = "birth-date")
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

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setBirthDate(Date date) {
        birthDate = date;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getAddress() {
        return address;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
