package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.AuthorDTO;
import lombok.Builder;

@Builder
public class AuthorReadByIdCommand extends Command<AuthorDTO>{
    private final Long id;

    public AuthorReadByIdCommand(Long id) {
        super(ENTITIES.AUTHOR, ACTIONS.READ_BY_ID);
        this.id = id;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{this.id};
    }

    @Override
    public void printResult() {
        System.out.println(getResult());
    }
}
