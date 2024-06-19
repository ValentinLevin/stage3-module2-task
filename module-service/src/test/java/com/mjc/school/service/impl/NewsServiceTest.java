package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.NewsRepositoryImpl;
import com.mjc.school.repository.model.NewsEntity;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.mapper.NewsMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {
    @Mock()
    private NewsRepositoryImpl newsRepository;

    @Mock
    private AuthorServiceImpl authorService;

    private final NewsMapper newsMapper = new NewsMapperImpl();

    private NewsServiceImpl newsService;

    @BeforeEach
    void testSetup() {
         newsService = new NewsServiceImpl(newsRepository, authorService, newsMapper);
    }

    @Test
    void readAll_success() {
        List<AuthorDTO> authors = List.of(
                new AuthorDTO(1L, "Author 1 name"),
                new AuthorDTO(2L, "Author 2 name"),
                new AuthorDTO(3L, "Author 3 name")
        );

        List<NewsEntity> news = List.of(
                new NewsEntity(
                        21L,
                        "News 21 title",
                        "News 21 content",
                        LocalDateTime.of(2024, 5, 21, 12, 22, 1),
                        LocalDateTime.of(2024, 6, 24, 20, 22, 23),
                        1L
                ),
                new NewsEntity(
                        22L,
                        "News 22 title",
                        "News 22 content",
                        LocalDateTime.of(2023, 1, 2, 10, 1, 1),
                        LocalDateTime.of(2025, 2, 14, 21, 22, 23),
                        1L
                ),
                new NewsEntity(
                        23L,
                        "News 23 title",
                        "News 23 content",
                        LocalDateTime.of(2025, 11, 21, 13, 12, 1),
                        LocalDateTime.of(2026, 12, 1, 21, 22, 23),
                        3L
                ),
                new NewsEntity(
                        24L,
                        "News 24 title",
                        "News 24 content",
                        LocalDateTime.of(2022, 1, 23, 5, 12, 31),
                        LocalDateTime.of(2023, 2, 15, 1, 20, 3),
                        2L
                )
        );

        Mockito.when(authorService.readAll()).thenReturn(authors);
        Mockito.when(newsRepository.readAll()).thenReturn(news);

        List<NewsDTO> expectedNewsDTO = List.of(
                new NewsDTO(
                        21L,
                        "News 21 title",
                        "News 21 content",
                        "2024-05-21T12:22:01",
                        "2024-06-24T20:22:23",
                        new AuthorDTO(1L, "Author 1 name")
                ),
                new NewsDTO(
                        22L,
                        "News 22 title",
                        "News 22 content",
                        "2023-01-02T10:01:01",
                        "2025-02-14T21:22:23",
                        new AuthorDTO(1L, "Author 1 name")
                ),
                new NewsDTO(
                        23L,
                        "News 23 title",
                        "News 23 content",
                        "2025-11-21T13:12:01",
                        "2026-12-01T21:22:23",
                        new AuthorDTO(3L, "Author 3 name")
                ),
                new NewsDTO(
                        24L,
                        "News 24 title",
                        "News 24 content",
                        "2022-01-23T05:12:31",
                        "2023-02-15T01:20:03",
                        new AuthorDTO(2L, "Author 2 name")
                )
        );

        List<NewsDTO> actualNewsDTO = newsService.readAll();

        assertThat(actualNewsDTO)
                .isNotNull()
                .isNotEmpty()
                .hasSize(news.size())
                .usingRecursiveComparison()
                .isEqualTo(expectedNewsDTO);
    }
}
