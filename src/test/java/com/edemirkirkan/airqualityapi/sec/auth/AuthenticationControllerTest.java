package com.edemirkirkan.airqualityapi.sec.auth;

import com.edemirkirkan.airqualityapi.BaseTest;
import com.edemirkirkan.airqualityapi.usr.dto.UsrUserSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AuthenticationControllerTest extends BaseTest {
    private MockMvc mockMvc;
    private static final String BASE_PATH = "/api/v1/auth";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }
    @Test
    void register() throws Exception {
        UsrUserSaveRequestDto usrUserSaveRequestDto = UsrUserSaveRequestDto.builder()
                .firstName("Ege")
                .lastName("Demirkırkan")
                .username("edemirkirkan069")
                .password("password06")
                .build();
        String endpoint = "/register";
        String body = objectMapper.writeValueAsString(usrUserSaveRequestDto);
        MvcResult result = mockMvc.perform(
                post(BASE_PATH + endpoint).content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertTrue(isSuccess(result));
    }

    @Test
    void login() throws Exception {
        AuthenticationLoginRequestDto authenticationLoginRequestDto = AuthenticationLoginRequestDto.builder()
                .username("edemirkirkan069")
                .password("password06")
                .build();
        String endpoint = "/login";
        String body = objectMapper.writeValueAsString(authenticationLoginRequestDto);
        MvcResult result = mockMvc.perform(
                        post(BASE_PATH + endpoint).content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertTrue(isSuccess(result));
    }
}