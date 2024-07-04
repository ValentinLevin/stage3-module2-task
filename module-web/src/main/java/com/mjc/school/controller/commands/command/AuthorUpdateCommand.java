package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.AuthorRequestDTO;

public class AuthorUpdateCommand extends Command<AuthorDTO>{
    private final AuthorRequestDTO authorRequestDTO;

    public AuthorUpdateCommand(AuthorRequestDTO authorRequestDTO) {
        super(ENTITIES.AUTHOR, ACTIONS.UPDATE);
        this.authorRequestDTO = authorRequestDTO;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{this.authorRequestDTO};
    }
}
