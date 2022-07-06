package com.edemirkirkan.airqualityapi.usr.enums;

import com.edemirkirkan.airqualityapi.gen.base.BaseErrorMessage;

public enum UsrErrorMessage implements BaseErrorMessage {

    PASSWORDS_NOT_MATCHING("New passwords are not matching!"),
    DUPLICATE_USERNAME("Username already exists!"),
    PASSWORD_LENGTH("Passwords should have a minimum of eight characters!"),
    WRONG_PASSWORD("Old password is not matching with the current user!");

    private final String message;

    UsrErrorMessage(String message) {
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
