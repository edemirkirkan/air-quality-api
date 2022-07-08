package com.edemirkirkan.airqualityapi.rst.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestServiceTest {

    @InjectMocks
    private RestService restService;

    @Test
    void shouldNotGetGeoRequest() {
        assertThrows(NullPointerException.class, () -> restService.geoRequest(null));
    }

    @Test
    void shouldNotGetPolRequest() {
        assertThrows(NullPointerException.class, () -> restService.polRequest(BigDecimal.valueOf(12.4),
                BigDecimal.valueOf(13.7), new Date(),  new Date()));
    }
}