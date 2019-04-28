package com.permanovd.user_maintainance.User.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class UserAdditionalInfo {

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "address")
    private String address;

    @Column(name = "about_me")
    private String aboutMe;

    UserAdditionalInfo() {
    }

    UserAdditionalInfo(Date birthDate, String address, String aboutMe) {
        this.birthDate = birthDate;
        this.address = address;
        this.aboutMe = aboutMe;
    }

    public Date wasBornAt() {
        return birthDate;
    }

    public String address() {
        return address;
    }

    public String aboutMe() {
        return aboutMe;
    }
}
