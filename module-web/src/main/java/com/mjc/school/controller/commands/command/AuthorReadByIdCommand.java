package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.COMMANDS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.AuthorDTO;

import java.util.List;

public class AuthorReadByIdCommand extends Command<AuthorDTO>{
    private final Long id;

    public AuthorReadByIdCommand(Long id) {
        super(ENTITIES.AUTHOR, COMMANDS.READ_BY_ID);
        this.id = id;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{this.id};
    }
}
