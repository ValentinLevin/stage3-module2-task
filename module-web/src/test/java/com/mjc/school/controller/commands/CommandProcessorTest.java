package com.mjc.school.controller.commands;

import com.mjc.school.controller.impl.AuthorController;
import com.mjc.school.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = { CommandProcessor.class, AuthorController.class, AuthorServiceImpl.class })
class CommandProcessorTest {
    @Autowired
    private CommandProcessor commandProcessor;

    @Test
    void test() {
        commandProcessor.getClass();
    }
}
