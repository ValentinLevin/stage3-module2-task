package com.mjc.school.service;

import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.exception.*;

import java.util.List;

public interface NewsService extends BaseService<NewsRequestDTO, NewsDTO, Long> {
    void deleteAllByAuthorId(Long authorId) throws NullAuthorIdServiceException, NullNewsIdServiceException, NewsNotFoundServiceException;

    List<NewsDTO> readAll();

    NewsDTO readById(Long id) throws NullNewsIdServiceException, NewsNotFoundServiceException, NullAuthorIdServiceException, AuthorNotFoundServiceException;

    NewsDTO create(NewsRequestDTO createRequest) throws DTOValidationServiceException, RequestNullReferenceException, NullNewsIdServiceException, NewsNotFoundServiceException, NullAuthorIdServiceException, AuthorNotFoundServiceException;

    NewsDTO update(NewsRequestDTO updateRequest) throws DTOValidationServiceException, RequestNullReferenceException, NullAuthorIdServiceException, AuthorNotFoundServiceException, NullNewsIdServiceException, NewsNotFoundServiceException;

    boolean deleteById(Long id) throws NullNewsIdServiceException, NewsNotFoundServiceException;

    boolean existsById(Long id) throws NullNewsIdServiceException;

}
