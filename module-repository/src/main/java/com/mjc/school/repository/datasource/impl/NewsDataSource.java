package com.mjc.school.repository.datasource.impl;

import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.model.NewsEntity;
import org.springframework.stereotype.Component;

@Component
public class NewsDataSource extends DataSourceImpl<NewsEntity> implements DataSource<NewsEntity> {
    private static final String DATA_FILE_NAME = "news.json";

    public NewsDataSource() {
        super(DATA_FILE_NAME, NewsEntity.class);
    }
}
