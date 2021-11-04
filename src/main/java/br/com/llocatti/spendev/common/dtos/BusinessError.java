package br.com.llocatti.spendev.common.dtos;

import org.springframework.http.HttpStatus;

public class BusinessError extends ApplicationError {

  public BusinessError(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
