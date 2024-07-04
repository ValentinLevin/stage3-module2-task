package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import lombok.Builder;

@Builder
public class AuthorDeleteByIdCommand extends Command<Boolean>{
    private Long id;

    public AuthorDeleteByIdCommand(Long id) {
        super(ENTITIES.AUTHOR, ACTIONS.DELETE_BY_ID);
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
