package com.edemirkirkan.airqualityapi.gen.exceptions;

import com.edemirkirkan.airqualityapi.log.dto.LogInfoDto;
import com.edemirkirkan.airqualityapi.log.service.LogInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNullApi;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;

@RestController
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final LogInfoService logInfoService;

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webRequest){

        Date errorDate = new Date();
        String message = ex.getMessage();
        String description = webRequest.getDescription(false);

        return getResponseEntity(errorDate, message, description, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleGenBusinessExceptions(BusinessException ex, WebRequest webRequest){

        Date errorDate = new Date();
        String message = ex.getBaseErrorMessage().getMessage();
        String description = webRequest.getDescription(false);

        return getResponseEntity(errorDate, message, description, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler
    public final ResponseEntity<Object> handleItemNotFoundExceptions(ItemNotFoundException ex, WebRequest webRequest){

        Date errorDate = new Date();
        String message = ex.getBaseErrorMessage().getMessage();
        String description = webRequest.getDescription(false);

        return getResponseEntity(errorDate, message, description, HttpStatus.NOT_FOUND);
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        Date errorDate = new Date();
        String message = "Validation failed!";

        StringBuilder description = new StringBuilder();
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        if (!errorList.isEmpty()){

            for (ObjectError objectError : errorList) {
                String defaultMessage = objectError.getDefaultMessage();

                description.append(defaultMessage).append("\n");
            }
        } else {
            description = new StringBuilder(ex.getBindingResult().toString());
        }

        return getResponseEntity(errorDate, message, description.toString(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getResponseEntity(Date errorDate, String message, String description, HttpStatus internalServerError) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(errorDate, message, description);

        RestResponse<ExceptionResponse> restResponse = RestResponse.error(exceptionResponse);
        restResponse.setMessages(message);

        ResponseEntity<RestResponse<ExceptionResponse>> logResponse = new ResponseEntity<>(restResponse, internalServerError);

        logAndSave(logResponse);

        return new ResponseEntity<>(restResponse, internalServerError);
    }

    private void logAndSave(ResponseEntity<RestResponse<ExceptionResponse>> response) {

        String errorMessageBody = getObjectString(response.getBody());
        String errorHeaders = getObjectString(response.getHeaders());

        LogInfoDto logErrorSaveDto = LogInfoDto.builder()
                .httpStatus(response.getStatusCode())
                .headers(errorHeaders)
                .body(errorMessageBody)
                .build();

        logInfoService.saveLog(logErrorSaveDto);
    }

    private String getObjectString(Object object) {
        String errorMessageBody = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            errorMessageBody = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return errorMessageBody;
    }

}
