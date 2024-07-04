package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Command<R> {
    private final ENTITIES entity;
    private final ACTIONS action;

    private R result;

    public abstract Object[] getArgs();

    Command(ENTITIES entity, ACTIONS action) {
        this.entity = entity;
        this.action = action;
    }
}
