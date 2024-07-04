package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.dto.NewsRequestDTO;

public class NewsCreateCommand extends Command<NewsDTO>{
    private final NewsRequestDTO requestDTO;

    public NewsCreateCommand(NewsRequestDTO requestDTO) {
        super(ENTITIES.NEWS, ACTIONS.CREATE);
        this.requestDTO = requestDTO;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{this.requestDTO};
    }
}
