package com.mjc.school.controller.commands.constant;

import lombok.Getter;

@Getter
public enum ACTIONS {
    READ_ALL("readAll"),
    READ_BY_ID("readById"),
    DELETE_BY_ID("deleteById"),
    UPDATE("update"),
    CREATE("create");

    private final String name;

    ACTIONS(String name) {
        this.name = name;
    }
}
