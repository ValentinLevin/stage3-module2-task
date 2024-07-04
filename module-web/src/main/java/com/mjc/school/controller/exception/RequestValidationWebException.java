package com.mjc.school.controller.exception;

import com.mjc.school.controller.commands.constant.RESULT_CODE;

public class RequestValidationWebException extends CustomWebRuntimeException {
    private static final String MESSAGE_TEMPLATE = "The following errors were detected during the check: %s";

    public RequestValidationWebException(RESULT_CODE resultCode, String constrainViolations) {
        super(resultCode, String.format(MESSAGE_TEMPLATE, constrainViolations));
    }
}
