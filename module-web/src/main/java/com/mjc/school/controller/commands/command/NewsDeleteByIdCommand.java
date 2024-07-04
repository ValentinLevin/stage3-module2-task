package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import lombok.Builder;

@Builder
public class NewsDeleteByIdCommand extends Command<Boolean>{
    private Long id;

    public NewsDeleteByIdCommand(Long id) {
        super(ENTITIES.NEWS, ACTIONS.DELETE_BY_ID);
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
