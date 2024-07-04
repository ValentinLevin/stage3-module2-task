package com.mjc.school.controller.commands.command;

import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.AuthorRequestDTO;
import lombok.Builder;

@Builder()
public class AuthorCreateCommand extends Command<AuthorDTO>{
    private String authorName;
    private final AuthorRequestDTO authorRequestDTO = new AuthorRequestDTO();

    public AuthorCreateCommand(String authorName) {
        super(ENTITIES.AUTHOR, ACTIONS.CREATE);
        this.authorRequestDTO.setName(authorName);
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{this.authorRequestDTO};
    }

    @Override
    public void printResult() {
        System.out.println(getResult());
    }
}
