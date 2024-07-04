package com.mjc.school.service.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.exception.CustomRepositoryException;
import com.mjc.school.repository.exception.EntityNotFoundException;
import com.mjc.school.repository.exception.KeyNullReferenceException;
import com.mjc.school.repository.model.AuthorEntity;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.exception.AuthorNotFoundServiceException;
import com.mjc.school.service.exception.CustomServiceException;
import com.mjc.school.service.exception.NullAuthorIdServiceException;
import com.mjc.school.service.mapper.AuthorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    @Mock
    private AuthorRepository repository;

    @Mock
    private AuthorMapper authorMapper;

    @Mock
    private NewsService newsService;

    private AuthorServiceImpl authorService;

    @BeforeEach
    void testSetup() {
        authorService = new AuthorServiceImpl(repository, authorMapper);
        authorService.setNewsService(newsService);
    }

    @Test
    @DisplayName("Checking for reading list of authors")
    void readAll() {
        List<AuthorEntity> authors = List.of(
                new AuthorEntity(1L, "Author 1 name"),
                new AuthorEntity(2L, "Author 2 name"),
                new AuthorEntity(3L, "Author 3 name")
        );

        Mockito.when(repository.readAll()).thenReturn(authors);

        List<AuthorDTO> expectedAuthorDTOs =
                authors.stream()
                        .map(item -> {
                            AuthorDTO dto = new AuthorDTO(item.getId(), item.getName());
                            Mockito.when(authorMapper.toDTO(item)).thenReturn(dto);
                            return dto;
                        })
                        .toList();

        List<AuthorDTO> actualAuthorDTOs = authorService.readAll();

        assertThat(actualAuthorDTOs)
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthorDTOs);
    }
    @Test
    @DisplayName("Successful author request by id")
    void readById_success() throws CustomRepositoryException, CustomServiceException {
        AuthorEntity entity = new AuthorEntity(1L, "Author name");
        AuthorDTO expectedDTO = new AuthorDTO(entity.getId(), entity.getName());

        Mockito.when(repository.readById(entity.getId())).thenReturn(Optional.of(entity));
        Mockito.when(authorMapper.toDTO(entity)).thenReturn(expectedDTO);

        AuthorDTO actualDTO = authorService.readById(entity.getId());

        assertThat(actualDTO).isEqualTo(expectedDTO);
    }

    @Test
    @DisplayName("Request author by id. Not found")
    void readById_notFound() throws CustomRepositoryException {
        Long idForDelete = 1L;
        Mockito.when(repository.readById(Mockito.anyLong())).thenThrow(new EntityNotFoundException(idForDelete, AuthorEntity.class));
        assertThatThrownBy(() -> authorService.readById(idForDelete)).isInstanceOf(AuthorNotFoundServiceException.class);
    }

    @Test
    @DisplayName("Request author by id. Id is null")
    void readById_nullId() throws CustomRepositoryException {
        Mockito.when(repository.readById(Mockito.isNull())).thenThrow(new KeyNullReferenceException());
        assertThatThrownBy(() -> authorService.readById(null)).isInstanceOf(NullAuthorIdServiceException.class);
    }

    @Test
    @DisplayName("Removing an author by id. Success")
    void deleteById_success() throws CustomRepositoryException, CustomServiceException {
        Long id = 1L;
        Mockito.when(repository.deleteById(Mockito.anyLong())).thenReturn(true);

        assertThat(authorService.deleteById(id)).isTrue();
        Mockito.verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Removing an author by id. Not found")
    void deleteById_notFound() throws CustomRepositoryException {
        Long id = 1L;
        Mockito.when(repository.deleteById(Mockito.anyLong())).thenThrow(new EntityNotFoundException(id, AuthorEntity.class));
        assertThatThrownBy(() -> authorService.deleteById(id)).isInstanceOf(AuthorNotFoundServiceException.class);
    }

    @Test
    @DisplayName("Removing an author by id. id is null")
    void deleteById_nullId() throws CustomRepositoryException {
        Mockito.when(repository.deleteById(Mockito.isNull())).thenThrow(new KeyNullReferenceException());
        assertThatThrownBy(() -> authorService.deleteById(null)).isInstanceOf(NullAuthorIdServiceException.class);
    }

    @Test
    @DisplayName("Saving a new author. Success")
    void create_success() throws CustomRepositoryException, CustomServiceException {
        AuthorRequestDTO requestDTO = new AuthorRequestDTO(null, "Author name");

        AuthorEntity authorEntity = new AuthorEntity(1L, requestDTO.getName());
        AuthorDTO expectedAuthorDTO = new AuthorDTO(authorEntity.getId(), authorEntity.getName());

        Mockito.when(authorMapper.toEntity(requestDTO)).thenReturn(authorEntity);
        Mockito.when(authorMapper.toDTO(authorEntity)).thenReturn(expectedAuthorDTO);
        Mockito.when(repository.create(authorEntity)).thenReturn(authorEntity);
        Mockito.when(repository.readById(expectedAuthorDTO.getId())).thenReturn(Optional.of(authorEntity));

        AuthorDTO actualAuthorDTO = authorService.create(requestDTO);

        assertThat(actualAuthorDTO).isEqualTo(expectedAuthorDTO);
    }

    @ParameterizedTest
    @DisplayName("Obtaining an indication of the presence of an author by id. Success")
    @ValueSource(booleans = {true, false})
    void existsById_success(boolean value) throws CustomRepositoryException, CustomServiceException {
        Mockito.when(repository.existById(Mockito.anyLong())).thenReturn(value);
        Boolean actualResult = authorService.existsById(1L);
        assertThat(actualResult).isEqualTo(value);
    }

    @Test
    @DisplayName("Obtaining an indication of the presence of an author by id. Null id")
    void existsById_nullId() throws CustomRepositoryException {
        Mockito.when(repository.existById(Mockito.isNull())).thenThrow(KeyNullReferenceException.class);
        assertThatThrownBy(() -> authorService.existsById(null)).isInstanceOf(NullAuthorIdServiceException.class);
    }

    @Test
    @DisplayName("Change of author data. Success")
    void update_success() throws CustomRepositoryException, CustomServiceException {
        AuthorRequestDTO requestDTO = new AuthorRequestDTO(1L, "Author name");

        AuthorEntity authorEntity = new AuthorEntity(1L, requestDTO.getName());
        Mockito.when(repository.readById(requestDTO.getId())).thenReturn(Optional.of(authorEntity));

        AuthorDTO expectedAuthorDTO = new AuthorDTO(authorEntity.getId(), authorEntity.getName());

        Mockito.when(authorMapper.toEntity(requestDTO)).thenReturn(authorEntity);
        Mockito.when(authorMapper.toDTO(authorEntity)).thenReturn(expectedAuthorDTO);
        Mockito.when(repository.update(authorEntity)).thenReturn(authorEntity);

        AuthorDTO actualAuthorDTO = authorService.update(requestDTO);

        assertThat(actualAuthorDTO).isEqualTo(expectedAuthorDTO);
    }
}
