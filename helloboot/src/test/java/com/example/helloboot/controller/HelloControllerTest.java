package com.example.helloboot.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired private MockMvc mockMvc;

    @Test
    void hello() throws Exception {
        String name = "Spring";

        mockMvc.perform(get("/hello").param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Spring"));
    }

}
