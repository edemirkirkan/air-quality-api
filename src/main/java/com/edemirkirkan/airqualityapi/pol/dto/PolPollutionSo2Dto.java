package com.edemirkirkan.airqualityapi.pol.dto;

import com.edemirkirkan.airqualityapi.pol.enums.EnumPolPollutionQualityType;
import lombok.Data;

@Data
public class PolPollutionSo2Dto {
    private EnumPolPollutionQualityType SO2;
}
