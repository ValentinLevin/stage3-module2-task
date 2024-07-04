package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.AuthorDTO;

import java.util.List;

public class AuthorReadAllCommand extends Command<List<AuthorDTO>>{
    public AuthorReadAllCommand() {
        super(ENTITIES.AUTHOR, ACTIONS.READ_ALL);
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
