package com.example.loginpractice.domain;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String nickname;

    private UserRole userRole;

    protected User() {
    }

    public User(final String username, final String password, final String nickname, final UserRole userRole) {
        Assert.hasText(username, "username은 필수입니다.");
        Assert.hasText(password, "password는 필수입니다.");
        Assert.hasText(nickname, "닉네임은 필수입니다.");
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    public boolean isCorrectPassword(final String password) {
        return this.password.equals(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> userRole.name());
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
