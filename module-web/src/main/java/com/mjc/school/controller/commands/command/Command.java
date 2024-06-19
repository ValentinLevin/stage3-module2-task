package com.mjc.school.controller.commands.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Command<R> {
    private final String entity;
    private final String commandName;

    private R result;

    public abstract Object[] getArgs();

    Command(String entity, String commandName) {
        this.entity = entity;
        this.commandName = commandName;
    }
}
