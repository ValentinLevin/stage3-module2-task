package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.NewsDTO;

import java.util.List;

public class NewsReadAllCommand extends Command<List<NewsDTO>>{
    public NewsReadAllCommand() {
        super(ENTITIES.NEWS, ACTIONS.READ_ALL);
    }

    @Override
    public Object[] getArgs() {
        return new Object[0];
    }

    @Override
    public void printResult() {
        getResult().forEach(System.out::println);
    }
}
