package com.edemirkirkan.airqualityapi.rst.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestResponseGeoDto {
    private String name;
    private BigDecimal lat;
    private BigDecimal lon;
    private String country;
}
