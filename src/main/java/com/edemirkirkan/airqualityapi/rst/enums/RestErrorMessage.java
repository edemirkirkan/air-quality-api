package com.edemirkirkan.airqualityapi.rst.enums;

import com.edemirkirkan.airqualityapi.gen.base.BaseErrorMessage;

public enum RestErrorMessage implements BaseErrorMessage {

    WRONG_URL_SYNTAX("Wrong url syntax!"),
    RESPONSE_NULL("Response is null!"),
    RESPONSE_EMPTY("Response doesn't contain any element!");

    private final String message;

    RestErrorMessage(String message) {
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
