package com.mjc.school.service.exception;

public class RequestNullReferenceException extends CustomServiceException {
    private static final String EXCEPTION_MESSAGE_TEMPLATE = "Null was passed as an request to be processed";

    public RequestNullReferenceException() {
        super(String.format(EXCEPTION_MESSAGE_TEMPLATE));
    }
}
