package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorEntity;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.AuthorRequestDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface AuthorMapper {
    AuthorDTO toDTO(AuthorEntity entity);

    AuthorEntity toEntity(AuthorRequestDTO editRequestDTO);
}
