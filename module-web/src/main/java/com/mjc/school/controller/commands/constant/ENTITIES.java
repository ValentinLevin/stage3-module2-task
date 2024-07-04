package com.mjc.school.controller.commands.constant;

import lombok.Getter;

@Getter
public enum ENTITIES {
    AUTHOR("author"),
    NEWS("news");

    private final String name;

    ENTITIES(String name) {
        this.name = name;
    }
}
