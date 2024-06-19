package com.mjc.school.repository.exception;

import com.mjc.school.repository.model.BaseEntity;

public class EntityNotFoundException extends CustomRepositoryException {
    private static final String EXCEPTION_MESSAGE_TEMPLATE = "Not found entity with class %s by id %d";

    public EntityNotFoundException(Long entityId, Class<? extends BaseEntity<Long>> entityClass) {
        super(String.format(EXCEPTION_MESSAGE_TEMPLATE, entityClass.getCanonicalName(), entityId));
    }
}
