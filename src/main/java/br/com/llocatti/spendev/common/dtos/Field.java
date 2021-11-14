package br.com.llocatti.spendev.common.dtos;

public class Field {

  private final String name;
  private final String message;

  public Field(String name, String message) {
    this.name = name;
    this.message = message;
  }

  public String getName() {
    return name;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "Field{" + "name='" + name + '\'' + ", message='" + message + '\'' + '}';
  }
}
