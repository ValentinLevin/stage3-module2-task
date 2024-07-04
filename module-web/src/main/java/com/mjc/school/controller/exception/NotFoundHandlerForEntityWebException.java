package com.mjc.school.controller.exception;

import com.mjc.school.controller.commands.constant.RESULT_CODE;

public class NotFoundHandlerForEntityWebException extends CustomWebRuntimeException {
    private static final String MESSAGE_TEMPLATE = "Not found handler for entity %s";

    public NotFoundHandlerForEntityWebException(RESULT_CODE resultCode, String entity) {
        super(resultCode, String.format(MESSAGE_TEMPLATE, entity));
    }
}
