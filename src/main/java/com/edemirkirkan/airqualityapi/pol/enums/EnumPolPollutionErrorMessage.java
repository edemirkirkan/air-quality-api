package com.edemirkirkan.airqualityapi.pol.enums;

import com.edemirkirkan.airqualityapi.gen.base.BaseErrorMessage;

public enum EnumPolPollutionErrorMessage implements BaseErrorMessage {

    WRONG_DATE_FORMAT("Wrong date format!"),
    AVG_POLLUTANTS_AMOUNT_CANNOT_BE_NEGATIVE("Average pollutants amount cannot be negative!"),
    DATE_BEFORE_CUTOFF_CANNOT_BE_QUERIED("Dates before '27 November 2020' cannot be queried!"),
    ENTRY_CANNOT_FOUND("Entry with given city and date is not found!"),
    NOT_ACCEPTED_CITY("Given city isn't one of the accepted cities. List of accepted cities: [London, Barcelona, Ankara, Tokyo, Mumbai]");

    private final String message;

    EnumPolPollutionErrorMessage(String message) {
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