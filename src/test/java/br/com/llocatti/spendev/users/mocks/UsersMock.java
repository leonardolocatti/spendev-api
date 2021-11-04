package br.com.llocatti.spendev.users.mocks;

import br.com.llocatti.spendev.users.dtos.CreateUserRequest;
import br.com.llocatti.spendev.users.dtos.CreateUserResponse;
import br.com.llocatti.spendev.users.entities.User;

import java.util.UUID;

public final class UsersMock {

  private static final String NAME = "John Doe";
  private static final String EMAIL = "johndoe@email.com.br";
  private static final String INVALID_EMAIL = "invalid_email";
  private static final String PASSWORD = "pass123";
  private static final String INVALID_PASSWORD = "pass1";

  public static CreateUserRequest validCreateUserRequest() {
    return new CreateUserRequest(NAME, EMAIL, PASSWORD, PASSWORD);
  }

  public static CreateUserRequest createUserRequestWithInvalidEmail() {
    return new CreateUserRequest(NAME, INVALID_EMAIL, PASSWORD, PASSWORD);
  }

  public static CreateUserRequest createUserRequestWithInvalidPassword() {
    return new CreateUserRequest(NAME, EMAIL, INVALID_PASSWORD, PASSWORD);
  }

  public static CreateUserRequest createUserRequestWithWrongPasswordConfirmation() {
    return new CreateUserRequest(NAME, EMAIL, PASSWORD, INVALID_PASSWORD);
  }

  public static CreateUserResponse validCreateUserResponse() {
    return new CreateUserResponse(UUID.randomUUID(), NAME, EMAIL);
  }

  public static User validUser() {
    return new User(UUID.randomUUID(), NAME, EMAIL, PASSWORD);
  }
}
