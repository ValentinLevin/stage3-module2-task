package com.mjc.school.repository.datasource.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.exception.*;
import com.mjc.school.repository.model.BaseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class DataSourceImpl<T extends BaseEntity<Long>> implements DataSource<T> {
    private final Class<T> entityClass;
    private final Map<Long, T> values = new LinkedHashMap<>();
    private final ReadWriteLock entityLock = new ReentrantReadWriteLock();
    private final AtomicLong nextId = new AtomicLong(1L);

    protected DataSourceImpl(String dataFileName, Class<T> entityClass) {
        this.entityClass = entityClass;
        List<T> items = readDataFromFile(dataFileName, entityClass);
        indexItems(items);
    }

    private void indexItems(List<T> items) {
        entityLock.writeLock().lock();
        try {
            for (T item: items) {
                this.values.put(item.getId(), item);
                if (item.getId() >= nextId.get()) {
                    setNextId(item.getId() + 1);
                }
            }
        } finally {
            entityLock.writeLock().unlock();
        }
    }

    private List<T> readDataFromFile(String dataFileName, Class<T> entityClass) {
        ClassLoader classLoader = AuthorDataSource.class.getClassLoader();

        try (InputStream is = classLoader.getResourceAsStream(dataFileName)) {
            if (is == null) {
                throw new DataFileNotFoundException(dataFileName);
            }

            ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
            JavaType mapperType = mapper.getTypeFactory().constructCollectionType(List.class, entityClass);
            return mapper.readValue(is, mapperType);
        } catch (IOException e) {
            throw new DataFileReadException(dataFileName, e);
        }
    }

    @Override
    public Optional<T> readById(Long id) throws KeyNullReferenceException {
        if (id == null) {
            throw new KeyNullReferenceException();
        }

        entityLock.readLock().lock();
        try {
            T entity = this.values.get(id);
            if (entity == null) {
                return Optional.empty();
            } else {
                return Optional.of(cloneEntity(entity));
            }
        } finally {
            entityLock.readLock().unlock();
        }
    }

    @Override
    public List<T> readAll() {
        entityLock.readLock().lock();
        try {
            return this.values.values().stream()
                    .map(this::cloneEntity)
                    .toList();
        } finally {
            entityLock.readLock().unlock();
        }
    }

    @Override
    public List<T> readAll(long offset, long limit) {
        if (offset < this.values.size()) {
            entityLock.readLock().lock();
            try {
                return this.values.values().stream()
                        .skip(offset)
                        .limit(limit == -1 ? values.size() : limit)
                        .map(this::cloneEntity)
                        .toList();
            } finally {
                entityLock.readLock().unlock();
            }
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public T create(T value) throws EntityNullReferenceException {
        if (value == null) {
            throw new EntityNullReferenceException();
        }

        value.setId(getNextId());

        T entityToSave = cloneEntity(value);

        entityLock.writeLock().lock();
        try {
            this.values.put(entityToSave.getId(), entityToSave);
        } finally {
            entityLock.writeLock().unlock();
        }

        return value;
    }

    @Override
    public T update(T value) throws EntityNullReferenceException, KeyNullReferenceException, EntityNotFoundException {
        if (value == null) {
            throw new EntityNullReferenceException();
        }

        if (value.getId() == null || value.getId() == 0) {
            throw new KeyNullReferenceException();
        }

        if (!existsById(value.getId())) {
            throw new EntityNotFoundException(value.getId(), entityClass);
        }

        T entityToSave = cloneEntity(value);

        entityLock.writeLock().lock();
        try {
            this.values.put(entityToSave.getId(), entityToSave);
        } finally {
            entityLock.writeLock().unlock();
        }

        return value;
    }

    @Override
    public boolean deleteById(Long id) throws KeyNullReferenceException, EntityNotFoundException {
        if (id == null) {
            throw new KeyNullReferenceException();
        }

        entityLock.writeLock().lock();
        try {
            if (this.values.containsKey(id)) {
                return this.values.remove(id) != null;
            } else {
                throw new EntityNotFoundException(id, entityClass);
            }
        } finally {
            entityLock.writeLock().unlock();
        }
    }

    @Override
    public long count() {
        entityLock.readLock().lock();
        try {
            return this.values.size();
        } finally {
            entityLock.readLock().unlock();
        }
    }

    @Override
    public boolean existsById(Long id) throws KeyNullReferenceException {
        if (id == null) {
            throw new KeyNullReferenceException();
        }

        entityLock.readLock().lock();
        try {
            return this.values.containsKey(id);
        } finally {
            entityLock.readLock().unlock();
        }
    }

    private Long getNextId() {
        return this.nextId.getAndIncrement();
    }

    private void setNextId(Long value) {
        this.nextId.set(value);
    }

    private T cloneEntity(T entity) {
        return (T) entity.copy();
    }
}
