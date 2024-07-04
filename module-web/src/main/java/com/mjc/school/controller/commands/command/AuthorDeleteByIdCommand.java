package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;

public class AuthorDeleteByIdCommand extends Command<Boolean>{
    private final Long id;

    public AuthorDeleteByIdCommand(Long id) {
        super(ENTITIES.AUTHOR, ACTIONS.DELETE_BY_ID);
        this.id = id;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{this.id};
    }
}
