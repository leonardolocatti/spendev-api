package br.com.llocatti.spendev.common.dtos;

import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
public class BusinessError extends ApplicationError {

  public BusinessError(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
