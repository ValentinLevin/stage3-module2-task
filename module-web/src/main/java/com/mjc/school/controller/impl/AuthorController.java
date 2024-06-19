package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.commands.annotations.CommandBody;
import com.mjc.school.controller.commands.annotations.CommandHandler;
import com.mjc.school.controller.commands.annotations.CommandParam;
import com.mjc.school.controller.commands.constant.COMMANDS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.exception.CustomServiceException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorController implements BaseController<AuthorRequestDTO, AuthorDTO, Long> {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @CommandHandler(entity = ENTITIES.AUTHOR, command = COMMANDS.READ_ALL)
    @Override
    public List<AuthorDTO> readAll() {
        return this.authorService.readAll();
    }

    @CommandHandler(entity = ENTITIES.AUTHOR, command = COMMANDS.READ_BY_ID)
    @Override
    public AuthorDTO readById(@CommandParam("id") Long id) {
        try {
            return this.authorService.readById(id);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @CommandHandler(entity = ENTITIES.AUTHOR, command = COMMANDS.CREATE)
    @Override
    public AuthorDTO create(@CommandBody AuthorRequestDTO createRequest) {
        try {
            return authorService.create(createRequest);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CommandHandler(entity = ENTITIES.AUTHOR, command = COMMANDS.UPDATE)
    public AuthorDTO update(@CommandBody AuthorRequestDTO updateRequest) {
        try {
            return authorService.create(updateRequest);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @CommandHandler(entity = ENTITIES.AUTHOR, command = COMMANDS.DELETE_BY_ID)
    public boolean deleteById(Long id) {
        try {
            return authorService.deleteById(id);
        } catch (CustomServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
