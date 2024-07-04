package com.mjc.school.controller.exception;

import com.mjc.school.controller.commands.constant.RESULT_CODE;

public class AuthorNotFoundWebRuntimeException extends CustomWebRuntimeException {
    private static final String MESSAGE_TEMPLATE = "The author with id %d not found";

    public AuthorNotFoundWebRuntimeException(RESULT_CODE resultCode, Long idValue) {
        super(resultCode, String.format(MESSAGE_TEMPLATE, idValue));
    }
}
