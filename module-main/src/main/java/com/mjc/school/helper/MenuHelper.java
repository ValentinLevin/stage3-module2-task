package com.mjc.school.helper;

import com.mjc.school.controller.commands.CommandProcessor;
import com.mjc.school.controller.commands.command.*;
import com.mjc.school.service.dto.AuthorDTO;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.NewsDTO;

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
