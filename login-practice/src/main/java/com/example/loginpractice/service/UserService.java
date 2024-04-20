package com.example.loginpractice.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.loginpractice.domain.User;
import com.example.loginpractice.repository.UserRepository;
import com.example.loginpractice.service.dto.JoinRequest;
import com.example.loginpractice.service.dto.LoginRequest;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * username 중복 체크
     */
    public boolean checkUsernameDuplicate(final String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * nickname 중복 체크
     */
    public boolean checkNicknameDuplicate(final String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    /**
     * 회원가입
     * 비밀번호를 암호화해서 저장
     */
    public void join(final JoinRequest request) {
        userRepository.save(request.toEntity(
                passwordEncoder.encode(request.getPassword())
        ));
    }

    /**
     * 로그인
     */
    public User login(final LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser
                .orElseGet(() -> null);

        if (!user.isCorrectPassword(request.getPassword())) {
            return null;
        }
        return user;
    }

    public User findByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

}
