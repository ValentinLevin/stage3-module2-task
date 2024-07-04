package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.exception.*;

import java.util.List;

public interface AuthorService extends BaseService<AuthorRequestDTO, AuthorDTO, Long>{
    List<AuthorDTO> readAll();

    AuthorDTO readById(Long id) throws NullAuthorIdServiceException, AuthorNotFoundServiceException;

    AuthorDTO create(AuthorRequestDTO createRequest) throws DTOValidationServiceException, NullAuthorIdServiceException, AuthorNotFoundServiceException, RequestNullReferenceException;

    AuthorDTO update(AuthorRequestDTO updateRequest) throws RequestNullReferenceException, NullAuthorIdServiceException, AuthorNotFoundServiceException, DTOValidationServiceException;

    boolean deleteById(Long id) throws NullAuthorIdServiceException, AuthorNotFoundServiceException, NullNewsIdServiceException, NewsNotFoundServiceException;

    boolean existsById(Long id) throws NullAuthorIdServiceException;

}
