package com.edemirkirkan.airqualityapi.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestResponsePolComponentDto {
    private BigDecimal co;
    private BigDecimal o3;
    private BigDecimal so2;
}
