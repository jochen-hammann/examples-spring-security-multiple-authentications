package com.example.spring.security.multipleauthentications.hello;

import com.example.spring.security.multipleauthentications.constants.ConstHttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HelloBearerControllerIT
{
    // ============================== [Fields] ==============================

    // -------------------- [Private Fields] --------------------

    @Autowired
    private MockMvc mockMvc;

    // ============================== [Unit Tests] ==============================

    // -------------------- [Test Helper Classes] --------------------

    // -------------------- [Test Helper Methods] --------------------

    // -------------------- [Test Initialization] --------------------

    // -------------------- [Tests] --------------------

    @Test
    void getTest() throws Exception
    {
        this.mockMvc.perform(get("/bearer/hello").header(ConstHttpHeaders.AUTHORIZATION_HEADER, "Bearer 4812")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void authenticationErrorNotExists() throws Exception
    {
        this.mockMvc.perform(get("/bearer/hello").header(ConstHttpHeaders.AUTHORIZATION_HEADER, "Bearer NotExisting")).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    void authenticationErrorNoBearer() throws Exception
    {
        this.mockMvc.perform(get("/bearer/hello").header(ConstHttpHeaders.AUTHORIZATION_HEADER, "No Bearer")).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    void authorizationError() throws Exception
    {
        this.mockMvc.perform(get("/bearer/hello").header(ConstHttpHeaders.AUTHORIZATION_HEADER, "Bearer 4813")).andDo(print()).andExpect(status().isForbidden());
    }
}
