package com.edemirkirkan.airqualityapi.gen.exceptions;

import com.edemirkirkan.airqualityapi.gen.base.BaseErrorMessage;

public enum ErrorMessage implements BaseErrorMessage {

    ITEM_NOT_FOUND("Item not found!"),
    PARAMETER_CANNOT_BE_NULL("Parameter cannot be null");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
