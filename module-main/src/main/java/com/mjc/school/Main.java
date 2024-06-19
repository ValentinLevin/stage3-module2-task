package com.mjc.school;

import com.mjc.school.controller.commands.CommandProcessor;
import com.mjc.school.helper.MenuHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import static com.mjc.school.helper.Constant.COMMAND_NOT_FOUND;

public class Main {
  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext("com.mjc.school");

    CommandProcessor commandProcessor = context.getBean(CommandProcessor.class);

    Scanner keyboard = new Scanner(System.in);
    MenuHelper helper = new MenuHelper(commandProcessor);

    while (true) {
      try {
        helper.printMainMenu();
        String key = keyboard.nextLine();
        switch (key) {
          case "1" -> helper.getAuthors();
          case "2" -> helper.getAuthorById(keyboard);
          case "3" -> helper.createAuthor(keyboard);
          case "4" -> helper.updateAuthor(keyboard);
          case "5" -> helper.deleteAuthor(keyboard);

          case "6" -> helper.getNews();

          case "0" -> System.exit(0);
          default -> System.out.println(COMMAND_NOT_FOUND);
        }
      } catch (RuntimeException ex) {
        System.out.println(ex.getMessage());
      } catch (InvocationTargetException e) {
          throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
      }
    }
  }
}
