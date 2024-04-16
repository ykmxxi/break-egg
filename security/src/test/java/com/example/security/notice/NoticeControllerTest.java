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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
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
        mvc.perform(MockMvcRequestBuilders.get("/notice"))
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login"))
                .andExpect(status().is3xxRedirection());
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
    @WithMockUser(roles = {"ADMIN"}, username = "admin", password = "admin")
    void postNotice_어드민인증있음() throws Exception {
        mvc.perform(
                post("/notice").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "제목")
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
