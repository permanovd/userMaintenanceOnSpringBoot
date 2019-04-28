package com.permanovd.user_maintainance.User.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Embedded
    private UserAdditionalInfo additionalInfo;

    User() {
    }

    User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String login() {
        return login;
    }

    public UserAdditionalInfo additionalInfo() {
        return additionalInfo;
    }

    void provideAdditionalInfo(UserAdditionalInfo info) {
        additionalInfo = info;
    }

     void setFirstName(String firstName) {
        this.firstName = firstName;
    }

     void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
