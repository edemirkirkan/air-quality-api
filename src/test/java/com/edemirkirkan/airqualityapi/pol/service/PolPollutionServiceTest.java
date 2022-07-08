package com.edemirkirkan.airqualityapi.pol.service;

import com.edemirkirkan.airqualityapi.gen.exceptions.ItemNotFoundException;
import com.edemirkirkan.airqualityapi.pol.entity.PolPollution;
import com.edemirkirkan.airqualityapi.pol.service.entityservice.PolPollutionEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PolPollutionServiceTest {
    @Mock
    private PolPollutionEntityService polPollutionEntityService;

    @Spy
    @InjectMocks
    private PolPollutionService polPollutionService;

    @Test
    void shouldThrowExceptionWhenOneOfDatesIsNull() {
        assertThrows(NullPointerException.class, () -> polPollutionService.getPollutionData("Ankara", null, "25-16-2021"));
        assertThrows(NullPointerException.class, () -> polPollutionService.getPollutionData("Ankara", "21-13-2030", null));
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