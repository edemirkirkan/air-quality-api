package com.edemirkirkan.airqualityapi.cty.service;

import com.edemirkirkan.airqualityapi.cty.converter.CtyCityMapper;
import com.edemirkirkan.airqualityapi.cty.dto.CtyCityDto;
import com.edemirkirkan.airqualityapi.cty.entity.CtyCity;
import com.edemirkirkan.airqualityapi.cty.service.entityservice.CtyCityEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CtyCityService {
    private final CtyCityMapper ctyCityMapper;
    private final CtyCityEntityService ctyCityEntityService;

    public CtyCity save(CtyCityDto ctyCityDto) {
        CtyCity ctyCity = ctyCityMapper.convertToCtyCity(ctyCityDto);
        ctyCity = ctyCityEntityService.save(ctyCity);
        return ctyCity;
    }

    public CtyCity findByName(String name) {
        return ctyCityEntityService.findByName(name);
    }



}
