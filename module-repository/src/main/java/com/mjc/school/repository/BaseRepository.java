package com.mjc.school.repository;

import com.mjc.school.repository.exception.EntityNotFoundException;
import com.mjc.school.repository.exception.EntityNullReferenceException;
import com.mjc.school.repository.exception.KeyNullReferenceException;
import com.mjc.school.repository.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity<K>, K> {

    List<T> readAll();

    Optional<T> readById(K id) throws KeyNullReferenceException, EntityNotFoundException;

    T create(T entity) throws EntityNullReferenceException;

    T update(T entity) throws EntityNullReferenceException, EntityNotFoundException, KeyNullReferenceException;

    boolean deleteById(K id) throws KeyNullReferenceException, EntityNotFoundException;

    boolean existById(K id) throws KeyNullReferenceException;
}
