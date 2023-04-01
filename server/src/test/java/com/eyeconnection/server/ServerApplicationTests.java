package com.eyeconnection.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.eyeconnection.server.dao.UserRepo;
import com.eyeconnection.server.entity.User;

class ServerApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepo userRepo;

    @Test
    public void testSignUp() {
        User newUser = new User(Long.valueOf(999), "test@email.com", "password", "Jimmy", "Green", "10000");
        
        // MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/sign_up")
        // .content(asJsonString(newUser))
        // .contentType(MediaType.APPLICATION_JSON)
        // .accept(MediaType.APPLICATION_JSON))
        // .andReturn();
    }
}
