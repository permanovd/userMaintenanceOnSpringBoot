package com.permanovd.user_maintainance.User.domain.model;

public class UserWithSameNameExistsException extends IllegalStateException {

    private User user;

    UserWithSameNameExistsException(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
