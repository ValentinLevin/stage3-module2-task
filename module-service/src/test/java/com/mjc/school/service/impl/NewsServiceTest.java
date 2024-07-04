package com.mjc.school.service.impl;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.exception.CustomRepositoryException;
import com.mjc.school.repository.exception.EntityNotFoundException;
import com.mjc.school.repository.exception.KeyNullReferenceException;
import com.mjc.school.repository.model.NewsEntity;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.exception.AuthorNotFoundServiceException;
import com.mjc.school.service.exception.CustomServiceException;
import com.mjc.school.service.exception.NewsNotFoundServiceException;
import com.mjc.school.service.exception.NullNewsIdServiceException;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.mapper.NewsMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {
    @Mock
    private NewsRepository newsRepository;

    @Mock
    private AuthorService authorService;

    private final NewsMapper newsMapper = new NewsMapperImpl();

    private NewsService newsService;

    @BeforeEach
    void testSetup() {
         newsService = new NewsServiceImpl(newsRepository, authorService, newsMapper);
    }

    @Test
    @DisplayName("Getting a list of news. Success")
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

    @Test
    @DisplayName("Receive news by id. Success")
    void readById_success() throws CustomRepositoryException, CustomServiceException {
        NewsEntity newsEntity =
                new NewsEntity(
                        1L,
                        "News title",
                        "News content",
                        LocalDateTime.of(2024, 7, 2, 12, 23, 45),
                        LocalDateTime.of(2024, 9, 3, 15, 3, 59),
                        2L
                );
        Mockito.when(newsRepository.readById(newsEntity.getId())).thenReturn(Optional.of(newsEntity));

        AuthorDTO authorDTO = new AuthorDTO(2L, "Author name");
        Mockito.when(authorService.readById(newsEntity.getAuthorId())).thenReturn(authorDTO);

        NewsDTO expectedNewsDTO =
                new NewsDTO(
                        newsEntity.getId(),
                        newsEntity.getTitle(),
                        newsEntity.getContent(),
                        "2024-07-02T12:23:45",
                        "2024-09-03T15:03:59",
                        authorDTO
                );

        NewsDTO actualNewsDTO = newsService.readById(newsEntity.getId());

        assertThat(actualNewsDTO).usingRecursiveComparison().isEqualTo(expectedNewsDTO);
    }

    @Test
    @DisplayName("Receive news by id. Not found")
    void readById_notFound() throws CustomRepositoryException {
        Long id = 1L;
        Mockito.when(newsRepository.readById(id)).thenThrow(new EntityNotFoundException(id, NewsEntity.class));
        assertThatThrownBy(() -> newsService.readById(id)).isInstanceOf(NewsNotFoundServiceException.class);
    }

    @Test
    @DisplayName("Receive news by id. Null id value passed")
    void readById_nullId() throws CustomRepositoryException {
        Mockito.when(newsRepository.readById(Mockito.isNull())).thenThrow(new KeyNullReferenceException());
        assertThatThrownBy(() -> newsService.readById(null)).isInstanceOf(NullNewsIdServiceException.class);
    }

    @Test()
    @DisplayName("Deleting news by id. Success")
    void deleteById_success() throws CustomRepositoryException, CustomServiceException {
        Long id = 1L;
        Boolean expectedResult = true;
        Mockito.when(newsRepository.deleteById(id)).thenReturn(expectedResult);
        Boolean actualResult = newsService.deleteById(id);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Deleting news by id. Not found")
    void deleteById_notFound() throws CustomRepositoryException {
        Long id = 1L;
        Mockito.when(newsRepository.deleteById(id)).thenThrow(new EntityNotFoundException(id, NewsEntity.class));
        assertThatThrownBy(() -> newsService.deleteById(id)).isExactlyInstanceOf(NewsNotFoundServiceException.class);
    }

    @Test
    @DisplayName("Deleting news by id. Not found")
    void deleteById_nullId() throws CustomRepositoryException {
        Mockito.when(newsRepository.deleteById(Mockito.isNull())).thenThrow(new KeyNullReferenceException());
        assertThatThrownBy(() -> newsService.deleteById(null)).isExactlyInstanceOf(NullNewsIdServiceException.class);
    }

    @Test
    @DisplayName("Adding news. Success")
    void createNews_success() throws CustomRepositoryException, CustomServiceException {
        NewsRequestDTO requestDTO =
                new NewsRequestDTO(
                        null,
                        "News title",
                        "News content",
                        2L
                );

        NewsEntity newsEntity =
                new NewsEntity(
                        3L,
                        requestDTO.getTitle(),
                        requestDTO.getContent(),
                        LocalDateTime.of(2024, 1, 4, 23, 43, 1),
                        LocalDateTime.of(2024, 12, 4, 12, 34, 54),
                        requestDTO.getAuthorId()
                );

        AuthorDTO expectedAuthorDTO =
                new AuthorDTO(
                        newsEntity.getAuthorId(),
                        "Author name"
                );

        Mockito.when(authorService.readById(newsEntity.getAuthorId())).thenReturn(expectedAuthorDTO);

        NewsDTO expectedNewsDTO =
                new NewsDTO(
                        newsEntity.getId(),
                        newsEntity.getTitle(),
                        newsEntity.getContent(),
                        "2024-01-04T23:43:01",
                        "2024-12-04T12:34:54",
                        expectedAuthorDTO
                );

        Mockito.when(authorService.existsById(requestDTO.getAuthorId())).thenReturn(true);
        Mockito.when(newsRepository.create(Mockito.any(NewsEntity.class))).thenReturn(newsEntity);
        Mockito.when(newsRepository.readById(newsEntity.getId())).thenReturn(Optional.of(newsEntity));

        ArgumentCaptor<NewsEntity> entityCaptor = ArgumentCaptor.forClass(NewsEntity.class);

        LocalDateTime createdAtFrom = LocalDateTime.now();
        NewsDTO actualNewsDTO = newsService.create(requestDTO);
        LocalDateTime createdAtTo = LocalDateTime.now();

        assertThat(actualNewsDTO)
                .usingRecursiveComparison()
                .isEqualTo(expectedNewsDTO);

        Mockito.verify(newsRepository).create(entityCaptor.capture());

        NewsEntity actualEntity = entityCaptor.getValue();

        assertThat(actualEntity)
                .extracting("title", "content", "authorId")
                .containsOnly(requestDTO.getTitle(), requestDTO.getContent(), requestDTO.getAuthorId());

        assertThat(actualEntity.getCreateDate()).isBetween(createdAtFrom, createdAtTo);
        assertThat(actualEntity.getLastUpdateDate()).isBetween(createdAtFrom, createdAtTo);
    }

    @Test
    @DisplayName("Saving new news. Author not found by code")
    void create_authorNotFound() throws CustomServiceException {
        NewsRequestDTO requestDTO =
                new NewsRequestDTO(
                        null,
                        "News title",
                        "News content",
                        2L
                );

        Mockito.when(authorService.existsById(requestDTO.getAuthorId())).thenReturn(false);

        assertThatThrownBy(() -> newsService.create(requestDTO))
                .usingRecursiveComparison()
                .isEqualTo(new AuthorNotFoundServiceException(requestDTO.getAuthorId()));
    }

    @ParameterizedTest()
    @DisplayName("Checking the method for obtaining a sign of the presence of news by id. Success")
    @ValueSource(booleans = {true, false})
    void existsById_success(boolean expectedResult) throws CustomRepositoryException, CustomServiceException {
        Mockito.when(newsRepository.existById(Mockito.anyLong())).thenReturn(expectedResult);
        boolean actualResult = newsService.existsById(1L);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Checking the method for obtaining a sign of the presence of news by id. Null value as id")
    void existsById_nullId() throws CustomRepositoryException {
        Mockito.when(newsRepository.existById(Mockito.isNull())).thenThrow(new KeyNullReferenceException());
        assertThatThrownBy(() -> newsService.existsById(null))
                .usingRecursiveComparison()
                .isEqualTo(new NullNewsIdServiceException());
    }

    @Test
    @DisplayName("Changing news information. Success")
    void update_success() throws CustomRepositoryException, CustomServiceException{
        NewsRequestDTO requestDTO =
                new NewsRequestDTO(
                        1L,
                        "News title",
                        "News content",
                        2L
                );

        NewsEntity newsEntity =
                new NewsEntity(
                        requestDTO.getId(),
                        requestDTO.getTitle(),
                        requestDTO.getContent(),
                        LocalDateTime.of(2024, 1, 4, 23, 43, 1),
                        LocalDateTime.of(2024, 12, 4, 12, 34, 54),
                        requestDTO.getAuthorId()
                );

        AuthorDTO expectedAuthorDTO =
                new AuthorDTO(
                        newsEntity.getAuthorId(),
                        "Author name"
                );

        Mockito.when(authorService.readById(newsEntity.getAuthorId())).thenReturn(expectedAuthorDTO);

        NewsDTO expectedNewsDTO =
                new NewsDTO(
                        newsEntity.getId(),
                        newsEntity.getTitle(),
                        newsEntity.getContent(),
                        "2024-01-04T23:43:01",
                        "2024-12-04T12:34:54",
                        expectedAuthorDTO
                );

        Mockito.when(authorService.existsById(requestDTO.getAuthorId())).thenReturn(true);
        Mockito.when(newsRepository.update(Mockito.any(NewsEntity.class))).thenReturn(newsEntity);
        Mockito.when(newsRepository.readById(newsEntity.getId())).thenReturn(Optional.of(newsEntity));

        ArgumentCaptor<NewsEntity> entityCaptor = ArgumentCaptor.forClass(NewsEntity.class);

        LocalDateTime lastUpdateAtFrom = LocalDateTime.now();
        NewsDTO actualNewsDTO = newsService.update(requestDTO);
        LocalDateTime lastUpdateAtTo = LocalDateTime.now();

        assertThat(actualNewsDTO)
                .usingRecursiveComparison()
                .isEqualTo(expectedNewsDTO);

        Mockito.verify(newsRepository).update(entityCaptor.capture());

        NewsEntity actualEntity = entityCaptor.getValue();

        assertThat(actualEntity)
                .extracting("title", "content", "authorId")
                .containsOnly(requestDTO.getTitle(), requestDTO.getContent(), requestDTO.getAuthorId());

        assertThat(actualEntity.getCreateDate()).isEqualTo(newsEntity.getCreateDate());
        assertThat(actualEntity.getLastUpdateDate()).isBetween(lastUpdateAtFrom, lastUpdateAtTo);
    }

    @Test
    @DisplayName("Saving news changes. Author not found by code")
    void update_authorNotFound() throws CustomServiceException {
        NewsRequestDTO requestDTO =
                new NewsRequestDTO(
                        1L,
                        "News title",
                        "News content",
                        2L
                );

        Mockito.when(authorService.existsById(requestDTO.getAuthorId())).thenReturn(false);

        assertThatThrownBy(() -> newsService.update(requestDTO))
                .usingRecursiveComparison()
                .isEqualTo(new AuthorNotFoundServiceException(requestDTO.getAuthorId()));
    }

}
