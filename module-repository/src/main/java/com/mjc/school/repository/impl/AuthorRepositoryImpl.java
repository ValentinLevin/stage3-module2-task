package com.mjc.school.repository.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.model.AuthorEntity;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryImpl extends BaseRepositoryImpl<AuthorEntity> implements AuthorRepository {
    public AuthorRepositoryImpl(DataSource<AuthorEntity> dataSource) {
        super(dataSource);
    }
}
