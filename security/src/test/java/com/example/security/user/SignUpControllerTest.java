package com.example.security.user;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Transactional
class SignUpControllerTest {

    private MockMvc mvc;

    @BeforeEach
    void setUp(@Autowired WebApplicationContext applicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(springSecurity()) // SecurityMockMvcConfigurers.springSecurity()
                .alwaysDo(print())
                .build();
    }

    @Test
    void 회원가입() throws Exception {
        mvc.perform(post("/signup").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "ykmxxi")
                        .param("password", "password"))
                .andExpect(redirectedUrl("login"))
                .andExpect(status().is3xxRedirection());
    }

}
