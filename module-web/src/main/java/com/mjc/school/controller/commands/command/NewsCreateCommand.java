package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import lombok.Builder;

@Builder
public class NewsCreateCommand extends Command<NewsDTO>{
    private String title;
    private String content;
    private Long authorId;

    private final NewsRequestDTO requestDTO = new NewsRequestDTO();

    public NewsCreateCommand(String title, String content, Long authorId) {
        super(ENTITIES.NEWS, ACTIONS.CREATE);
        this.requestDTO.setId(null);
        this.requestDTO.setTitle(title);
        this.requestDTO.setContent(content);
        this.requestDTO.setAuthorId(authorId);
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{this.requestDTO};
    }

    @Override
    public void printResult() {
        System.out.println(getResult());
    }
}
