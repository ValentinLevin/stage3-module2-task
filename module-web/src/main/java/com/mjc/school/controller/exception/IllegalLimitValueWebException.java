package com.mjc.school.controller.exception;

import com.mjc.school.controller.commands.constant.RESULT_CODE;

public class IllegalLimitValueWebException extends CustomWebRuntimeException {
    private static final String INVALID_FORMAT_MESSAGE_TEMPLATE = "The limit value is in invalid %s";
    private static final String INVALID_VALUE_MESSAGE_TEMPLATE =
            "Received limit value is %d. The limit value must be greater than or equal to zero";

    public IllegalLimitValueWebException(RESULT_CODE resultCode, String limitValue) {
        super(resultCode, String.format(INVALID_FORMAT_MESSAGE_TEMPLATE, limitValue));
    }

    public IllegalLimitValueWebException(RESULT_CODE resultCode, Integer limitValue) {
        super(resultCode, String.format(INVALID_VALUE_MESSAGE_TEMPLATE, limitValue));
    }
}
