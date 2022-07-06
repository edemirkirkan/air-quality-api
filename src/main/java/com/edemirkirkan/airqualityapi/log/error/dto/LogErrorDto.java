package com.edemirkirkan.airqualityapi.log.error.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class LogErrorDto {
    private HttpStatus httpStatus;
    private String body;
    private String headers;
}