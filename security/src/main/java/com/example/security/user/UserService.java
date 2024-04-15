package com.example.security.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        log.info("PasswordEncoder={}", passwordEncoder);
    }

    /**
     * 유저 등록
     *
     * @param username 인증에 사용할 username
     * @param password 인증에 사용할 password
     * @return 일반 유저 권한을 가지고 있는 유저
     */
    public User singUp(final String username, final String password) {
        log.info("UserService signUp() username={}, password={}", username, password);
        if (userRepository.findByUsername(username) != null) {
            throw new AlreadyRegisteredUserException();
        }

        String encodedPw = passwordEncoder.encode(password);
        log.info("Encoded PW={}", encodedPw);
        return userRepository.save(new User(
                username,
                encodedPw,
                "ROLE_USER"
        ));
    }

    /**
     * 관리자 등록
     *
     * @param username 인증에 사용할 username
     * @param password 인증에 사용할 password
     * @return 일반 유저 권한을 가지고 있는 유저
     */
    public User singUpAdmin(final String username, final String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new AlreadyRegisteredUserException();
        }
        return userRepository.save(new User(
                username,
                passwordEncoder.encode(password),
                "ROLE_ADMIN"
        ));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
