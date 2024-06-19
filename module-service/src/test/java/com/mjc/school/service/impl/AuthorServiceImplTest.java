package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepositoryImpl;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.mapper.AuthorMapperImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {
    private AuthorMapper authorMapper = new AuthorMapperImpl();

    @Mock
    private AuthorRepositoryImpl authorRepository;

    private AuthorServiceImpl authorService = new AuthorServiceImpl(authorRepository, authorMapper);

}
