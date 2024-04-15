package com.example.security.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("유저를 찾을 수 없습니다.");
    }

}
