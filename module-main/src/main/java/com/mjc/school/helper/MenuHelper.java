package com.mjc.school.helper;

import com.mjc.school.controller.commands.CommandProcessor;
import com.mjc.school.controller.commands.command.*;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.NewsDTO;
import com.mjc.school.service.dto.NewsRequestDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;

import static com.mjc.school.helper.Constant.*;
import static com.mjc.school.helper.Operations.*;

public class MenuHelper {
  private final CommandProcessor commandProcessor;

  public MenuHelper(CommandProcessor commandProcessor) {
    this.commandProcessor = commandProcessor;
  }

  public void printMainMenu() {
    System.out.println(NUMBER_OPERATION_ENTER);
    for (Operations operation : Operations.values()) {
      System.out.println(operation.getOperationWithNumber());
    }
  }

  public void getAuthors() throws InvocationTargetException, IllegalAccessException {
    System.out.println(GET_ALL_AUTHORS.getOperation());
    Command<List<AuthorDTO>> command = new AuthorReadAllCommand();
    commandProcessor.processCommand(command);
    command.getResult().forEach(System.out::println);
  }

  public void getAuthorById(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    System.out.println(GET_AUTHOR_BY_ID.getOperation());
    System.out.println(AUTHOR_ID_ENTER);

    long id = getKeyboardNumber(AUTHOR_ID, keyboard);

    Command<AuthorDTO> command = new AuthorReadByIdCommand(id);
    commandProcessor.processCommand(command);
    System.out.println(command.getResult());
  }

  public void createAuthor(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    AuthorRequestDTO dtoRequest = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        System.out.println(CREATE_AUTHOR.getOperation());
        System.out.println(AUTHOR_NAME_ENTER);
        String name = keyboard.nextLine();
        dtoRequest = new AuthorRequestDTO(null, name);
        isValid = true;
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
    AuthorCreateCommand command = new AuthorCreateCommand(dtoRequest);
    commandProcessor.processCommand(command);
    System.out.println(command.getResult());
  }

  public void updateAuthor(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    AuthorRequestDTO dtoRequest = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        System.out.println(UPDATE_AUTHOR.getOperation());
        System.out.println(AUTHOR_ID_ENTER);
        Long id = getKeyboardNumber(AUTHOR_ID, keyboard);
        System.out.println(AUTHOR_NAME_ENTER);
        String name = keyboard.nextLine();

        dtoRequest = new AuthorRequestDTO(id, name);
        isValid = true;
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }

    AuthorUpdateCommand command = new AuthorUpdateCommand(dtoRequest);
    commandProcessor.processCommand(command);
    System.out.println(command.getResult());
  }

  public void deleteAuthor(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    System.out.println(REMOVE_AUTHOR_BY_ID.getOperation());
    System.out.println(AUTHOR_ID_ENTER);
    Long id = getKeyboardNumber(AUTHOR_ID, keyboard);

    AuthorDeleteByIdCommand command = new AuthorDeleteByIdCommand(id);
    commandProcessor.processCommand(command);
    System.out.println(command.getResult());
  }

  public void getNews() throws InvocationTargetException, IllegalAccessException {
    System.out.println(GET_ALL_NEWS.getOperation());
    Command<List<NewsDTO>> command = new NewsReadAllCommand();
    commandProcessor.processCommand(command);
    command.getResult().forEach(System.out::println);
  }

  public void getNewsById(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    System.out.println(GET_NEWS_BY_ID.getOperation());
    System.out.println(NEWS_ID_ENTER);

    long id = getKeyboardNumber(NEWS_ID, keyboard);

    Command<NewsDTO> command = new NewsReadByIdCommand(id);
    commandProcessor.processCommand(command);
    System.out.println(command.getResult());
  }

  public void createNews(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    NewsRequestDTO dtoRequest = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        System.out.println(CREATE_NEWS.getOperation());

        System.out.println(NEWS_TITLE_ENTER);
        String title = keyboard.nextLine();
        System.out.println(NEWS_CONTENT_ENTER);
        String content = keyboard.nextLine();
        System.out.println(AUTHOR_ID_ENTER);
        Long authorId = getKeyboardNumber(AUTHOR_ID, keyboard);

        dtoRequest = new NewsRequestDTO(null, title, content, authorId);
        isValid = true;
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
    NewsCreateCommand command = new NewsCreateCommand(dtoRequest);
    commandProcessor.processCommand(command);
    System.out.println(command.getResult());
  }

  public void updateNews(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    NewsRequestDTO dtoRequest = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        System.out.println(UPDATE_NEWS.getOperation());
        System.out.println(NEWS_ID_ENTER);
        Long newsId = getKeyboardNumber(NEWS_ID, keyboard);
        System.out.println(NEWS_TITLE_ENTER);
        String title = keyboard.nextLine();
        System.out.println(NEWS_CONTENT_ENTER);
        String content = keyboard.nextLine();
        System.out.println(AUTHOR_ID_ENTER);
        Long authorId = getKeyboardNumber(AUTHOR_ID, keyboard);

        dtoRequest = new NewsRequestDTO(newsId, title, content, authorId);
        isValid = true;
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }

    NewsUpdateCommand command = new NewsUpdateCommand(dtoRequest);
    commandProcessor.processCommand(command);
    System.out.println(command.getResult());
  }

  public void deleteNews(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    System.out.println(REMOVE_NEWS_BY_ID.getOperation());
    System.out.println(NEWS_ID_ENTER);
    Long id = getKeyboardNumber(NEWS_ID, keyboard);

    NewsDeleteByIdCommand command = new NewsDeleteByIdCommand(id);
    commandProcessor.processCommand(command);
    System.out.println(command.getResult());
  }

  private long getKeyboardNumber(String params, Scanner keyboard) {
    long enter;
    try {
      enter = keyboard.nextLong();
      keyboard.nextLine();
    } catch (Exception ex) {
      keyboard.nextLine();
      throw new RuntimeException();
    }
    return enter;
  }
}
