package com.mjc.school.helper;

import static com.mjc.school.helper.Constant.OPERATION;

public enum Operations {
  GET_ALL_AUTHORS(1, "Get all authors."),
  GET_AUTHOR_BY_ID(2, "Get author by id."),
  CREATE_AUTHOR(3, "Create author."),
  UPDATE_AUTHOR(4, "Update author."),
  REMOVE_AUTHOR_BY_ID(5, "Remove author by id."),

  GET_ALL_NEWS(6, "Get all news."),
  GET_NEWS_BY_ID(7, "Get news by id."),
  CREATE_NEWS(8, "Create news."),
  UPDATE_NEWS(9, "Update news."),
  REMOVE_NEWS_BY_ID(10, "Remove news by id."),

  EXIT(0, "Exit.");

  private final Integer operationNumber;
  private final String operation;

  Operations(Integer operationNumber, String operation) {
    this.operationNumber = operationNumber;
    this.operation = operation;
  }

  public String getOperation() {
    return OPERATION + operation;
  }

  public String getOperationWithNumber() {
    return operationNumber + " - " + operation;
  }
}
