package br.com.llocatti.spendev.sessions.dtos;

import br.com.llocatti.spendev.common.dtos.ApplicationError;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
public class AuthenticationError extends ApplicationError {

  public AuthenticationError(String message) {
    super(HttpStatus.UNAUTHORIZED, message);
  }
}
