package br.com.llocatti.spendev.sessions.exceptions;

public class AuthenticationException extends RuntimeException {

  public AuthenticationException(String message) {
    super(message);
  }
}
