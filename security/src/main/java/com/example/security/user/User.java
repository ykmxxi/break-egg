package com.example.security.user;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String authority;

    public User(final String username, final String password, final String authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 사용자에게 부여된 권한을 반환
        return Collections.singleton((GrantedAuthority) () -> authority);
    }

    public boolean isAdmin() {
        return ROLE_ADMIN.equals(authority);
    }

    @Override
    public String getPassword() { // 패스워드 얻기
        return password;
    }

    @Override
    public String getUsername() { // 사용자 이름 얻기
        return username;
    }

    @Override
    public boolean isAccountNonExpired() { // 사용자의 계정이 만료되었는지 여부
        return true; // true: 유효함(만료되지 않음)
    }

    @Override
    public boolean isAccountNonLocked() { // 사용자가 잠겨 있는지 또는 잠금 해제되어 있는지
        return true; // true: 잠겨있지 않음(lock X)
    }

    @Override
    public boolean isCredentialsNonExpired() { // 자격 증명(비밀번호)이 만료되었는지 여부
        return true; // true: 자격 증명이 유효함(만료되지 않음)
    }

    @Override
    public boolean isEnabled() { // 사용자가 활성화되었는지 또는 비활성화되었는지 여부
        return true; // true: 활성화되어 있음
    }

}
