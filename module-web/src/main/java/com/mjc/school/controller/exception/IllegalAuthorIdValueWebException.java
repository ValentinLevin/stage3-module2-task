package com.mjc.school.controller.exception;

import com.mjc.school.controller.commands.constant.RESULT_CODE;

public class IllegalAuthorIdValueWebException extends CustomWebRuntimeException {
    private static final String MESSAGE_TEMPLATE = "The author id value is invalid %s";

    public IllegalAuthorIdValueWebException(RESULT_CODE resultCode, String idValue) {
        super(resultCode, String.format(MESSAGE_TEMPLATE, idValue));
    }
}
