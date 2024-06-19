package com.mjc.school.controller.exception;

public class IllegalNewsIdValueWebException extends CustomWebException {
    private static final String MESSAGE_TEMPLATE = "The news id value is invalid %s";

    public IllegalNewsIdValueWebException(String idValue) {
        super(String.format(MESSAGE_TEMPLATE, idValue));
    }
}
