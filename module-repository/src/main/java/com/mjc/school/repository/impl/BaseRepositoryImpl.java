package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.exception.EntityNotFoundException;
import com.mjc.school.repository.exception.EntityNullReferenceException;
import com.mjc.school.repository.exception.KeyNullReferenceException;
import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.repository.aop.validation.ValidateEntity;

import java.util.List;
import java.util.Optional;

public class BaseRepositoryImpl <T extends BaseEntity<Long>> implements BaseRepository<T, Long> {
    private final DataSource<T> dataSource;

    public BaseRepositoryImpl(DataSource<T> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<T> readAll() {
        return dataSource.readAll();
    }

    @Override
    public Optional<T> readById(Long id) throws KeyNullReferenceException {
        return dataSource.readById(id);
    }

    @Override
    @ValidateEntity(ignoreFields = "id")
    public T create(T entity) throws EntityNullReferenceException {
        return dataSource.create(entity);
    }

    @Override
    @ValidateEntity()
    public T update(T entity) throws EntityNullReferenceException, EntityNotFoundException, KeyNullReferenceException {
        return dataSource.update(entity);
    }

    @Override
    public boolean deleteById(Long id) throws KeyNullReferenceException, EntityNotFoundException {
        return dataSource.deleteById(id);
    }

    @Override
    public boolean existById(Long id) throws KeyNullReferenceException {
        return dataSource.existsById(id);
    }
}
