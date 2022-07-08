package com.edemirkirkan.airqualityapi.rst.service;

import com.edemirkirkan.airqualityapi.rst.dto.RestResponseGeoDto;
import com.edemirkirkan.airqualityapi.rst.dto.RestResponsePolDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RestServiceTest {

    @Spy
    @InjectMocks
    private RestService restService;

    @Test
    void shouldGetCoordinatesFromAPI() {
        RestResponseGeoDto restResponseGeoDto;

        restResponseGeoDto = restService.geoRequest("Barcelona");
        assertEquals(41.38, restResponseGeoDto.getLat().setScale(2, RoundingMode.HALF_UP).doubleValue());
        assertEquals(2.18, restResponseGeoDto.getLon().setScale(2, RoundingMode.HALF_UP).doubleValue());
        assertEquals("ES", restResponseGeoDto.getCountry());

        restResponseGeoDto = restService.geoRequest("Tokyo");
        assertEquals(35.68, restResponseGeoDto.getLat().setScale(2, RoundingMode.HALF_UP).doubleValue());
        assertEquals(139.76, restResponseGeoDto.getLon().setScale(2, RoundingMode.HALF_UP).doubleValue());
        assertEquals("JP", restResponseGeoDto.getCountry());

        restResponseGeoDto = restService.geoRequest("Ankara");
        assertEquals(39.92, restResponseGeoDto.getLat().setScale(2, RoundingMode.HALF_UP).doubleValue());
        assertEquals(32.85, restResponseGeoDto.getLon().setScale(2, RoundingMode.HALF_UP).doubleValue());
        assertEquals("TR", restResponseGeoDto.getCountry());

        restResponseGeoDto = restService.geoRequest("Mumbai");
        assertEquals(19.08, restResponseGeoDto.getLat().setScale(2, RoundingMode.HALF_UP).doubleValue());
        assertEquals(72.88, restResponseGeoDto.getLon().setScale(2, RoundingMode.HALF_UP).doubleValue());
        assertEquals("IN", restResponseGeoDto.getCountry());

        restResponseGeoDto = restService.geoRequest("London");
        assertEquals(51.51, restResponseGeoDto.getLat().setScale(2, RoundingMode.HALF_UP).doubleValue());
        assertEquals(-0.13, restResponseGeoDto.getLon().setScale(2, RoundingMode.HALF_UP).doubleValue());
        assertEquals("GB", restResponseGeoDto.getCountry());
    }

    @Test
    void shouldGetPollutantDataFromAPI() {
        // Get Ankara's pollutant data Ankara(lat=39.9207886,lon=32.8540482) for today
        RestResponsePolDto restResponsePolDto = restService.
                polRequest(BigDecimal.valueOf(39.9207886), BigDecimal.valueOf(32.8540482), new Date(), new Date());

        assertNotNull(restResponsePolDto);
    }


}