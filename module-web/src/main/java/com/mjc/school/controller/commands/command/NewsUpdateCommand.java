package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.dto.NewsRequestDTO;

public class NewsUpdateCommand extends Command<NewsDTO>{
    private final NewsRequestDTO requestDTO;

    public NewsUpdateCommand(NewsRequestDTO requestDTO) {
        super(ENTITIES.NEWS, ACTIONS.UPDATE);
        this.requestDTO = requestDTO;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{this.requestDTO};
    }
}
