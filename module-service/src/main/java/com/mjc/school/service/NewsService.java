package com.mjc.school.service;

import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.exception.NewsNotFoundServiceException;
import com.mjc.school.service.exception.NullAuthorIdServiceException;
import com.mjc.school.service.exception.NullNewsIdServiceException;

public interface NewsService extends BaseService<NewsRequestDTO, NewsDTO, Long> {
    void deleteAllByAuthorId(Long authorId) throws NullAuthorIdServiceException, NullNewsIdServiceException, NewsNotFoundServiceException;
}
