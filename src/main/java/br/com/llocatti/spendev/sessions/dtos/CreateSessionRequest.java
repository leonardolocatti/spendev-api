package br.com.llocatti.spendev.sessions.dtos;

import javax.validation.constraints.NotEmpty;

public class CreateSessionRequest {

  @NotEmpty(message = "The 'email' field must be filled")
  private String email;

  @NotEmpty(message = "The 'password' field must be filled")
  private String password;

  public CreateSessionRequest() {}

  public CreateSessionRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
