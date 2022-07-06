package com.edemirkirkan.airqualityapi.log.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class LogInfoDto {
    private HttpStatus httpStatus;
    private String body;
    private String headers;
}