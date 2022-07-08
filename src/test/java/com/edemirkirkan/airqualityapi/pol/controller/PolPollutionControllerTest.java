package com.edemirkirkan.airqualityapi.pol.controller;

import com.edemirkirkan.airqualityapi.BaseTest;
import com.edemirkirkan.airqualityapi.log.info.service.LogInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PolPollutionControllerTest extends BaseTest {
    private MockMvc mockMvc;
    private static final String BASE_PATH = "/api/v1/pollutions";
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }
    @Test
    void getPollutionData() throws Exception {
        int expectedDayResult = 5;
        MvcResult mvcResult = mockMvc.perform(get(BASE_PATH)
                        .param("city", "Ankara")
                        .param("startDate", "22-02-2021")
                        .param("endDate", "26-02-2021")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk()).andReturn();

        assertTrue(isSuccessPol(mvcResult, expectedDayResult));
    }

    @Test
    void deletePollutionData() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(BASE_PATH)
                        .param("city", "Ankara")
                        .param("date", "22-02-2021")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        assertTrue(isSuccess(mvcResult));
    }
}