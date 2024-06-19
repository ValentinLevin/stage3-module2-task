package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.NewsEntity;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsMapper {
    @Mapping(target = "author", ignore = true)
    NewsDTO toDTO(NewsEntity newsEntity);

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    NewsEntity toEntity(NewsRequestDTO dto);
}
