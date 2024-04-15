package com.example.security.user;

public class AlreadyRegisteredUserException extends RuntimeException {

    public AlreadyRegisteredUserException(final String message) {
        super(message);
    }

    public AlreadyRegisteredUserException() {
        super("이미 등록된 유저입니다.");
    }

}
