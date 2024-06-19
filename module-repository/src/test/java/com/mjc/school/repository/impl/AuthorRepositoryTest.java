package com.mjc.school.repository.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.aop.validation.AopConfig;
import com.mjc.school.repository.aop.validation.EntityValidationAspect;
import com.mjc.school.repository.datasource.impl.AuthorDataSource;
import com.mjc.school.repository.exception.EntityValidationException;
import com.mjc.school.repository.model.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig(classes = { AopConfig.class, AuthorDataSource.class, AuthorRepositoryImpl.class, EntityValidationAspect.class })
class AuthorRepositoryTest {
    @Mock
    private AuthorDataSource authorDataSource;

    @Autowired
    @InjectMocks
    private AuthorRepository repository;

    @Test
    void create() {
        AuthorEntity entity = new AuthorEntity(0L, "Au");
        assertThatThrownBy(() -> repository.create(entity)).isInstanceOf(EntityValidationException.class);
    }

    @Test
    void update() {
        AuthorEntity entity = new AuthorEntity(0L, "Author name");
        assertThatThrownBy(() -> repository.update(entity)).isInstanceOf(EntityValidationException.class);
    }
}
