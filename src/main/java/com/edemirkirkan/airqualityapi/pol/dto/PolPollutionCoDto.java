package com.edemirkirkan.airqualityapi.pol.dto;

import com.edemirkirkan.airqualityapi.pol.enums.EnumPolPollutionQualityType;
import lombok.Data;

@Data
public class PolPollutionCoDto {
    private EnumPolPollutionQualityType CO;
}
