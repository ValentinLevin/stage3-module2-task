package com.mjc.school.service.impl;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.exception.EntityNotFoundException;
import com.mjc.school.repository.exception.EntityNullReferenceException;
import com.mjc.school.repository.exception.KeyNullReferenceException;
import com.mjc.school.repository.model.NewsEntity;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.aop.validation.ValidateDTO;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.exception.*;
import com.mjc.school.service.mapper.NewsMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository repository;
    private final AuthorService authorService;
    private final NewsMapper newsMapper;

    public NewsServiceImpl(
            NewsRepository repository,
            AuthorService authorService,
            NewsMapper newsMapper
    ) {
        this.repository = repository;
        this.authorService = authorService;
        this.newsMapper = newsMapper;
    }

    @Override
    public List<NewsDTO> readAll() {
        Map<Long, AuthorDTO> authors =
                authorService.readAll()
                        .stream()
                        .collect(Collectors.toMap(AuthorDTO::getId, author -> author));

        return this.repository.readAll()
                .stream()
                .map(entity -> {
                    NewsDTO dto = newsMapper.toDTO(entity);
                    AuthorDTO author = authors.get(entity.getAuthorId());
                    dto.setAuthor(author);
                    return dto;
                })
                .toList();
    }

    @Override
    public NewsDTO readById(Long id) throws NullNewsIdServiceException, NewsNotFoundServiceException, NullAuthorIdServiceException, AuthorNotFoundServiceException {
        NewsEntity newsEntity;
        try {
            newsEntity =
                    this.repository
                            .readById(id)
                            .orElseThrow(() -> new NewsNotFoundServiceException(id));
        } catch (KeyNullReferenceException e) {
            throw new NullNewsIdServiceException();
        } catch (EntityNotFoundException e) {
            throw new NewsNotFoundServiceException(id);
        }

        AuthorDTO authorDTO = this.authorService.readById(newsEntity.getAuthorId());

        NewsDTO newsDTO = newsMapper.toDTO(newsEntity);
        newsDTO.setAuthor(authorDTO);

        return newsDTO;
    }

    @Override
    @ValidateDTO(ignoreFields = "id")
    public NewsDTO create(NewsRequestDTO createRequest) throws RequestNullReferenceException, DTOValidationServiceException, NullNewsIdServiceException, NewsNotFoundServiceException, NullAuthorIdServiceException, AuthorNotFoundServiceException {
        if (!authorService.existsById(createRequest.getAuthorId())) {
            throw new AuthorNotFoundServiceException(createRequest.getAuthorId());
        }

        NewsEntity entity = newsMapper.toEntity(createRequest);

        entity.setCreateDate(LocalDateTime.now());
        entity.setLastUpdateDate(entity.getCreateDate());

        try {
            entity = this.repository.create(entity);
        } catch (EntityNullReferenceException e) {
            throw new DTOValidationServiceException(e.getMessage());
        }

        return readById(entity.getId());
    }

    @Override
    @ValidateDTO
    public NewsDTO update(NewsRequestDTO updateRequest) throws RequestNullReferenceException, NullAuthorIdServiceException, AuthorNotFoundServiceException, NullNewsIdServiceException, NewsNotFoundServiceException {
        if (!this.authorService.existsById(updateRequest.getAuthorId())) {
            throw new AuthorNotFoundServiceException(updateRequest.getAuthorId());
        }

        NewsEntity entity = newsMapper.toEntity(updateRequest);

        entity.setLastUpdateDate(LocalDateTime.now());

        try {
            entity = this.repository.update(entity);
        } catch (EntityNullReferenceException e) {
            throw new RequestNullReferenceException();
        } catch (KeyNullReferenceException e) {
            throw new NullNewsIdServiceException();
        } catch (EntityNotFoundException e) {
            throw new NewsNotFoundServiceException(updateRequest.getId());
        }

        return readById(entity.getId());
    }

    @Override
    public boolean deleteById(Long id) throws NullNewsIdServiceException, NewsNotFoundServiceException {
        try {
            return this.repository.deleteById(id);
        } catch (KeyNullReferenceException e) {
            throw new NullNewsIdServiceException();
        } catch (EntityNotFoundException e) {
            throw new NewsNotFoundServiceException(id);
        }
    }

    @Override
    public boolean existsById(Long id) throws NullNewsIdServiceException {
        try {
            return this.repository.existById(id);
        } catch (KeyNullReferenceException e) {
            throw new NullNewsIdServiceException();
        }
    }

    public void deleteAllByAuthorId(Long authorId) throws NullAuthorIdServiceException, NullNewsIdServiceException, NewsNotFoundServiceException {
        if (authorId == null) {
            throw new NullAuthorIdServiceException();
        }

        List<Long> newsIdForDelete =
                this.repository.readAll()
                        .stream()
                        .filter(item -> item.getAuthorId().equals(authorId))
                        .map(NewsEntity::getId)
                        .toList();

        for (Long id: newsIdForDelete) {
            try {
                repository.deleteById(id);
            } catch (EntityNotFoundException e) {
                throw new NewsNotFoundServiceException(id);
            } catch (KeyNullReferenceException e) {
                throw new NullNewsIdServiceException();
            }
        }
    }
}
