package com.edemirkirkan.airqualityapi.pol.dto;

import lombok.Data;

import java.util.List;

@Data
public class PolPollutionResponseDto {
    private String city;
    private List<PolPollutionResultDto> results;
}
