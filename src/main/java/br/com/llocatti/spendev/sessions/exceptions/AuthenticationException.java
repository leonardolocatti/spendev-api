package br.com.llocatti.spendev.sessions.exceptions;

import lombok.ToString;

@ToString
public class AuthenticationException extends RuntimeException {

  public AuthenticationException(String message) {
    super(message);
  }
}
