package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.NewsEntity;
import com.mjc.school.service.dto.NewsDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NewsMapperTest {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final NewsMapper newsMapper = new NewsMapperImpl();

    @Test
    @DisplayName("Testing how NewsEntity maps to NewsDTO")
    void toDTO() {
        NewsEntity entity =
                new NewsEntity(
                        123L,
                        "News title",
                        "News content",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        321L
                );

        NewsDTO expectedDTO =
                new NewsDTO(
                        entity.getId(),
                        entity.getTitle(),
                        entity.getContent(),
                        dateTimeFormatter.format(entity.getCreateDate()),
                        dateTimeFormatter.format(entity.getLastUpdateDate()),
                        null
                );

        NewsDTO actualDTO = newsMapper.toDTO(entity);

        assertThat(actualDTO).usingRecursiveComparison().isEqualTo(expectedDTO);
    }
}
