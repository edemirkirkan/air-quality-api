package com.edemirkirkan.airqualityapi.rst.dto;

import lombok.Data;

@Data
public class RestResponsePolDayDto {
    private Long dt;
    private RestResponsePolComponentDto components;
}
