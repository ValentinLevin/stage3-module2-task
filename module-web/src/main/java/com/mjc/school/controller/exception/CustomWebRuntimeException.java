package com.mjc.school.controller.exception;

public class CustomWebRuntimeException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "An unexpected error occurred while processing the request";

    public CustomWebRuntimeException() {
        super(MESSAGE_TEMPLATE);
    }
}
