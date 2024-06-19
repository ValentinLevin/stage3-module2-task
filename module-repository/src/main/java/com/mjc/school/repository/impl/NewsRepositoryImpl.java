package com.mjc.school.repository.impl;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.model.NewsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class NewsRepositoryImpl extends BaseRepositoryImpl<NewsEntity> implements NewsRepository {
    public NewsRepositoryImpl(DataSource<NewsEntity> dataSource) {
        super(dataSource);
    }
}
