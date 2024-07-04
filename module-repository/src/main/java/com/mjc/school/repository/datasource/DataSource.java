package com.mjc.school.repository.datasource;

import com.mjc.school.repository.exception.EntityNotFoundException;
import com.mjc.school.repository.exception.EntityNullReferenceException;
import com.mjc.school.repository.exception.KeyNullReferenceException;
import com.mjc.school.repository.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface DataSource <T extends BaseEntity<Long>> {
    Optional<T> readById(Long id) throws KeyNullReferenceException;
    List<T> readAll();

    /**
     * @param offset number of elements to skip. If the value is zero, the elements will be taken from the first element
     * @param limit number of elements no more than the method should return. If the value of "limit" parameter is -1, all the elements of the dataset will be returned
     * @return list of dataset elements
     */
    List<T> readAll(long offset, long limit);

    T create(T value) throws EntityNullReferenceException;
    T update(T value) throws EntityNullReferenceException, KeyNullReferenceException, EntityNotFoundException;
    boolean deleteById(Long id) throws KeyNullReferenceException, EntityNotFoundException;
    long count();
    boolean existsById(Long id) throws KeyNullReferenceException;
}
