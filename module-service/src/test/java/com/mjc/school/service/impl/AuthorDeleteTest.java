package com.mjc.school.service.impl;

import com.mjc.school.repository.datasource.impl.AuthorDataSource;
import com.mjc.school.repository.datasource.impl.NewsDataSource;
import com.mjc.school.repository.impl.AuthorRepositoryImpl;
import com.mjc.school.repository.impl.NewsRepositoryImpl;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.exception.CustomServiceException;
import com.mjc.school.service.mapper.AuthorMapperImpl;
import com.mjc.school.service.mapper.NewsMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = {
        NewsServiceImpl.class, AuthorServiceImpl.class,
        NewsRepositoryImpl.class, AuthorRepositoryImpl.class,
        AuthorDataSource.class, NewsDataSource.class,
        AuthorMapperImpl.class, NewsMapperImpl.class
})
class AuthorDeleteTest {
    @Autowired
    private NewsService newsService;

    @Autowired
    private AuthorService authorService;

    @Test
    void newsDeleteOnAuthorDelete() throws CustomServiceException {
        List<NewsDTO> startNewsList = newsService.readAll();
        List<Long> authorIdList =
                startNewsList.stream().map(item -> item.getAuthor().getId()).distinct().toList();
        Long idForDelete = authorIdList.get(new Random().nextInt(authorIdList.size()));

        authorService.deleteById(idForDelete);

        List<NewsDTO> finishNewsList = newsService.readAll();

        assertThat(finishNewsList)
                .extracting("author.id")
                .doesNotContain(idForDelete);
    }
}
