package com.example.loginpractice.service.dto;

import com.example.loginpractice.domain.User;
import com.example.loginpractice.domain.UserRole;

public class JoinRequest {

    private String username;
    private String password;
    private String passwordCheck;
    private String nickname;

    public User toEntity() {
        return new User(
                this.username,
                this.password,
                this.nickname,
                UserRole.MEMBER
        );
    }

    public User toEntity(final String encodedPassword) {
        return new User(
                this.username,
                encodedPassword,
                this.nickname,
                UserRole.MEMBER
        );
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(final String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

}
