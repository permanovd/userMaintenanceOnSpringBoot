package com.permanovd.tests.User.domain.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "about_me")
    private String aboutMe;

    public User() {
    }
}
