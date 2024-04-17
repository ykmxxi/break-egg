package com.example.security.notice;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@Transactional
class NoticeControllerTest {

    @Autowired
    private NoticeRepository noticeRepository;
    private MockMvc mvc;

    @BeforeEach
    void setUp(@Autowired WebApplicationContext applicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .alwaysDo(print())
                .build();
    }

    @Test
    void getNotice_인증없음() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notice")) // /notice GET 요청을 실행하면
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login")) // 인증이 없는 경우 ~~~/login 페이지로 리다이렉션
                .andExpect(status().is3xxRedirection()); // 상태코드도 3xx 리다이렉션
    }

    @Test
    @WithMockUser
    void getNotice_인증있음() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notice"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("notice/index"));
    }

    @Test
    void postNotice_인증없음() throws Exception {
        mvc.perform(post("/notice")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "제목")
                .param("content", "내용")
        ).andExpect(status().isForbidden()); // 접근 거부
    }

    @Test
    @WithMockUser(roles = {"USER"}, username = "user", password = "user")
    void postNotice_유저인증있음() throws Exception {
        mvc.perform(
                post("/notice").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "제목")
                        .param("content", "내용")
        ).andExpect(status().isForbidden()); // 접근 거부
    }

    @Test
    @WithMockUser(roles = {"ADMIN"}, username = "admin", password = "admin") // admin 권한의 가짜 유저 생성
    void postNotice_어드민인증있음() throws Exception {
        mvc.perform(
                post("/notice").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 폼 데이터를 사용
                        .param("title", "제목") // 파라미터 목록
                        .param("content", "내용")
        ).andExpect(redirectedUrl("notice")).andExpect(status().is3xxRedirection());
    }

    @Test
    void deleteNotice_인증없음() throws Exception {
        Notice notice = noticeRepository.save(new Notice("제목", "내용"));
        mvc.perform(
                delete("/notice?id=" + notice.getId())
        ).andExpect(status().isForbidden()); // 접근 거부
    }

    @Test
    @WithMockUser(roles = {"USER"}, username = "user", password = "user")
    void deleteNotice_유저인증있음() throws Exception {
        Notice notice = noticeRepository.save(new Notice("제목", "내용"));
        mvc.perform(
                delete("/notice?id=" + notice.getId()).with(csrf())
        ).andExpect(status().isForbidden()); // 접근 거부
    }

    @Test
    @WithMockUser(roles = {"ADMIN"}, username = "admin", password = "admin")
    void deleteNotice_어드민인증있음() throws Exception {
        Notice notice = noticeRepository.save(new Notice("제목", "내용"));
        mvc.perform(
                delete("/notice?id=" + notice.getId()).with(csrf())
        ).andExpect(redirectedUrl("notice")).andExpect(status().is3xxRedirection());
    }

}
