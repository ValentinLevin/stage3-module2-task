package com.mjc.school.controller.commands.constant;

import lombok.Getter;

@Getter
public enum RESULT_CODE {
    SUCCESS(0),
    ADD_SUCCESS(0),
    UNEXPECTED_ERROR(1, "An unexpected error occurred while processing the request"),
    NEWS_NOT_FOUND(2),
    AUTHOR_NOT_FOUND(3),
    REQUEST_IS_NOT_IN_UTF_ENCODING(4),
    ILLEGAL_REQUEST_DATA_FORMAT(5),
    DATA_VALIDATION(6),
    ILLEGAL_ID_VALUE(7),
    ILLEGAL_LIMIT_VALUE(8),
    ILLEGAL_OFFSET_VALUE(9),
    ILLEGAL_DATA_FORMAT(10),
    NOT_FOUND_HANDLER_FOR_ENTITY(11),
    NOT_FOUND_HANDLER_FOR_ACTION(12);

    private final int errorCode;
    private final String defaultMessage;

    RESULT_CODE(int errorCode) {
        this(errorCode, "");
    }

    RESULT_CODE(int errorCode, String defaultMessage) {
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }
}
