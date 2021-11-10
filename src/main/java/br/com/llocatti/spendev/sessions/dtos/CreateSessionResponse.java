package br.com.llocatti.spendev.sessions.dtos;

public class CreateSessionResponse {

  private final String token;

  public CreateSessionResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
