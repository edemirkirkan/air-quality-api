package com.edemirkirkan.airqualityapi.rst.dto;

import lombok.Data;

import java.util.List;

@Data
public class RestResponsePolDto {
    private List<RestResponsePolDayDto> list;
}
