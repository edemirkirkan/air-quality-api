package com.edemirkirkan.airqualityapi.rest.dto;

import com.edemirkirkan.airqualityapi.cty.dto.CtyCityDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RestResponseGeoDto {
    private String name;
    private BigDecimal lat;
    private BigDecimal lon;
    private String country;
}
