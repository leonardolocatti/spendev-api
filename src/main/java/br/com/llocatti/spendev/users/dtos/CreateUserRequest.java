package br.com.llocatti.spendev.users.dtos;

import br.com.llocatti.spendev.users.entities.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CreateUserRequest {

  @NotEmpty(message = "The 'name' field must be filled")
  private String name;

  @NotEmpty(message = "The 'email' field must be filled")
  @Email(message = "The 'email' field must be filled with a valid email")
  private String email;

  @NotEmpty(message = "The 'password' field must be filled")
  @Size(
      min = 6,
      message = "The 'password' field must be filled in with a password of at least 6 digits")
  private String password;

  @NotEmpty(message = "The 'passwordConfirmation' field must be filled")
  private String passwordConfirmation;

  public CreateUserRequest() {}

  public CreateUserRequest(
      String name, String email, String password, String passwordConfirmation) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.passwordConfirmation = passwordConfirmation;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPasswordConfirmation() {
    return passwordConfirmation;
  }

  public User toEntity() {
    return new User(name, email, password);
  }

  @Override
  public String toString() {
    return "CreateUserRequest{"
        + "name='"
        + name
        + '\''
        + ", email='"
        + email
        + '\''
        + ", password='"
        + password
        + '\''
        + ", passwordConfirmation='"
        + passwordConfirmation
        + '\''
        + '}';
  }
}
