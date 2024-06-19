package com.mjc.school.controller.exception;

import lombok.Getter;

@Getter
public class CustomWebException extends Exception {
    public CustomWebException(String message) {
        super(message);
    }
}
