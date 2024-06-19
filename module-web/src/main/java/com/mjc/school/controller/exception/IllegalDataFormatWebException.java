package com.mjc.school.controller.exception;

public class IllegalDataFormatWebException extends CustomWebException {
    private static final String MESSAGE_TEMPLATE_WITH_DATA = "Error in request data format, received data %s";

    public IllegalDataFormatWebException(String data) {
        super(String.format(MESSAGE_TEMPLATE_WITH_DATA, data));
    }
}
