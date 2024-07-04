package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.NewsDTO;
import lombok.Builder;

@Builder
public class NewsReadByIdCommand extends Command<NewsDTO>{
    private final Long id;

    public NewsReadByIdCommand(Long id) {
        super(ENTITIES.NEWS, ACTIONS.READ_BY_ID);
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
