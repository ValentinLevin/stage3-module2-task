package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.AuthorRequestDTO;

public class AuthorCreateCommand extends Command<AuthorDTO>{
    private final AuthorRequestDTO authorRequestDTO;

    public AuthorCreateCommand(AuthorRequestDTO authorRequestDTO) {
        super(ENTITIES.AUTHOR, ACTIONS.CREATE);
        this.authorRequestDTO = authorRequestDTO;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{this.authorRequestDTO};
    }
}
