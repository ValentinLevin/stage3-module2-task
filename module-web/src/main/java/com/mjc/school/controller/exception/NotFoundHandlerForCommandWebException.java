package com.mjc.school.controller.exception;

import com.mjc.school.controller.commands.constant.RESULT_CODE;

public class NotFoundHandlerForCommandWebException extends CustomWebRuntimeException {
    private static final String MESSAGE_TEMPLATE = "Not found handler of action %s for entity %s";

    public NotFoundHandlerForCommandWebException(RESULT_CODE resultCode, String entity, String command) {
        super(resultCode, String.format(MESSAGE_TEMPLATE, command, entity));
    }
}
