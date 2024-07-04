package com.mjc.school.controller.exception;

import com.mjc.school.controller.commands.constant.RESULT_CODE;

public class IllegalDataFormatWebException extends CustomWebRuntimeException {
    private static final String MESSAGE_TEMPLATE_WITH_DATA = "Error in request data format, received data %s";

    public IllegalDataFormatWebException(RESULT_CODE resultCode, String data) {
        super(resultCode, String.format(MESSAGE_TEMPLATE_WITH_DATA, data));
    }
}
