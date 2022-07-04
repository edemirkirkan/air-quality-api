package com.edemirkirkan.airqualityapi.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class RestResponsePolDto {
    private List<RestResponsePolDayDto> list;
}
