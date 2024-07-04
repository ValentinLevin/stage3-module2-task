package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.commands.annotations.CommandBody;
import com.mjc.school.controller.commands.annotations.CommandHandler;
import com.mjc.school.controller.commands.annotations.CommandParam;
import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.controller.commands.constant.RESULT_CODE;
import com.mjc.school.controller.exception.*;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.exception.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsController implements BaseController<NewsRequestDTO, NewsDTO, Long> {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @CommandHandler(entity = ENTITIES.NEWS, command = ACTIONS.READ_ALL)
    @Override
    public List<NewsDTO> readAll() {
        return this.newsService.readAll();
    }

    @CommandHandler(entity = ENTITIES.NEWS, command = ACTIONS.READ_BY_ID)
    @Override
    public NewsDTO readById(@CommandParam("id") Long id) {
        try {
            return this.newsService.readById(id);
        } catch (NullNewsIdServiceException e) {
            throw new IllegalNewsIdValueWebException(RESULT_CODE.ILLEGAL_ID_VALUE, null);
        } catch (NewsNotFoundServiceException e) {
            throw new NewsNotFoundWebRuntimeException(RESULT_CODE.NEWS_NOT_FOUND, id);
        } catch (NullAuthorIdServiceException | AuthorNotFoundServiceException e) {
            throw new CustomWebRuntimeException();
        }
    }

    @CommandHandler(entity = ENTITIES.NEWS, command = ACTIONS.CREATE)
    @Override
    public NewsDTO create(@CommandBody NewsRequestDTO createRequest) {
        try {
            return newsService.create(createRequest);
        } catch (DTOValidationServiceException | NullAuthorIdServiceException e) {
            throw new IllegalDataFormatWebException(RESULT_CODE.ILLEGAL_DATA_FORMAT, createRequest.toString());
        } catch (AuthorNotFoundServiceException e) {
            throw new AuthorNotFoundWebRuntimeException(RESULT_CODE.AUTHOR_NOT_FOUND, createRequest.getAuthorId());
        } catch (RequestNullReferenceException e) {
            throw new IllegalDataFormatWebException(RESULT_CODE.ILLEGAL_DATA_FORMAT, null);
        } catch (NullNewsIdServiceException | NewsNotFoundServiceException e) {
            throw new CustomWebRuntimeException();
        }
    }

    @Override
    @CommandHandler(entity = ENTITIES.NEWS, command = ACTIONS.UPDATE)
    public NewsDTO update(@CommandBody NewsRequestDTO updateRequest) {
        try {
            return newsService.update(updateRequest);
        } catch (DTOValidationServiceException | NullAuthorIdServiceException e) {
            throw new IllegalDataFormatWebException(RESULT_CODE.ILLEGAL_DATA_FORMAT, updateRequest.toString());
        } catch (AuthorNotFoundServiceException e) {
            throw new AuthorNotFoundWebRuntimeException(RESULT_CODE.AUTHOR_NOT_FOUND, updateRequest.getAuthorId());
        } catch (RequestNullReferenceException e) {
            throw new IllegalDataFormatWebException(RESULT_CODE.ILLEGAL_DATA_FORMAT, null);
        } catch (NullNewsIdServiceException | NewsNotFoundServiceException e) {
            throw new CustomWebRuntimeException();
        }
    }

    @Override
    @CommandHandler(entity = ENTITIES.NEWS, command = ACTIONS.DELETE_BY_ID)
    public boolean deleteById(Long id) {
        try {
            return newsService.deleteById(id);
        } catch (NewsNotFoundServiceException e) {
            throw new NewsNotFoundWebRuntimeException(RESULT_CODE.NEWS_NOT_FOUND, id);
        } catch (NullNewsIdServiceException e) {
            throw new NewsNotFoundWebRuntimeException(RESULT_CODE.ILLEGAL_ID_VALUE, id);
        }
    }
}
