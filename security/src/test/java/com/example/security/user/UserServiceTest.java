package com.example.security.user;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles(profiles = "test") // test 프로필 적용, InitializeDefaultConfig에서 등록한 기존 데이터 사용 X
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 일반유저_회원가입() {
        // given
        String username = "ykmxxi";
        String password = "password";

        // when
        User user = userService.singUp(username, password);

        // then
        then(user.getId()).isNotNull(); // id가 정상적으로 생성되었는지 확인
        then(user.getUsername()).isEqualTo("ykmxxi"); // username 확인
        then(user.getPassword()).startsWith("{bcrypt}"); // password가 인코딩되어 {bcrypt}로 시작하는지 검증
        then(user.getAuthorities()).hasSize(1); // 인가 권한이 1개인지 확인
        then(user.getAuthorities().stream()
                .findFirst()
                .get()
                .getAuthority()).isEqualTo("ROLE_USER"); // 일반 유저인지 확인
        then(user.isAdmin()).isFalse(); // 관리자가 아닌지 확인
        then(user.isAccountNonExpired()).isTrue();
        then(user.isAccountNonLocked()).isTrue();
        then(user.isEnabled()).isTrue();
        then(user.isCredentialsNonExpired()).isTrue();
    }

    @Test
    void 관리자_회원가입() {
        // given
        String username = "admin123";
        String password = "password";

        //when
        User user = userService.singUpAdmin(username, password);

        //then
        then(user.getId()).isNotNull();
        then(user.getUsername()).isEqualTo("admin123");
        then(user.getPassword()).startsWith("{bcrypt}");
        then(user.getAuthorities()).hasSize(1);
        then(user.getAuthorities().stream()
                .findFirst()
                .get()
                .getAuthority()).isEqualTo("ROLE_ADMIN");
        then(user.isAdmin()).isTrue();
        then(user.isAccountNonExpired()).isTrue();
        then(user.isAccountNonLocked()).isTrue();
        then(user.isEnabled()).isTrue();
        then(user.isCredentialsNonExpired()).isTrue();
    }

    @Test
    void 이름으로_회원조회() {
        // given
        userRepository.save(new User("user123", "password", "ROLE_USER"));

        // when
        User user = userService.findByUsername("user123");

        // then
        then(user.getId()).isNotNull();
    }

}
