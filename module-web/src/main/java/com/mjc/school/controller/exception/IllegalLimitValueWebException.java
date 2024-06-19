package com.mjc.school.controller.exception;

public class IllegalLimitValueWebException extends CustomWebException {
    private static final String INVALID_FORMAT_MESSAGE_TEMPLATE = "The limit value is in invalid %s";
    private static final String INVALID_VALUE_MESSAGE_TEMPLATE =
            "Received limit value is %d. The limit value must be greater than or equal to zero";

    public IllegalLimitValueWebException(String limitValue) {
        super(String.format(INVALID_FORMAT_MESSAGE_TEMPLATE, limitValue));
    }

    public IllegalLimitValueWebException(Integer limitValue) {
        super(String.format(INVALID_VALUE_MESSAGE_TEMPLATE, limitValue));
    }
}
