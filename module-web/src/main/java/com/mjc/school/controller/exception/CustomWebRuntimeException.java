package com.mjc.school.controller.exception;

import com.mjc.school.controller.commands.constant.RESULT_CODE;

public class CustomWebRuntimeException extends RuntimeException {
    private static final String DEFAULT_MESSAGE_TEMPLATE = "An unexpected error occurred while processing the request";
    private final RESULT_CODE resultCode;

    public CustomWebRuntimeException() {
        this(RESULT_CODE.UNEXPECTED_ERROR, DEFAULT_MESSAGE_TEMPLATE);
    }

    public CustomWebRuntimeException(RESULT_CODE resultCode) {
        this(resultCode, DEFAULT_MESSAGE_TEMPLATE);
    }

    public CustomWebRuntimeException(RESULT_CODE resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }
}
