package io.github.sejoung.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.sejoung.test.util.WithMockCustomUser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {

    private static final String URL = "/api/test";

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser
    @Test
    void withMockTest() throws Exception {
        this.mockMvc.perform(get(URL)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk());
    }

    @WithMockCustomUser
    @Test
    void customWithMockTest() throws Exception {
        this.mockMvc.perform(get(URL)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk());
    }
}