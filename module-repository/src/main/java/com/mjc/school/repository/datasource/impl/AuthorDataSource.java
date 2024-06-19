package com.mjc.school.repository.datasource.impl;

import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.model.AuthorEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthorDataSource extends DataSourceImpl<AuthorEntity> implements DataSource<AuthorEntity> {
    private static final String DATA_FILE_NAME = "author.json";

    public AuthorDataSource() {
        super(DATA_FILE_NAME, AuthorEntity.class);
    }
}
