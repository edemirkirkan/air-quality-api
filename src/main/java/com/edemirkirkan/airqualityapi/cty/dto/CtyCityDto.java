package com.edemirkirkan.airqualityapi.cty.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CtyCityDto {
    private String name;
    private BigDecimal lat;
    private BigDecimal lon;
    private String country;
}
