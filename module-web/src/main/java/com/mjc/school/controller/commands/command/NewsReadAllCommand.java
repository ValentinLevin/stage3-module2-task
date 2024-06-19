package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.COMMANDS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.NewsDTO;

import java.util.List;

public class NewsReadAllCommand extends Command<List<NewsDTO>>{
    public NewsReadAllCommand() {
        super(ENTITIES.NEWS, COMMANDS.READ_ALL);
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }
}
