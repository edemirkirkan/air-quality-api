package com.edemirkirkan.airqualityapi.pol.dto;

import com.edemirkirkan.airqualityapi.cty.entity.CtyCity;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PolPollutionDto {
    private String date;
    private CtyCity ctyCity;
    private BigDecimal co;
    private BigDecimal o3;
    private BigDecimal so2;
}
