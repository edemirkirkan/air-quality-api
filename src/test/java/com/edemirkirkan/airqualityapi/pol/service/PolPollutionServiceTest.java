package com.edemirkirkan.airqualityapi.pol.service;

import com.edemirkirkan.airqualityapi.cty.service.CtyCityService;
import com.edemirkirkan.airqualityapi.gen.exceptions.BusinessException;
import com.edemirkirkan.airqualityapi.gen.exceptions.ItemNotFoundException;
import com.edemirkirkan.airqualityapi.pol.converter.PolPollutionConverter;
import com.edemirkirkan.airqualityapi.pol.dto.PolPollutionResponseDto;
import com.edemirkirkan.airqualityapi.pol.entity.PolPollution;
import com.edemirkirkan.airqualityapi.pol.enums.EnumPolPollutionErrorMessage;
import com.edemirkirkan.airqualityapi.pol.service.entityservice.PolPollutionEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PolPollutionServiceTest {
    @Mock
    private PolPollutionEntityService polPollutionEntityService;

    @InjectMocks
    private PolPollutionService polPollutionService;

    @Test
    void shouldGetPollutionData() {
        String city = "Ankara";
        String startDate ="18-02-2021";
        String endDate = "25-02-2021";
        PolPollutionResponseDto polPollutionResponseDto = polPollutionService.getPollutionData(city, startDate, endDate);
        assertNull(polPollutionResponseDto);
    }

    @Test
    void shouldNotGetPollutionDataWhenStartDateIsBefore() {
        String city = "Ankara";
        String startDate ="25-02-2021";
        String endDate = "18-02-2021";
        when(polPollutionService.getPollutionData(city, startDate, endDate)).thenThrow(BusinessException.class);
        assertThrows(BusinessException.class,
                () ->  polPollutionService.getPollutionData(city, startDate, endDate));
    }


    @Test
    void shouldDeletePollutionData() {
        PolPollution polPollution = mock(PolPollution.class);

        when(polPollutionEntityService.findByDateAndCityName(anyString(), anyString())).thenReturn(polPollution);

        doNothing().when(polPollutionEntityService).delete(any());

        polPollutionService.deletePollutionData("Ankara", "22-02-2020");

        verify(polPollutionEntityService).findByDateAndCityName(anyString(), anyString());
        verify(polPollutionEntityService).delete(any());
    }

    @Test
    void shouldNotDeletePollutionDataWhenEntryDoesNotExist() {
        when(polPollutionEntityService.findByDateAndCityName(anyString(), anyString()))
                .thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> polPollutionService
                .deletePollutionData("Ankara", "10-03-2021"));

        verify(polPollutionEntityService).findByDateAndCityName(anyString(), anyString());
    }


}