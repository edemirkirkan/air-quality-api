package com.edemirkirkan.airqualityapi.pol.dto;


import lombok.Data;

import java.util.List;

@Data
public class PolPollutionResultDto {
    private String date;
    private List<Object> categories;
}
