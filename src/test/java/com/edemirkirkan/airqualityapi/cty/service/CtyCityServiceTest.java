package com.edemirkirkan.airqualityapi.cty.service;

import com.edemirkirkan.airqualityapi.cty.converter.CtyCityMapper;
import com.edemirkirkan.airqualityapi.cty.dto.CtyCityDto;
import com.edemirkirkan.airqualityapi.cty.entity.CtyCity;
import com.edemirkirkan.airqualityapi.cty.service.entityservice.CtyCityEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CtyCityServiceTest {
    @Mock
    private CtyCityMapper ctyCityMapper;
    @Mock
    private CtyCityEntityService ctyCityEntityService;

    @Test
    void shouldNotSave() {
        CtyCityDto ctyCityDto = mock(CtyCityDto.class);

        assertThrows(NullPointerException.class, () -> ctyCityMapper.convertToCtyCity(ctyCityDto));
    }

    @Test
    void shouldNotFindByName() {
        CtyCity ctyCity = mock(CtyCity.class);
        when(ctyCity.getName()).thenReturn("ege");

        assertThrows(NullPointerException.class, () -> ctyCityEntityService.findByName("ege"));
    }
}