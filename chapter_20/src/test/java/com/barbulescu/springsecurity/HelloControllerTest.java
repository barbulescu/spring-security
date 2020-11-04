package com.barbulescu.springsecurity;

import com.barbulescu.springsecurity.config.WithCustomUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void dependenciesAreValid() {
        assertThat(mvc).isNotNull();
    }

    @Test
    void helloUnauthenticated() throws Exception {
        mvc.perform(get("/hello1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "mary")
    void helloAuthenticated1() throws Exception {
        mvc.perform(get("/hello1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello!"));
    }

    @Test
    @WithMockUser(username = "mary")
    void helloAuthenticated2() throws Exception {
        mvc.perform(get("/hello2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello mary!"));
    }

    @Test
    void helloAuthenticatedWithUser() throws Exception {
        var requestBuilder = get("/hello1")
                .with(user("mary"));
        mvc.perform(requestBuilder)
                .andExpect(content().string("Hello!"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("john")
    public void helloAuthenticated() throws Exception {
        mvc.perform(get("/hello2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithCustomUser(username = "mary")
    public void helloCustomAuthenticated() throws Exception {
        mvc.perform(get("/hello2"))
                .andExpect(status().isOk());
    }
}
