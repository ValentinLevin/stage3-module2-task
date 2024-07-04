package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.commands.annotations.CommandBody;
import com.mjc.school.controller.commands.annotations.CommandHandler;
import com.mjc.school.controller.commands.annotations.CommandParam;
import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.controller.commands.constant.RESULT_CODE;
import com.mjc.school.controller.exception.AuthorNotFoundWebRuntimeException;
import com.mjc.school.controller.exception.CustomWebRuntimeException;
import com.mjc.school.controller.exception.IllegalAuthorIdValueWebException;
import com.mjc.school.controller.exception.IllegalDataFormatWebException;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.exception.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorController implements BaseController<AuthorRequestDTO, AuthorDTO, Long> {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @CommandHandler(entity = ENTITIES.AUTHOR, command = ACTIONS.READ_ALL)
    @Override
    public List<AuthorDTO> readAll() {
        return this.authorService.readAll();
    }

    @CommandHandler(entity = ENTITIES.AUTHOR, command = ACTIONS.READ_BY_ID)
    @Override
    public AuthorDTO readById(@CommandParam("id") Long id) {
        try {
            return this.authorService.readById(id);
        } catch (NullAuthorIdServiceException e) {
            throw new AuthorNotFoundWebRuntimeException(RESULT_CODE.ILLEGAL_ID_VALUE, id);
        } catch (AuthorNotFoundServiceException e) {
            throw new AuthorNotFoundWebRuntimeException(RESULT_CODE.AUTHOR_NOT_FOUND, id);
        }
    }

    @CommandHandler(entity = ENTITIES.AUTHOR, command = ACTIONS.CREATE)
    @Override
    public AuthorDTO create(@CommandBody AuthorRequestDTO createRequest) {
        try {
            return authorService.create(createRequest);
        } catch (DTOValidationServiceException e) {
            throw new IllegalDataFormatWebException(RESULT_CODE.ILLEGAL_DATA_FORMAT, createRequest.toString());
        } catch (RequestNullReferenceException e) {
            throw new IllegalDataFormatWebException(RESULT_CODE.ILLEGAL_DATA_FORMAT, null);
        } catch (NullAuthorIdServiceException | AuthorNotFoundServiceException e) {
            throw new CustomWebRuntimeException();
        }
    }

    @Override
    @CommandHandler(entity = ENTITIES.AUTHOR, command = ACTIONS.UPDATE)
    public AuthorDTO update(@CommandBody AuthorRequestDTO updateRequest) {
        try {
            return authorService.update(updateRequest);
        } catch (DTOValidationServiceException e) {
            throw new IllegalDataFormatWebException(RESULT_CODE.ILLEGAL_DATA_FORMAT, updateRequest.toString());
        } catch (RequestNullReferenceException e) {
            throw new IllegalDataFormatWebException(RESULT_CODE.ILLEGAL_DATA_FORMAT, null);
        } catch (NullAuthorIdServiceException | AuthorNotFoundServiceException e) {
            throw new CustomWebRuntimeException();
        }
    }

    @Override
    @CommandHandler(entity = ENTITIES.AUTHOR, command = ACTIONS.DELETE_BY_ID)
    public boolean deleteById(Long id) {
        try {
            return authorService.deleteById(id);
        } catch (NullAuthorIdServiceException e) {
            throw new IllegalAuthorIdValueWebException(RESULT_CODE.ILLEGAL_ID_VALUE, null);
        } catch (AuthorNotFoundServiceException e) {
            throw new AuthorNotFoundWebRuntimeException(RESULT_CODE.AUTHOR_NOT_FOUND, id);
        } catch (NullNewsIdServiceException | NewsNotFoundServiceException e) {
            throw new CustomWebRuntimeException();
        }
    }
}
