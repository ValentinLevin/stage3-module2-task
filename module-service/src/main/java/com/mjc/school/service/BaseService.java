package com.mjc.school.service;

import com.mjc.school.service.exception.*;

import java.util.List;

public interface BaseService<T, R, K> {
    List<R> readAll();

    R readById(K id) throws NullNewsIdServiceException, NewsNotFoundServiceException, NullAuthorIdServiceException, AuthorNotFoundServiceException;

    R create(T createRequest) throws DTOValidationServiceException, NullAuthorIdServiceException, AuthorNotFoundServiceException, RequestNullReferenceException, NullNewsIdServiceException, NewsNotFoundServiceException;

    R update(T updateRequest) throws RequestNullReferenceException, NullAuthorIdServiceException, AuthorNotFoundServiceException, DTOValidationServiceException, NullNewsIdServiceException, NewsNotFoundServiceException;

    boolean deleteById(K id) throws NullNewsIdServiceException, NewsNotFoundServiceException, AuthorNotFoundServiceException, NullAuthorIdServiceException;

    boolean existsById(K id) throws NullAuthorIdServiceException, NullNewsIdServiceException;
}
