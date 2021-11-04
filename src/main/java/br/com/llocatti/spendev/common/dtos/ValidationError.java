package br.com.llocatti.spendev.common.dtos;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends ApplicationError {

  private final List<Field> fields = new ArrayList<>();

  public ValidationError() {
    super(HttpStatus.BAD_REQUEST, "There are fields with validation errors");
  }

  public void addField(Field field) {
    fields.add(field);
  }

  public List<Field> getFields() {
    return fields;
  }
}
