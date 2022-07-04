package com.edemirkirkan.airqualityapi.gen.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RestController
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

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

        return new ResponseEntity<>(restResponse, internalServerError);
    }

}
