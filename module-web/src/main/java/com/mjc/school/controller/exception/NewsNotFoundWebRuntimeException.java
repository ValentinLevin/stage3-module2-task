package com.mjc.school.controller.exception;

import com.mjc.school.controller.commands.constant.RESULT_CODE;

public class NewsNotFoundWebRuntimeException extends CustomWebRuntimeException {
    private static final String MESSAGE_TEMPLATE = "The news with id %d not found";

    public NewsNotFoundWebRuntimeException(RESULT_CODE resultCode, Long idValue) {
        super(resultCode, String.format(MESSAGE_TEMPLATE, idValue));
    }
}
