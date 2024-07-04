package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorEntity;
import com.mjc.school.service.dto.AuthorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuthorMapperTest {
    private final AuthorMapper authorMapper = new AuthorMapperImpl();

    @Test
    @DisplayName("Testing how AuthorEntity maps to AuthorDTO")
    void toDTO() {
        AuthorEntity entity = new AuthorEntity(123L, "Author's name");
        AuthorDTO expectedDTO = new AuthorDTO(entity.getId(), entity.getName());

        AuthorDTO actualDTO = authorMapper.toDTO(entity);

        assertThat(actualDTO).usingRecursiveComparison().isEqualTo(expectedDTO);
    }
}
