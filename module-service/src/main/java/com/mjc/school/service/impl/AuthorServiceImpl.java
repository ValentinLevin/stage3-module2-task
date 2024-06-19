package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.exception.EntityNotFoundException;
import com.mjc.school.repository.exception.EntityNullReferenceException;
import com.mjc.school.repository.exception.KeyNullReferenceException;
import com.mjc.school.repository.model.AuthorEntity;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.aop.validation.ValidateDTO;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.exception.*;
import com.mjc.school.service.mapper.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    @Autowired
    @Lazy
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    private NewsService newsService;

    public AuthorServiceImpl(
            AuthorRepository repository,
            AuthorMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<AuthorDTO> readAll() {
        return this.repository.readAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public AuthorDTO readById(Long id) throws NullAuthorIdServiceException, AuthorNotFoundServiceException {
        AuthorEntity authorEntity;
        try {
            authorEntity =
                    repository
                            .readById(id)
                            .orElseThrow(() -> new AuthorNotFoundServiceException(id));
        } catch (EntityNotFoundException e) {
            throw new AuthorNotFoundServiceException(id);
        } catch (KeyNullReferenceException e) {
            throw new NullAuthorIdServiceException();
        }

        return mapper.toDTO(authorEntity);
    }

    @Override
    @ValidateDTO(ignoreFields = "id")
    public AuthorDTO create(
            AuthorRequestDTO createRequest
    ) throws DTOValidationServiceException, NullAuthorIdServiceException, AuthorNotFoundServiceException, RequestNullReferenceException {
        AuthorEntity entity = mapper.toEntity(createRequest);

        try {
            entity = this.repository.create(entity);
        } catch (EntityNullReferenceException e) {
            throw new DTOValidationServiceException(e.getMessage());
        }

        return readById(entity.getId());
    }

    @Override
    @ValidateDTO
    public AuthorDTO update(
            AuthorRequestDTO updateRequest
    ) throws RequestNullReferenceException, NullAuthorIdServiceException, AuthorNotFoundServiceException, DTOValidationServiceException {
        AuthorEntity entity = mapper.toEntity(updateRequest);

        try {
            entity = this.repository.update(entity);
        } catch (KeyNullReferenceException e) {
            throw new NullAuthorIdServiceException();
        } catch (EntityNotFoundException e) {
            throw new AuthorNotFoundServiceException(updateRequest.getId());
        } catch (EntityNullReferenceException e) {
            throw new DTOValidationServiceException(e.getMessage());
        }

        return readById(entity.getId());
    }

    @Override
    public boolean deleteById(Long id) throws NullAuthorIdServiceException, AuthorNotFoundServiceException, NullNewsIdServiceException, NewsNotFoundServiceException {
        try {
            newsService.deleteAllByAuthorId(id);
            return this.repository.deleteById(id);
        } catch (KeyNullReferenceException e) {
            throw new NullAuthorIdServiceException();
        } catch (EntityNotFoundException e) {
            throw new AuthorNotFoundServiceException(id);
        }
    }

    @Override
    public boolean existsById(Long id) throws NullAuthorIdServiceException {
        try {
            return this.repository.existById(id);
        } catch (KeyNullReferenceException e ) {
            throw new NullAuthorIdServiceException();
        }
    }
}
