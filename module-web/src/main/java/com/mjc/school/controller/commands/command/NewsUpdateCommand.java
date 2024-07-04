package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import lombok.Builder;

@Builder
public class NewsUpdateCommand extends Command<NewsDTO>{
    private Long id;
    private String title;
    private String content;
    private Long authorId;

    private final NewsRequestDTO requestDTO = new NewsRequestDTO();

    public NewsUpdateCommand(Long id, String title, String content, Long authorId) {
        super(ENTITIES.NEWS, ACTIONS.UPDATE);
        this.requestDTO.setId(id);
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
