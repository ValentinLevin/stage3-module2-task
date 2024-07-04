package com.mjc.school.helper;

import com.mjc.school.controller.commands.CommandProcessor;
import com.mjc.school.controller.commands.command.*;

import java.lang.reflect.InvocationTargetException;
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
    Command command = new AuthorReadAllCommand();
    commandProcessor.processCommand(command);
    command.printResult();
  }

  public void getAuthorById(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    System.out.println(GET_AUTHOR_BY_ID.getOperation());
    System.out.println(AUTHOR_ID_ENTER);

    long id = getKeyboardNumber(AUTHOR_ID, keyboard);

    Command command = AuthorReadByIdCommand.builder().id(id).build();
    commandProcessor.processCommand(command);
    command.printResult();
  }

  public void createAuthor(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    String authorName = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        System.out.println(CREATE_AUTHOR.getOperation());
        System.out.println(AUTHOR_NAME_ENTER);
        authorName = keyboard.nextLine();
        isValid = true;
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
    Command command = AuthorCreateCommand.builder().authorName(authorName).build();
    commandProcessor.processCommand(command);
    command.printResult();
  }

  public void updateAuthor(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    AuthorUpdateCommand.AuthorUpdateCommandBuilder builder = AuthorUpdateCommand.builder();
    boolean isValid = false;
    while (!isValid) {
      try {
        System.out.println(UPDATE_AUTHOR.getOperation());
        System.out.println(AUTHOR_ID_ENTER);
        Long id = getKeyboardNumber(AUTHOR_ID, keyboard);
        builder.id(id);
        System.out.println(AUTHOR_NAME_ENTER);
        String name = keyboard.nextLine();
        builder.authorName(name);

        isValid = true;
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }

    Command command = builder.build();
    commandProcessor.processCommand(command);
    command.printResult();
  }

  public void deleteAuthor(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    System.out.println(REMOVE_AUTHOR_BY_ID.getOperation());
    System.out.println(AUTHOR_ID_ENTER);
    Long id = getKeyboardNumber(AUTHOR_ID, keyboard);

    AuthorDeleteByIdCommand command = AuthorDeleteByIdCommand.builder().id(id).build();
    commandProcessor.processCommand(command);
    command.printResult();
  }

  public void getNews() throws InvocationTargetException, IllegalAccessException {
    System.out.println(GET_ALL_NEWS.getOperation());
    Command command = new NewsReadAllCommand();
    commandProcessor.processCommand(command);
    command.printResult();
  }

  public void getNewsById(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    System.out.println(GET_NEWS_BY_ID.getOperation());
    System.out.println(NEWS_ID_ENTER);

    NewsReadByIdCommand.NewsReadByIdCommandBuilder builder = NewsReadByIdCommand.builder();
    long id = getKeyboardNumber(NEWS_ID, keyboard);
    builder.id(id);

    Command command = builder.build();
    commandProcessor.processCommand(command);
    command.printResult();
  }

  public void createNews(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    NewsCreateCommand.NewsCreateCommandBuilder builder = NewsCreateCommand.builder();
    boolean isValid = false;
    while (!isValid) {
      try {
        System.out.println(CREATE_NEWS.getOperation());

        System.out.println(NEWS_TITLE_ENTER);
        String title = keyboard.nextLine();
        builder.title(title);

        System.out.println(NEWS_CONTENT_ENTER);
        String content = keyboard.nextLine();
        builder.content(content);

        System.out.println(AUTHOR_ID_ENTER);
        Long authorId = getKeyboardNumber(AUTHOR_ID, keyboard);
        builder.authorId(authorId);

        isValid = true;
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
    Command command = builder.build();
    commandProcessor.processCommand(command);
    command.printResult();
  }

  public void updateNews(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    NewsUpdateCommand.NewsUpdateCommandBuilder builder = NewsUpdateCommand.builder();
    boolean isValid = false;
    while (!isValid) {
      try {
        System.out.println(UPDATE_NEWS.getOperation());
        System.out.println(NEWS_ID_ENTER);
        Long newsId = getKeyboardNumber(NEWS_ID, keyboard);
        builder.id(newsId);

        System.out.println(NEWS_TITLE_ENTER);
        String title = keyboard.nextLine();
        builder.title(title);

        System.out.println(NEWS_CONTENT_ENTER);
        String content = keyboard.nextLine();
        builder.content(content);

        System.out.println(AUTHOR_ID_ENTER);
        Long authorId = getKeyboardNumber(AUTHOR_ID, keyboard);
        builder.authorId(authorId);

        isValid = true;
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }

    Command command = builder.build();
    commandProcessor.processCommand(command);
    command.printResult();
  }

  public void deleteNews(Scanner keyboard) throws InvocationTargetException, IllegalAccessException {
    System.out.println(REMOVE_NEWS_BY_ID.getOperation());
    System.out.println(NEWS_ID_ENTER);
    Long id = getKeyboardNumber(NEWS_ID, keyboard);

    Command command = NewsDeleteByIdCommand.builder().id(id).build();
    commandProcessor.processCommand(command);
    command.printResult();
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
