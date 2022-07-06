package com.edemirkirkan.airqualityapi.gen.exceptions;

import com.edemirkirkan.airqualityapi.gen.base.BaseErrorMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@RequiredArgsConstructor
public class BusinessException extends RuntimeException{
    private final BaseErrorMessage baseErrorMessage;
}