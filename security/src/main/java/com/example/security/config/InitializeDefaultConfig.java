package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.security.note.NoteService;
import com.example.security.notice.NoticeService;
import com.example.security.user.User;
import com.example.security.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Profile(value = "!test") // test 제외
@Slf4j
public class InitializeDefaultConfig {

    private final UserService userService;
    private final NoteService noteService;
    private final NoticeService noticeService;

    /**
     * 유저, note 4개
     */
    @Bean
    public void initializeDefaultUser() {
        User user = userService.singUp("user", "user");
        log.info("user id={}, pw={}", user.getUsername(), user.getPassword());

        noteService.saveNote(user, "제목1", "내용1 입니다.");
        noteService.saveNote(user, "제목2", "내용2 입니다.");
        noteService.saveNote(user, "제목3", "내용3 입니다.");
        noteService.saveNote(user, "제목4", "내용4 입니다.");
    }

    /**
     * 관리자, notice 2개
     */
    @Bean
    public void initializeDefaultAdmin() {
        userService.singUpAdmin("admin", "admin");

        noticeService.saveNotice("공지사항1", "공지사항 내용1 입니다.");
        noticeService.saveNotice("공지사항2", "공지사항 내용2 입니다.");
    }

}
