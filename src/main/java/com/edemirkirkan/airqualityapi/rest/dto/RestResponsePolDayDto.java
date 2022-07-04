package com.edemirkirkan.airqualityapi.rest.dto;

import lombok.Data;

@Data
public class RestResponsePolDayDto {
    private Long dt;
    private RestResponsePolComponentDto components;
}
