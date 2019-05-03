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
        return null == additionalInfo ? new UserAdditionalInfo() : additionalInfo;
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

    public String password() {
        return password;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    void changeLogin(String newLogin) {
        login = newLogin;
    }

    void changePassword(String newPassword) {
        password = newPassword;
    }

    void rename(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }

        return ((User) o).getId() == getId();
    }
}
